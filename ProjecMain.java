import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.SwingUtilities.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.pickfast.PickCanvas;
import com.sun.j3d.utils.universe.*;

public class ProjecMain extends Applet implements MouseListener, MouseMotionListener,KeyListener,MouseWheelListener {
     
    private static final long serialVersionUID = 1L;
    private MainFrame frame; //the main app; display
    private Box box; //sample box
    private int imageHeight = 1024; 
    private int imageWidth = 1024;
    private Canvas3D canvas; //for drawing
    private SimpleUniverse universe; //for perspective
    private BranchGroup group = new BranchGroup();
    private PickCanvas pickCanvas;
    private BufferedImage frontImage; //frontimage
    private Shape3D frontShape; //shape at forefront
    private Texture texture;
    private Appearance appearance;
    private TextureLoader loader;
    private int lastX=-1;
    private int lastY=-1;
    private int mouseButton = 0; //determine if mouse is held
    private TransformGroup boxTransformGroup = new TransformGroup();
    Transform3D tran = new Transform3D();
    private float viewX=0.0f;
    private float viewY=0.0f;
    private float viewZ=0.0f;
    int bab = 1;
    
/**
 *main method run on startup: init objects and gui
 */
    public static void main(String[] args) {
        System.setProperty("sun.awt.noerasebackground", "false");
        ProjecMain object = new ProjecMain();         
        object.frame = new MainFrame(object, args, object.imageWidth, object.imageHeight);
        object.validate();
        //object validation
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUI();
                //ui setup
            }
        });
    }
     
/**
 *applet extension runs init
 */
    public void init() {
          startDrawing();
    }
     
/**
 *gui stuff: frame button etc
 */
    private static void GUI(){
        JFrame frame = new JFrame("Options");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JButton cubeButt = new JButton("Insert Cube");
        frame.getContentPane().add(cubeButt, BorderLayout.CENTER);
        //init separate window
        
        //display the window
        frame.pack();
        frame.setLocation(1100,0);
        frame.setVisible(true);
    }

/**
 *locate object in virtual universe and return coord
 */
    public Point3d getPosition(MouseEvent event) {
        Point3d eyePos = new Point3d();
        Point3d mousePos = new Point3d();
        canvas.getCenterEyeInImagePlate(eyePos);
        canvas.getPixelLocationInImagePlate(event.getX(), event.getY(), mousePos);
        Transform3D transform = new Transform3D();
        canvas.getImagePlateToVworld(transform);
        transform.transform(eyePos);
        transform.transform(mousePos);
        Vector3d direction = new Vector3d(eyePos);
        direction.sub(mousePos); // three points on the plane
        Point3d p1 = new Point3d(.5, -.5, .5);
        Point3d p2 = new Point3d(.5, .5, .5);
        Point3d p3 = new Point3d(-.5, .5, .5);
        Transform3D currentTransform = new Transform3D();
        box.getLocalToVworld(currentTransform);
        currentTransform.transform(p1);
        currentTransform.transform(p2);
        currentTransform.transform(p3);        
        Point3d intersection = getIntersection(eyePos, mousePos, p1, p2, p3); //set coordinates relative to eye position
        currentTransform.invert();
        currentTransform.transform(intersection);
        return intersection;        
    }

    /**
     * returns the point where a line crosses a plane  
     */
    Point3d getIntersection(Point3d line1, Point3d line2, 
        Point3d plane1, Point3d plane2, Point3d plane3) { //find intersection points of vectors
        Vector3d p1 = new Vector3d(plane1); //find vectors to judge point of intersection
        Vector3d p2 = new Vector3d(plane2);
        Vector3d p3 = new Vector3d(plane3);
        Vector3d p2minusp1 = new Vector3d(p2);
        p2minusp1.sub(p1);
        Vector3d p3minusp1 = new Vector3d(p3);
        p3minusp1.sub(p1);
        Vector3d normal = new Vector3d();
        normal.cross(p2minusp1, p3minusp1);
        double d = -p1.dot(normal);
        Vector3d i1 = new Vector3d(line1);
        Vector3d direction = new Vector3d(line1);
        direction.sub(line2);
        double dot = direction.dot(normal);
        if (dot == 0) return null;
        double t = (-d - i1.dot(normal)) / (dot);
        Vector3d intersection = new Vector3d(line1);
        Vector3d scaledDirection = new Vector3d(direction);
        scaledDirection.scale(t);
        intersection.add(scaledDirection);
        Point3d intersectionPoint = new Point3d(intersection);
        return intersectionPoint;
    }
     
/**
 *visuals and camera init
 */
    private void startDrawing() { //init visual stuff
        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse
                .getPreferredConfiguration(); //universe defaults
        canvas = new Canvas3D(config);
        universe = new SimpleUniverse(canvas);
        add("Center", canvas); //add center config
        positionViewer(); //init camera
        getScene();
        universe.addBranchGraph(group);
        pickCanvas = new PickCanvas(canvas, group);
        pickCanvas.setMode(PickInfo.PICK_BOUNDS);
        //initializing listeners
        canvas.addMouseMotionListener(this);
        canvas.addMouseListener(this);
        canvas.addKeyListener(this);
        canvas.addMouseWheelListener(this);
    }
    public void getScene() { //set scene capabilities
        boxTransformGroup //box allow read data
                .setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        boxTransformGroup //box allow change
                .setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//user viewpoint here
        tran.lookAt(new Point3d(0+viewX,0+viewY,2+viewZ),new Point3d(0+viewX,0+viewY,0+viewZ),new Vector3d(0+viewX,1+viewY,0+viewZ));
        tran.invert(); //necessary step
        addLights(group);
//appearance
        Appearance ap = getAppearance(new Color3f(Color.blue));
        ap.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
        ap.setCapability(Appearance.ALLOW_TEXGEN_WRITE);
//create new box
        box = new Box(.2f, .1f, .2f,getAppearance(new Color3f(Color.green)));    
//, Primitive.GENERATE_TEXTURE_COORDS
        box.setCapability(Box.ENABLE_APPEARANCE_MODIFY);
        box.setCapability(Box.GEOMETRY_NOT_SHARED);        
        box.setCapability(Box.ALLOW_LOCAL_TO_VWORLD_READ);
        frontShape = box.getShape(Box.FRONT);
        frontShape.setAppearance(ap);
//box textures
        box.getShape(Box.TOP).setAppearance(getAppearance(Color.magenta));
        box.getShape(Box.BOTTOM).setAppearance(getAppearance(Color.orange)); ;
        box.getShape(Box.RIGHT).setAppearance(getAppearance(Color.red));
        box.getShape(Box.LEFT).setAppearance(getAppearance(Color.green)); 
        box.getShape(Box.BACK).setAppearance(getAppearance(new Color3f(Color.yellow))); 
//init texture on cube + mousebehavior
        frontImage = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D)frontImage.getGraphics();
        g.setColor(new Color(70,70,140));
        g.fillRect(0, 0, imageWidth, imageHeight);
        addTexture(frontImage, frontShape);    
        MouseRotate behavior = new MouseRotate();
        BoundingSphere bounds =
            new BoundingSphere(new Point3d(0.0,0.0,0.0), 50.0);        
        behavior.setTransformGroup(boxTransformGroup);
        boxTransformGroup.addChild(behavior);
        behavior.setSchedulingBounds(bounds);    
        boxTransformGroup.addChild(box);
        group.addChild(boxTransformGroup);
//transformation on cube
        Transform3D sas = new Transform3D();
        boxTransformGroup = universe.getViewingPlatform().getViewPlatformTransform();
        universe.getViewingPlatform().setNominalViewingTransform();
        ViewPlatform myPlatform = new ViewPlatform();
        boxTransformGroup.setTransform(tran);
    }
    public void addTexture(BufferedImage image, Shape3D shape) { //add textures to objects
        frontShape.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
        appearance = shape.getAppearance();
        appearance.setCapability(Appearance.ALLOW_TEXTURE_ATTRIBUTES_WRITE);
        appearance.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
        appearance.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
        changeTexture( texture,  image,  shape);        
        Color3f col = new Color3f(0.0f, 0.0f, 1.0f);
        ColoringAttributes ca = new ColoringAttributes(col,
                ColoringAttributes.NICEST);
        appearance.setColoringAttributes(ca);
        
        }
    
/**
 *affect textures of cube
 */
    public void changeTexture(Texture texture, BufferedImage image, Shape3D shape) {
        loader = new TextureLoader(image, "RGB",
                TextureLoader.ALLOW_NON_POWER_OF_TWO);
        texture = loader.getTexture();
        texture.setBoundaryModeS(Texture.CLAMP_TO_BOUNDARY);
        texture.setBoundaryModeT(Texture.CLAMP_TO_BOUNDARY);
        texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.5f, 0f));
        // Set up the texture attributes
        // could be REPLACE, BLEND or DECAL instead of MODULATE
        Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
        Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
        Color3f red = new Color3f(0.7f, .15f, .15f);
        appearance.setMaterial(new Material(red, black, red, white, 1.0f));         
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.REPLACE);    
        appearance.setTextureAttributes(texAttr);
        appearance.setTexture(texture);
        shape.setAppearance(appearance);
    }
        
/**
 *find current shape and render
 */
    BufferedImage getStartingImage(int i, int width, int height) { //find current shape
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D)image.getGraphics();
        g.setColor(new Color(70,70,140));
        g.fillRect(0, 0, width, height);
        return image;
    }
    
/**
 *add shading and the like
 */
    public static void addLights(BranchGroup group) { //add lights for shading
        Color3f light1Color = new Color3f(0.7f, 0.8f, 0.8f);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
                100.0);
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color,
                light1Direction);
        light1.setInfluencingBounds(bounds);
        group.addChild(light1);
        AmbientLight light2 = new AmbientLight(new Color3f(0.3f, 0.3f, 0.3f));
        light2.setInfluencingBounds(bounds);
        group.addChild(light2);
    }
     
/**
 * find color
 */
    public static Appearance getAppearance(Color color) {
        return getAppearance(new Color3f(color));
    }
     
/**
 *set colors
 */
    public static Appearance getAppearance(Color3f color) {
        Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
        Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
        Appearance ap = new Appearance();
        Texture texture = new Texture2D();
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));
        Material mat = new Material(color, black, color, white, 70f);
        //change color attributes
        ap.setTextureAttributes(texAttr);
        ap.setMaterial(mat);
        ap.setTexture(texture);     
        ColoringAttributes ca = new ColoringAttributes(color,
                ColoringAttributes.NICEST);
        ap.setColoringAttributes(ca);
        return ap;
    }
    @Override
    public void mouseClicked(MouseEvent arg0) {//redundant event
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {//redundant event
    }

    @Override
    public void mouseExited(MouseEvent arg0) {//redundant event
    }

/**
 *mouseheld checker
 */
    @Override
    public void mousePressed(MouseEvent event) {
        System.out.println("mouseheld");
        lastX=-1;
        lastY=-1;
        mouseButton = event.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {    //redundant event    
    }

/**
 *uses mouseheld checker and lets user rotate
 */
    @Override
    public void mouseDragged(MouseEvent event) {  //what happens when you click and hold mouse     
         System.out.println("mouseheld");
         if (mouseButton==MouseEvent.BUTTON1) return; //if not clicked, exit
         Point3d  intersectionPoint = getPosition(event);
         if (Math.abs(intersectionPoint.x) < 0.5 && Math.abs(intersectionPoint.y) < 0.5)  {
             double x = (0.5 + intersectionPoint.x) * imageWidth;
             double y = (0.5 - intersectionPoint.y) * imageHeight;
             //rotate coordinates             
             Graphics2D g = (Graphics2D) frontImage.getGraphics();
             g.setColor( Color.BLACK);
             g.setStroke(new BasicStroke(3));
             int iX = (int)(x + .5);
             int iY = (int)(y + .5);
             if (lastX < 0) {
                 lastX = iX;
                 lastY = iY;
             }
             g.drawLine(lastX, lastY, iX, iY);
             lastX = iX;
             lastY = iY;
             changeTexture(texture, frontImage, frontShape);
         }    
    }  
    
/**
 *arrowkey input to move viewpoints
 */
    @Override
    public void keyPressed(KeyEvent e) { //move viewpoint around
        int keyCode = e.getKeyCode();
        System.out.println(viewX + "," + viewY + "," + viewZ);
        switch( keyCode ) { 
            case KeyEvent.VK_UP:
                viewY+=0.1f;
                break;
            case KeyEvent.VK_DOWN:
                viewY-=0.1f;
                break;
            case KeyEvent.VK_LEFT:
                viewX-=0.1f;
                break;
            case KeyEvent.VK_RIGHT :
                viewX+=0.1f;
                break;
         }
        tran.lookAt(new Point3d(0+viewX,0+viewY,-1+viewZ),new Point3d(viewX,viewY,viewZ),new Vector3d(0+viewX,-1+viewY,0+viewZ));     
        appearance = frontShape.getAppearance();
        boxTransformGroup = universe.getViewingPlatform().getViewPlatformTransform(); //reinit views and renders
        universe.getViewingPlatform().setNominalViewingTransform();
        boxTransformGroup.setTransform(tran);
    } 
    @Override
    public void keyTyped(KeyEvent e){//redundant event
 //nothing here
    }
    @Override
    public void keyReleased(KeyEvent e){//redundant event
    }
    @Override
    public void mouseMoved(MouseEvent arg0) {    //redundant event
    }
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {//redundant event
    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmain;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 *
 * @author andrewliu
 */
public class ProjectMain extends Application {
    double xSize = 1000;
    double ySize = 1000;
    double scale = 1;
    public abstract class Shape {
        public Shape(){
            
        }
        public abstract void scale(double x);
        public abstract void rotate(double x);
        public abstract void color(Color c);
        public abstract void refresh();
    }
    public class Triangle extends Shape{
        private double ax=0;
        private double ay=0;
        private double bx=0;
        private double by=0;
        private double cx=0;
        private double cy=0;
        private double scaleF=1;
        private Line line1 = new Line();
        private Line line2 = new Line();
        private Line line3 = new Line();
        public Triangle(Group g, double aax, double aay, double bbx, double bby, double ccx, double ccy){
            ax = aax;
            ay = aay;
            bx = bbx;
            by = bby;
            cx = ccx;
            cy = ccy;
            line1.setStartX(ax*scaleF+xSize/2);
            line1.setStartY(ay*scaleF+ySize/2);
            line1.setEndX(bx*scaleF+xSize/2);
            line1.setEndY(by*scaleF+ySize/2);
            line2.setStartX(bx*scaleF+xSize/2);
            line2.setStartY(by*scaleF+ySize/2);
            line2.setEndX(cx*scaleF+xSize/2);
            line2.setEndY(cy*scaleF+ySize/2);
            line3.setStartX(cx*scaleF+xSize/2);
            line3.setStartY(cy*scaleF+ySize/2);
            line3.setEndX(ax*scaleF+xSize/2);
            line3.setEndY(ay*scaleF+ySize/2);
            g.getChildren().add(line1);
            g.getChildren().add(line2);
            g.getChildren().add(line3);
        }
        @Override
        public void scale(double scalebale) {
            scaleF = scalebale;
        }

        @Override
        public void rotate(double x) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void color(Color c) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void refresh() {
            line1.setStartX(ax*scaleF+xSize/2);
            line1.setStartY(ay*scaleF+ySize/2);
            line1.setEndX(bx*scaleF+xSize/2);
            line1.setEndY(by*scaleF+ySize/2);
            line2.setStartX(bx*scaleF+xSize/2);
            line2.setStartY(by*scaleF+ySize/2);
            line2.setEndX(cx*scaleF+xSize/2);
            line2.setEndY(cy*scaleF+ySize/2);
            line3.setStartX(cx*scaleF+xSize/2);
            line3.setStartY(cy*scaleF+ySize/2);
            line3.setEndX(ax*scaleF+xSize/2);
            line3.setEndY(ay*scaleF+ySize/2);
        }
        
    }
    public Triangle baboon;
    @Override
    public void start(Stage primaryStage) {
        Button zoomIn = new Button("+"); //init
        Button zoomOut = new Button("-");
        Group r = new Group();
        Triangle baboon = new Triangle(r, -100.0, -100.0, 100.0, -100.0, 0, 100);
        primaryStage.setTitle("Main");
        r.getChildren().addAll(zoomIn, zoomOut);
        Scene scene = new Scene(r,xSize,ySize);
        primaryStage.setScene(scene);
        primaryStage.show();
        zoomIn.setOnAction((ActionEvent e) -> {
            scale+=0.05;
            baboon.scale(scale);
        });
        zoomOut.setOnAction((ActionEvent e) -> {
            scale-=0.05;
            baboon.scale(scale);
        });
        new AnimationTimer(){
            public void handle(long now){
                baboon.refresh();
            }
        }.start();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}

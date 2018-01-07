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
    public class Shape {
        public Shape(){
            
        }
    }
    public class Triangle extends Shape{
        private double ax=0;
        private double ay=0;
        private double bx=0;
        private double by=0;
        private double cx=0;
        private double cy=0;
        private Line line1 = new Line();
        private Line line2 = new Line();
        private Line line3 = new Line();
        public Triangle(){
        }
        public void tri(Group g, double aax, double aay, double bbx, double bby, double ccx, double ccy, double scalebale){
            ax = aax;
            ay = aay;
            bx = bbx;
            by = bby;
            cx = ccx;
            cy = ccy;
            line1.setStartX(ax*scalebale+xSize/2);
            line1.setStartY(ay*scalebale+ySize/2);
            line1.setEndX(bx*scalebale+xSize/2);
            line1.setEndY(by*scalebale+ySize/2);
            line2.setStartX(bx*scalebale+xSize/2);
            line2.setStartY(by*scalebale+ySize/2);
            line2.setEndX(cx*scalebale+xSize/2);
            line2.setEndY(cy*scalebale+ySize/2);
            line3.setStartX(cx*scalebale+xSize/2);
            line3.setStartY(cy*scalebale+ySize/2);
            line3.setEndX(ax*scalebale+xSize/2);
            line3.setEndY(ay*scalebale+ySize/2);
            g.getChildren().add(line1);
            g.getChildren().add(line2);
            g.getChildren().add(line3);
        }
        public void refresh(double scalebale){
            line1.setStartX(ax*scalebale+xSize/2);
            line1.setStartY(ay*scalebale+ySize/2);
            line1.setEndX(bx*scalebale+xSize/2);
            line1.setEndY(by*scalebale+ySize/2);
            line2.setStartX(bx*scalebale+xSize/2);
            line2.setStartY(by*scalebale+ySize/2);
            line2.setEndX(cx*scalebale+xSize/2);
            line2.setEndY(cy*scalebale+ySize/2);
            line3.setStartX(cx*scalebale+xSize/2);
            line3.setStartY(cy*scalebale+ySize/2);
            line3.setEndX(ax*scalebale+xSize/2);
            line3.setEndY(ay*scalebale+ySize/2);
        }
    }
    public Triangle baboon;
    @Override
    public void start(Stage primaryStage) {
        Button zoomIn = new Button("+"); //init
        Button zoomOut = new Button("-");
        zoomIn.setOnAction((ActionEvent e) -> {
            scale-=0.05;
        });
        zoomOut.setOnAction((ActionEvent e) -> {
            scale+=0.05;
        });
        Triangle baboon = new Triangle();
        scale = 1;
        primaryStage.setTitle("Main");
        Group r = new Group();
        r.getChildren().addAll(zoomIn, zoomOut);
        Scene scene = new Scene(r,xSize,ySize);
        primaryStage.setScene(scene);
        baboon.tri(r, -100.0, -100.0, 100.0, -100.0, 0, 100,scale);
        primaryStage.show();
        new AnimationTimer(){
            public void handle(long now){
                baboon.refresh(scale);
            }
        }.start();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}

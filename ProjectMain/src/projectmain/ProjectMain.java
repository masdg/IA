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
import javafx.geometry.Point2D;
import java.awt.geom.*;
import java.awt.event.*;

/**
 *
 * @author andrewliu
 */
public class ProjectMain extends Application {
    double rotateX = 0;
    double rotateY = 0.;
    double xSize = 1000;
    double ySize = 1000;
    Group r = new Group();
    double scale = 1;
    Scene scene = new Scene(r,xSize,ySize);
    public abstract class Shape {
        public Shape(){
            
        }
        public abstract void scale(double x);
        public abstract void rotate(double x);
        public abstract void color(Color c);
        public abstract void refresh();
    }
    public class Quad extends Shape{
        private Double length = 0.;
        private Double height = 0.;
        private Double centerX = 0.;
        private Double centerY = 0.;
        Line line1 = new Line();
        Line line2 = new Line();
        Line line3 = new Line();
        Line line4 = new Line();
        public Quad(Group g, Double lengthh, Double heightt, Double centerXX, Double centerYY){
            length = lengthh;
            height = heightt;
            centerX = centerXX;
            centerY = centerYY;
            line1.setEndX(centerX+Math.sin(rotateX)*length/2);
            line1.setStartX(centerX-Math.sin(rotateX)*length/2);
            line2.setStartX(centerX+Math.sin(rotateX)*length/2);
            line2.setEndX(centerX+Math.sin(rotateX)*length/2);
            line3.setStartX(centerX+Math.sin(rotateX)*length/2);
            line3.setEndX(centerX-Math.sin(rotateX)*length/2);
            line4.setStartX(centerX-Math.sin(rotateX)*length/2);
            line4.setEndX(centerX-Math.sin(rotateX)*length/2);
            line1.setStartY(centerY+Math.sin(rotateY)*height/2);
            line1.setEndY(centerY+Math.sin(rotateY)*height/2);
            line2.setStartY(centerY+Math.sin(rotateY)*height/2);
            line2.setEndY(centerY-Math.sin(rotateY)*height/2);
            line3.setStartY(centerY-Math.sin(rotateY)*height/2);
            line3.setEndY(centerY-Math.sin(rotateY)*height/2);
            line4.setStartY(centerY-Math.sin(rotateY)*height/2);
            line4.setEndY(centerY+Math.sin(rotateY)*height/2);
            g.getChildren().addAll(line1,line2,line3,line4);
        }
        @Override
        public void refresh(){
            line1.setEndX(centerX+Math.sin(rotateX)*length/2);
            line1.setStartX(centerX-Math.sin(rotateX)*length/2);
            line2.setStartX(centerX+Math.sin(rotateX)*length/2);
            line2.setEndX(centerX+Math.sin(rotateX)*length/2);
            line3.setStartX(centerX+Math.sin(rotateX)*length/2);
            line3.setEndX(centerX-Math.sin(rotateX)*length/2);
            line4.setStartX(centerX-Math.sin(rotateX)*length/2);
            line4.setEndX(centerX-Math.sin(rotateX)*length/2);
            line1.setStartY(centerY+Math.sin(rotateY)*height/2);
            line1.setEndY(centerY+Math.sin(rotateY)*height/2);
            line2.setStartY(centerY+Math.sin(rotateY)*height/2);
            line2.setEndY(centerY-Math.sin(rotateY)*height/2);
            line3.setStartY(centerY-Math.sin(rotateY)*height/2);
            line3.setEndY(centerY-Math.sin(rotateY)*height/2);
            line4.setStartY(centerY-Math.sin(rotateY)*height/2);
            line4.setEndY(centerY+Math.sin(rotateY)*height/2);
        }
        public void color(Color c){
        }
        public void rotate(double x){
        }
        public void scale(double x){
        }
    }
//    public class Triangle extends Shape{
//        private double ax=0;
//        private double ay=0;
//        private double bx=0;
//        private double by=0;
//        private double cx=0;
//        private double cy=0;
//        private double scaleF=1;
//        private Line line1 = new Line();
//        private Line line2 = new Line();
//        private Line line3 = new Line();
//        public Triangle(Group g, double aax, double aay, double bbx, double bby, double ccx, double ccy){
//            ax = aax;
//            ay = aay;
//            bx = bbx;
//            by = bby;
//            cx = ccx;
//            cy = ccy;
//            line1.setStartX(ax*scaleF+xSize/2);
//            line1.setStartY(ay*scaleF+ySize/2);
//            line1.setEndX(bx*scaleF+xSize/2);
//            line1.setEndY(by*scaleF+ySize/2);
//            line2.setStartX(bx*scaleF+xSize/2);
//            line2.setStartY(by*scaleF+ySize/2);
//            line2.setEndX(cx*scaleF+xSize/2);
//            line2.setEndY(cy*scaleF+ySize/2);
//            line3.setStartX(cx*scaleF+xSize/2);
//            line3.setStartY(cy*scaleF+ySize/2);
//            line3.setEndX(ax*scaleF+xSize/2);
//            line3.setEndY(ay*scaleF+ySize/2);
//            g.getChildren().add(line1);
//            g.getChildren().add(line2);
//            g.getChildren().add(line3);
//        }
//        @Override
//        public void scale(double scalebale) {
//            scaleF = scalebale;
//        }
//
//        @Override
//        public void rotate(double x) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//        @Override
//        public void color(Color c) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//        @Override
//        public void refresh() {
//            line1.setStartX(ax*scaleF+xSize/2);
//            line1.setStartY(ay*scaleF+ySize/2);
//            line1.setEndX(bx*scaleF+xSize/2);
//            line1.setEndY(by*scaleF+ySize/2);
//            line2.setStartX(bx*scaleF+xSize/2);
//            line2.setStartY(by*scaleF+ySize/2);
//            line2.setEndX(cx*scaleF+xSize/2);
//            line2.setEndY(cy*scaleF+ySize/2);
//            line3.setStartX(cx*scaleF+xSize/2);
//            line3.setStartY(cy*scaleF+ySize/2);
//            line3.setEndX(ax*scaleF+xSize/2);
//            line3.setEndY(ay*scaleF+ySize/2);
//        }
//        
//    }
//    public Triangle baboon;
    public Quad baboon;
    @Override
    public void start(Stage primaryStage) {
        Button zoomIn = new Button("+"); //init
        Button zoomOut = new Button("-");
        Double length = 300.0;
        Double height = 300.0;
        Double centerX = 500.0;
        Double centerY = 500.0;
        Quad baboon = new Quad(r, length, height, centerX, centerY);
//        Triangle baboon = new Triangle(r, -100.0, -100.0, 100.0, -100.0, 0, 100);
        primaryStage.setTitle("Main");
        r.getChildren().addAll(zoomIn, zoomOut);
        primaryStage.setScene(scene);
        primaryStage.show();
        zoomIn.setOnAction((ActionEvent e) -> {
            scale+=0.05;
//            baboon.scale(scale);
        });
        zoomOut.setOnAction((ActionEvent e) -> {
            scale-=0.05;
//            baboon.scale(scale);
        });
        new AnimationTimer(){
            public void handle(long now){
                rotateX+=Math.PI/512;
                rotateY=Math.PI/2;
                baboon.refresh();
            }
        }.start();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}

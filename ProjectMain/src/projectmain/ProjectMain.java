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
    Double[][] base = {
        {1.,0.,0.,0.},
        {0.,1.,0.,0.},
        {0.,0.,1.,0.},
        {0.,0.,0.,1.}
    };
    
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
        private Double[][] points;

        public Quad() {
            this.points = new Double[][]{{0.}, {0.}, {0.}, {1.}};
        }
        public Quad(Group g, Double lengthh, Double heightt, Double centerXX, Double centerYY){
        }
        @Override
        public void refresh(){
        }
        public void color(Color c){
        }
        public void rotate(double x){
        }
        public void scale(double x){
        }
    }
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/**
 *
 * @author marez
 */
public abstract class Character {
    private Polygon character;
    private Point2D movement;
    
    public Character(Polygon pol, int x, int y){
        this.character=pol;
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);
        
        this.movement=new Point2D(0,0);
    }

    public Point2D getMovement() {
        return movement;
    }

    public void setMovement(Point2D movement) {
        this.movement = movement;
    }

    
    public Polygon getCharacter() {
        return character;
    }
    
    public void turnLeft(){
        this.character.setRotate(this.character.getRotate()-5);
    }
    
    public void turnRight(){
        this.character.setRotate(this.character.getRotate()+5);
    }
    
    public void move(){
        this.character.setTranslateX(this.character.getTranslateX()+this.movement.getX());
        this.character.setTranslateY(this.character.getTranslateY()+this.movement.getY());
        
        if (this.character.getTranslateX()<0){
            this.character.setTranslateX(this.character.getTranslateX()+AsteroidsApplication.WIDTH);
        }
        
        if(this.character.getTranslateX()>AsteroidsApplication.WIDTH){
            this.character.setTranslateX(this.character.getTranslateX()%AsteroidsApplication.WIDTH);
        }
        
        if(this.character.getTranslateY()<0){
            this.character.setTranslateY(this.character.getTranslateY()+AsteroidsApplication.HEIGHT);
        }
        
        if(this.character.getTranslateY()>AsteroidsApplication.HEIGHT){
            this.character.setTranslateY(this.character.getTranslateY()%AsteroidsApplication.HEIGHT);
        }
    }
    
    public boolean collide(Character c2){
        Shape collisionArea=Shape.intersect(this.character, c2.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth()!=-1;
    }
    
    public void accelerate(){
        double changeX=Math.cos(Math.toRadians(this.character.getRotate()));
        double changeY=Math.sin(Math.toRadians(this.character.getRotate()));
        
        this.movement=this.movement.add(changeX*0.05, changeY*0.05);
    }
}

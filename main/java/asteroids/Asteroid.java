/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.util.Random;
import javafx.scene.shape.Polygon;

/**
 *
 * @author marez
 */
public class Asteroid extends Character {
    private double  rotationalFactor;
    private boolean alive;

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    
    
    public Asteroid(int x, int y){
        super(new PolygonFactory().createPolygon(), x, y);
    
        Random rand=new Random();
        
        super.getCharacter().setRotate(rand.nextInt(360));
        
        int accelerationAmount=1+rand.nextInt(10);
        for(int i=0; i<accelerationAmount; i++){
            accelerate();
        }
        
        this.rotationalFactor=0.5-rand.nextDouble();
        this.alive=true;
    }
    
    public void move(){
        super.move();
        super.getCharacter().setRotate(super.getCharacter().getRotate()+rotationalFactor);
    }
}

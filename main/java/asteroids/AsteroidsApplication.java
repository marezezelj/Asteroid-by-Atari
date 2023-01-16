package asteroids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AsteroidsApplication extends Application {

    public static int WIDTH=500;
    public static int HEIGHT=300;
    
    public static void main(String[] args) {
        System.out.println("Hello, world!");
        launch(AsteroidsApplication.class);
    }

    public static int partsCompleted() {
        // State how many parts you have completed using the return value of this method
        return 4;
    }

    @Override
    public void start(Stage arg0) throws Exception {
        Pane pane=new Pane();
        Text text=new Text(10,20,"Points: 0");
        pane.setPrefSize(WIDTH, HEIGHT);
        //Point2D movement=new Point2D(1,0);
        
        AtomicInteger points=new AtomicInteger();
        Map<KeyCode, Boolean> pressedKeys=new HashMap<>();
        
        Ship ship=new Ship(WIDTH/2,HEIGHT/2);
        List<Projectile> projectiles=new ArrayList<>();
        List<Asteroid> asteroidi=new ArrayList<Asteroid>();
        for(int i=0; i<5; i++){
            Random rand=new Random();
            Asteroid aster=new Asteroid(rand.nextInt(WIDTH/3), rand.nextInt(HEIGHT));
            asteroidi.add(aster);
        }
        pane.getChildren().add(ship.getCharacter());
        pane.getChildren().add(text);
        asteroidi.forEach((aster)->pane.getChildren().add(aster.getCharacter()));
        
        //asteroid.turnRight();
        //asteroid.turnRight();
        //asteroid.accelerate();
        //asteroid.accelerate();
        
        Scene scena=new Scene(pane);
        
        scena.setOnKeyPressed((event)->{
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });
        
        scena.setOnKeyReleased((event)->{
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });
        
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                if(Math.random()<0.005){
                    Asteroid aster=new Asteroid(WIDTH,HEIGHT);
                    if(!aster.collide(ship)){
                        asteroidi.add(aster);
                        pane.getChildren().add(aster.getCharacter());
                    }
                }
                
                if(pressedKeys.getOrDefault(KeyCode.LEFT, false)){
                    ship.turnLeft();
                }
                if(pressedKeys.getOrDefault(KeyCode.RIGHT, Boolean.FALSE)){
                    ship.turnRight();
                }
                if(pressedKeys.getOrDefault(KeyCode.UP, Boolean.FALSE)){
                    ship.accelerate();
                }
                if(pressedKeys.getOrDefault(KeyCode.SPACE, Boolean.FALSE) && projectiles.size()<3){
                    Projectile project=new Projectile((int)ship.getCharacter().getTranslateX(), (int)ship.getCharacter().getTranslateY());
                    project.getCharacter().setRotate(ship.getCharacter().getRotate());
                    projectiles.add(project);
                    
                    project.accelerate();
                    project.setMovement(project.getMovement().normalize().multiply(3));
                    
                    pane.getChildren().add(project.getCharacter());
                }
                
                ship.move();
                projectiles.forEach((project)->project.move());
                asteroidi.forEach((asteroid)->asteroid.move());
                asteroidi.forEach((asteroid)->{
                    if(ship.collide(asteroid)){
                        text.setText("GAME OVER!");
                        stop();
                    }
                });
                projectiles.forEach((project)->{
                    asteroidi.forEach((asteroid)->{
                        if(project.collide(asteroid)){
                            project.setAlive(false);
                            asteroid.setAlive(false);
                        }
                    });
                    if(!project.isAlive()){
                        text.setText("Points: " + points.addAndGet(1000));
                    }
                });
                
                
                
                projectiles.stream()
                        .filter(project->!project.isAlive())
                        .forEach(project->pane.getChildren().remove(project.getCharacter()));
                projectiles.removeAll(projectiles.stream()
                                        .filter((project)->!project.isAlive())
                                        .collect(Collectors.toList())
                );
                
                asteroidi.stream()
                        .filter(asteroid->!asteroid.isAlive())
                        .forEach(asteroid->pane.getChildren().remove(asteroid.getCharacter()));
                asteroidi.removeAll(asteroidi.stream()
                                    .filter((asteroid)->!asteroid.isAlive())
                                    .collect(Collectors.toList())
                );
                
               
            }
        }.start();
        
        arg0.setScene(scena);
        arg0.setTitle("Asteroids! by Dolph Games");
        arg0.show();
    }

}

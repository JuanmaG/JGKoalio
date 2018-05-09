package net.realKoalio.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.realKoalio.Beer;
import net.realKoalio.Chest;
import net.realKoalio.Espada;
import net.realKoalio.Explosion;
import net.realKoalio.Knight;
import net.realKoalio.Koala;
//import net.realKoalio.puntuacionActor;

public class MainScreen implements Screen {
    Stage stage;
    TiledMap map;
    Chest chest;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    public static Koala koala;
    //private puntuacionActor puntuacion;
    ArrayList<Knight> knight;
    ArrayList<Beer> beer;
    Explosion explosion;
    boolean facing;
    int hp=4;
    float time;
    float time2;
    float time3;
    public Espada espada;
    private Game game=TitleScreen.game;
    Music musica= Gdx.audio.newMusic(Gdx.files.internal("Bass King vs. X-Vertigo feat. Golden Sun - Kings (Matt Nash Remix) RedMusic.pl.mp3"));
    public void crearEspada(){
        espada = new Espada();
        espada.layer = (TiledMapTileLayer) map.getLayers().get("Walls");
        espada.setPosition(koala.getX(),20);
        stage.addActor(espada);
    }
    public void show() {
        map = new TmxMapLoader().load("sin nombre.tmx");
        final float pixelsPerTile = 32;
        renderer = new OrthogonalTiledMapRenderer(map, 1 / pixelsPerTile);
        camera = new OrthographicCamera();
        
        stage = new Stage();
        stage.getViewport().setCamera(camera);

        koala = new Koala();
        koala.layer = (TiledMapTileLayer) map.getLayers().get("Walls");
        koala.setPosition(1, 1);
        
        knight = new ArrayList<Knight>();
        knight.add(new Knight());
        knight.add(new Knight());
        knight.get(knight.size()-1).layer=(TiledMapTileLayer) map.getLayers().get("Walls");
        knight.get(knight.size()-1).setPosition(28,15);
        knight.get(knight.size()-2).layer=(TiledMapTileLayer) map.getLayers().get("Walls");
        knight.get(knight.size()-2).setPosition(40,15);
                
        chest = new Chest();
        chest.layer = (TiledMapTileLayer) map.getLayers().get("Walls");
        chest.setPosition(34, 1);
        
        beer= new ArrayList<Beer>();
        for(int i=0;i<4;i++){
        beer.add(new Beer());
        //beer.get(beer.size()-1).layer = (TiledMapTileLayer) map.getLayers().get("Walls");
        }
        int i=0;
        for(Beer culo: beer){
            i++;
        culo.setPosition(camera.position.x-13+i,17);
        stage.addActor(culo);
        }
        /*puntuacion=new puntuacionActor(new BitmapFont());
        puntuacion.setPosition(camera.position.x+12+i,18);
        puntuacion.puntuacion=0;*/
       for(Knight culo: knight){
            i++;
        stage.addActor(culo);
        }
        
        stage.addActor(chest);
        stage.addActor(koala);
        
        

       /* stage.addActor(puntuacion);*/
    }
    public boolean checkCollision(float delta){
        boolean collision=false;
        boolean hit=false;
        time2+=delta;

        if(koala.borders.overlaps(chest.borders)){
            collision=true;
        }
        if((koala.borders.overlaps(Knight.borders))||(koala.borders.overlaps(espada.borders))){
            hit = true;
            //crearEspada();
            System.out.println("choca");
            if(hp>0){
                if(hit && time2>1){
                    beer.get(hp-1).remove(); 
                    hp--;
                    time=0;
                }
            }
                if(hp==0){
                    explosion = new Explosion();
                    explosion.layer = (TiledMapTileLayer) map.getLayers().get("Walls");
                    explosion.setPosition(koala.getX(),koala.getY());
                    stage.addActor(explosion);
                    koala.remove();
                    
                       
                    if(time>1){
                    game.setScreen(new DeathScreen(game));
                    musica.stop();
                    }
                }
            }
        
       return collision ;
    }
    public void render(float delta) {
        time+=delta;
        time3+=delta;
        Random ran = new Random();
        int x = ThreadLocalRandom.current().nextInt(0, 1000 + 1);
        int i=0;
        Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        musica.play();
        if(time3>1){
            crearEspada();
            time3=0;
        }
      if (koala.getX()<14){
            camera.position.x=14;
            for(Beer culo: beer){
                i++;
                culo.setPosition(camera.position.x-13+i,17);
        }
      }
        else if(koala.getX()>45){
            camera.position.x=45;
            for(Beer culo: beer){
                i++;
                culo.setPosition(camera.position.x-13+i,17);
            }
        }
        else{
            camera.position.x = koala.getX();
            for(Beer culo: beer){
                i++;
                culo.setPosition(camera.position.x-13+i,17);
            }
        }
        
        camera.update();
        if(time>1){

       if(checkCollision(delta)==true){
           System.out.println(chest.open);
           chest.open=true; 
        }
        }
        renderer.setView(camera);
        renderer.render();

        stage.act(delta);
        stage.draw();
    }

    public void dispose() {
    }

    public void hide() {
    }

    public void pause() {
    }

    public void resize(int width, int height) {
        camera.setToOrtho(false, 20 * width / height, 20);
    }

    public void resume() {
    }
}

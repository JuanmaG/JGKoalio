/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.realKoalio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 *
 * @author Jero
 */
public class Explosion extends Image{
    public Animation stand;
    public Texture idle;
    public TextureRegion jump;
    public Rectangle borders=new Rectangle();
    public Animation walk;
    public Boolean open=false;
    public float time = 0;
    public float xVelocity = 0;
    public float yVelocity = 0;
    public boolean canJump = false;
    public boolean isFacingRight = true;
    public TiledMapTileLayer layer;

    final float GRAVITY = -2.5f;
    final float MAX_VELOCITY = 10f;
    final float DAMPING = 0.87f;

    public Explosion() {
        final float width = 18;
        final float height = 26;
        this.setSize(1, height / width);
        borders.setSize(1, height / width);
        

        
        TextureRegion[][] grid = new TextureRegion[1][6];
        Texture text1=new Texture("explosion1.png");
        Texture text2=new Texture("explosion2.png");
        Texture text3=new Texture("explosion3.png");
        Texture text4=new Texture("explosion4.png");
        Texture text5=new Texture("explosion5.png");
        Texture text6=new Texture("explosion6.png");
        TextureRegion idle1= new TextureRegion(text1);
        TextureRegion idle2= new TextureRegion(text2);
        TextureRegion idle3= new TextureRegion(text3);
        TextureRegion idle4= new TextureRegion(text4);
        TextureRegion idle5= new TextureRegion(text5);
        TextureRegion idle6= new TextureRegion(text6);
        grid[0][0]=idle1;
        grid[0][1]=idle2;
        grid[0][2]=idle3;
        grid[0][3]=idle4;
        grid[0][4]=idle5;
        grid[0][5]=idle6;


        
       stand = new Animation(0.15f,grid[0][0],grid[0][1],grid[0][2],grid[0][3],grid[0][4],grid[0][5]);
        stand.setPlayMode(Animation.PlayMode.NORMAL);

    }

    public void act(float delta) {
        time = time + delta;
       /*  yVelocity = yVelocity + GRAVITY;

        float x = this.getX();
        float y = this.getY();
        float xChange = xVelocity * delta;
        float yChange = yVelocity * delta;

        if (canMoveTo(x + xChange, y, false) == false) {
            xVelocity = xChange = 0;
        }

        if (canMoveTo(x, y + yChange, yVelocity > 0) == false) {
            canJump = yVelocity < 0;
            yVelocity = yChange = 0;
        }

        this.setPosition(x + xChange, y + yChange);

        xVelocity = xVelocity * DAMPING;
        if (Math.abs(xVelocity) < 0.5f) {
            xVelocity = 0;
        }*/
    }

    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;
            frame = stand.getKeyFrame(time);
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());

        borders.setPosition(this.getX(),this.getY());
        
    }

    private boolean canMoveTo(float startX, float startY, boolean shouldDestroy) {
        float endX = startX + this.getWidth();
        float endY = startY + this.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                if (layer.getCell(x, y) != null) {
                    if (shouldDestroy) {
                        layer.setCell(x, y, null);
                    }
                    return false;
                }
                y = y + 1;
            }
            x = x + 1;
        }

        return true;
    }
}
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
public class Espada extends Image{
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

    final float GRAVITY = -1f;
    final float MAX_VELOCITY = 10f;
    final float DAMPING = 0.87f;

    public Espada() {
        final float width = 18;
        final float height = 26;
        this.setSize(2, height*2 / width);
        borders.setSize(1, height / width);
        

        
        TextureRegion[][] grid = new TextureRegion[1][3];
        Texture text1=new Texture("sword2_orange.png");
        Texture text2=new Texture("chest2.png");
        Texture text3=new Texture("chest3.png");
        TextureRegion idle1= new TextureRegion(text1);
        TextureRegion idle2= new TextureRegion(text2);
        TextureRegion idle3= new TextureRegion(text3);
        grid[0][0]=idle1;
        grid[0][1]=idle2;
        grid[0][2]=idle3;

        this.jump= new TextureRegion(text1);
       /*stand = new Animation(0.15f,grid[0][0],grid[0][1],grid[0][2]);
        stand.setPlayMode(Animation.PlayMode.NORMAL);*/

    }

    public void act(float delta) {
        time = time + delta;
       yVelocity = yVelocity + GRAVITY;

        float x = this.getX();
        float y = this.getY();
        float xChange = xVelocity * delta;
        float yChange = yVelocity * delta;


        this.setPosition(x + xChange, y + yChange);

        xVelocity = xVelocity * DAMPING;
        if (Math.abs(xVelocity) < 0.5f) {
            xVelocity = 0;
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;

        
        
            frame = jump;
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




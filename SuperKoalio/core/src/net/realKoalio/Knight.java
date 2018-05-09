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
import net.realKoalio.screens.MainScreen;
import static net.realKoalio.screens.MainScreen.koala;

/**
 *
 * @author Jero
 */
public class Knight extends Image {
    public Animation stand;
    public TextureRegion jump;
    public static Rectangle borders=new Rectangle();
    public Animation walk;

    public float time = 0;
    public float xVelocity = 0;
    public float yVelocity = 0;
    public boolean canJump = false;
    public boolean isFacingRight = true;
    public TiledMapTileLayer layer;

    final float GRAVITY = -2.5f;
    final float MAX_VELOCITY = 10f;
    final float DAMPING = 0.87f;

    public Knight() {
        final float width = 15;
        final float height = 40;
        this.setSize(3, height / width);

        borders.setSize(2, height / width);
        TextureRegion[][] grid = new TextureRegion[1][16];
        Texture text1=new Texture("knight-frame16.png");
        Texture text2=new Texture("knight-frame16.png");
        Texture text3=new Texture("knight-frame19.png");
        Texture text4=new Texture("knight-frame20.png");
        Texture text5=new Texture("run(5).png");
        Texture text6=new Texture("run(6).png");
        Texture text7=new Texture("run(7).png");
        Texture text8=new Texture("run(8).png");
        
        Texture text9=new Texture("idle(1).png");
        Texture text10=new Texture("idle(2).png");
        Texture text11=new Texture("idle(3).png");
        Texture text12=new Texture("idle(4).png");
        Texture text13=new Texture("idle(5).png");
        Texture text14=new Texture("idle(6).png");
        Texture text15=new Texture("idle(7).png");
        Texture text16=new Texture("idle(8).png");
        Texture text17 = new Texture("jump.png");
        TextureRegion idle1=new TextureRegion(text1);
        TextureRegion idle2=new TextureRegion(text2);
        TextureRegion idle3=new TextureRegion(text3);
        TextureRegion idle4=new TextureRegion(text4);
        TextureRegion idle5=new TextureRegion(text5);
        TextureRegion idle6=new TextureRegion(text6);
        TextureRegion idle7=new TextureRegion(text7);
        TextureRegion idle8=new TextureRegion(text8);
        
        TextureRegion idle9=new TextureRegion(text9);
        TextureRegion idle10=new TextureRegion(text10);
        TextureRegion idle11=new TextureRegion(text11);
        TextureRegion idle12=new TextureRegion(text12);
        TextureRegion idle13=new TextureRegion(text13);
        TextureRegion idle14=new TextureRegion(text14);
        TextureRegion idle15=new TextureRegion(text15);
        TextureRegion idle16=new TextureRegion(text16);
        
        grid[0][0]=idle1;
        grid[0][1]=idle2;
        grid[0][2]=idle3;
        grid[0][3]=idle4;
        grid[0][4]=idle5;
        grid[0][5]=idle6;
        grid[0][6]=idle7;
        grid[0][7]=idle8;
        grid[0][8]=idle9;
        grid[0][9]=idle10;
        grid[0][10]=idle11;
        grid[0][11]=idle12;
        grid[0][12]=idle13;
        grid[0][13]=idle14;
        grid[0][14]=idle15;
        grid[0][15]=idle16;
        TextureRegion koalaTexture = new TextureRegion(text1);
        
        jump = koalaTexture;
        walk = new Animation(0.15f, grid[0][0],grid[0][1],grid[0][2], grid[0][3]);
        walk.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void act(float delta) {
        time = time + delta;

           if (koala.getX() - this.getX() < 8) {
            if (koala.getX() - this.getX() < 0) {
                this.xVelocity--;
                isFacingRight = false;
            } else {
                this.xVelocity++;
                isFacingRight = true;
            }
        }
        yVelocity = yVelocity + GRAVITY;

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
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;
       
       if (yVelocity != 0) {
            frame = jump;
        } else if (xVelocity != 0) {
            frame = walk.getKeyFrame(time);
        } else {
            frame = jump;
        }



        if (isFacingRight) {
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } else {
            batch.draw(frame, this.getX() + this.getWidth(), this.getY(), -1 * this.getWidth(), this.getHeight());
        }
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
                        //layer.setCell(x, y, null);
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


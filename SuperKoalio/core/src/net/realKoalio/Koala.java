package net.realKoalio;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class Koala extends Image {
    public Animation stand;
    public Animation attack;
    public TextureRegion jump;
    public TextureRegion beer;
    public Rectangle borders=new Rectangle();
    public Animation walk;

    public float time = 0;
    public float xVelocity = 0;
    public float yVelocity = 0;
    public boolean canJump = false;
    public boolean isFacingRight = true;
    public TiledMapTileLayer layer;

    final float GRAVITY = -2.5f;
    final float MAX_VELOCITY = 10f;
    final float DAMPING = 0.9f;

    public Koala() {
        final float width = 18;
        final float height = 26;
        this.setSize(1.5f, height / width);

        borders.setSize(1f, height / width);
        TextureRegion[][] grid = new TextureRegion[1][17];
        Texture text1=new Texture("run1.png");
        Texture text2=new Texture("run2.png");
        Texture text3=new Texture("run3.png");
        Texture text4=new Texture("run4.png");
        Texture text5=new Texture("run5.png");
        Texture text6=new Texture("run6.png");
        Texture text7=new Texture("run7.png");
        Texture text8=new Texture("run8.png");
        
        Texture text9=new Texture("idle1.png");
        Texture text10=new Texture("idle2.png");
        Texture text11=new Texture("idle3.png");
        Texture text12=new Texture("idle4.png");
        Texture text13=new Texture("idle5.png");
        Texture text14=new Texture("idle6.png");
        Texture text15=new Texture("idle7.png");
        Texture text16=new Texture("idle8.png");
        Texture text17 = new Texture("jump.png");
        Texture text18 = new Texture("beer.png");
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
        TextureRegion idle17=new TextureRegion(text18);
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
        grid[0][16]=idle17;
        TextureRegion koalaTexture = new TextureRegion(text17);
        stand = new Animation(0.15f, grid[0][8],grid[0][9],grid[0][10], grid[0][11], grid[0][12], grid[0][13], grid[0][14], grid[0][15]);
        stand.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        jump = koalaTexture;
        walk = new Animation(0.15f, grid[0][0],grid[0][1],grid[0][2], grid[0][3], grid[0][4], grid[0][5], grid[0][6], grid[0][7]);
        walk.setPlayMode(Animation.PlayMode.LOOP);
        beer=grid[0][16];
    }

    public void act(float delta) {
        time = time + delta;

        boolean upTouched = Gdx.input.isTouched() && Gdx.input.getY() < Gdx.graphics.getHeight() / 2;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || upTouched) {
            if (canJump) {
                yVelocity = yVelocity + MAX_VELOCITY * 4;
            }
            canJump = false;
        }

        boolean leftTouched = Gdx.input.isTouched() && Gdx.input.getX() < Gdx.graphics.getWidth() / 3;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || leftTouched) {
            xVelocity = -1 * MAX_VELOCITY;
            isFacingRight = false;
        }

        boolean rightTouched = Gdx.input.isTouched() && Gdx.input.getX() > Gdx.graphics.getWidth() * 2 / 3;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || rightTouched) {
            xVelocity = MAX_VELOCITY;
            isFacingRight = true;
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
            frame = stand.getKeyFrame(time);
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

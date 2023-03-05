package entity.player;

import entity.Entity;
import tile.TileManager;
import window.gamepanel.GamePanel;
import window.keyhandler.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    private KeyHandler keyH;
    private BufferedImage image;

    public Player(KeyHandler keyH) {
        super();

        path = "player";
        upSprites = getEntityImage("up0.png", "up1.png", "up2.png");
        downSprites = getEntityImage("down0.png", "down1.png", "down2.png");
        leftSprites = getEntityImage("left0.png", "left1.png", "left2.png");
        rightSprites = getEntityImage("right0.png", "right1.png", "right2.png");

        image = downSprites[position];
        direction = Direction.Down;
        x = 640;
        y = 360;

        speed = 4;
        this.keyH = keyH;
    }
    public void setDefaultValues() {

        direction = Direction.Down;
        x = 100;
        y = 100;
        speed = 2;
    }

    @Override
    public void draw(Graphics2D g2) {
        switch (direction) {

            case Up -> {
                image = upSprites[position];
                break;
            }
            case Down -> {
                image = downSprites[position];
                break;
            }
            case Left -> {
                image = leftSprites[position];
                break;
            }
            case Right -> {
                image = rightSprites[position];
                break;
            }

        }
        g2.drawImage(image, (GamePanel.SCREEN_WIDTH - GamePanel.TILE_SIZE)/2, (GamePanel.SCREEN_HIGH - GamePanel.TILE_SIZE)/2,
                GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
    }
    private void getDir() {

        if(keyH.upPressed) {
            direction = Direction.Up;

        } else if(keyH.downPressed) {
            direction = Direction.Down;

        } else if(keyH.rightPressed) {
            direction = Direction.Right;

        } else if (keyH.leftPressed) {
            direction = Direction.Left;

        } else {
            isMoved = false;
            position = 0;
            return;
        }
        isMoved = true;

    }

    @Override
    public void update(TileManager tm) {

        getDir();

        if(collision(tm)) {
            isMoved = false;
            return;
        }

        if(!isMoved) return;

        switch (direction) {
            case Up -> {
                y -= speed;
                break;
            }
            case Down -> {
                y += speed;
                break;
            }
            case Right -> {
                x += speed;
                break;
            }
            case Left -> {
                x -= speed;
                break;
            }
        }
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

}

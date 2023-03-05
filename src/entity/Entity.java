package entity;

import tile.TileManager;
import window.gamepanel.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Entity implements Runnable {

    protected int x, y;
    protected int speed;
    protected String path;
    protected Direction direction;
    protected int position;
    protected boolean isMoved;

    protected enum Direction {
        Up,
        Down,
        Left,
        Right
    }
    protected Thread imgPosition;

    public Entity() {

        imgPosition = new Thread(this::run);
        imgPosition.start();

    }

    @Override
    public void run() {
        while (imgPosition != null) {

            try {
                if (isMoved) {
                    position = position % (upSprites.length - 1) + 1;
                    Thread.sleep(300);

                } else {
                    position = 0;
                    Thread.sleep(50);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void update(TileManager tm);

    protected BufferedImage[] leftSprites;
    protected BufferedImage[] rightSprites;
    protected BufferedImage[] upSprites;
    protected BufferedImage[] downSprites;

    public abstract void draw(Graphics2D g2);

    public BufferedImage[] getEntityImage(String... namesImg) {

        BufferedImage[] array = new BufferedImage[namesImg.length];

        try {
            for(int i = 0; i < namesImg.length; i++) {

                array[i] = ImageIO
                        .read(Objects.requireNonNull(getClass()
                        .getResourceAsStream("/entity/" + path + "/" + namesImg[i])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean collision(TileManager tm) {

        switch (direction) {
            case Up -> {
                return tm.tileCollision((x)/GamePanel.TILE_SIZE, (y - 24)/GamePanel.TILE_SIZE);
            }
            case Down -> {
                return tm.tileCollision((x)/GamePanel.TILE_SIZE, (y + 24)/GamePanel.TILE_SIZE);
            }
            case Left -> {
                return tm.tileCollision((x - 24)/GamePanel.TILE_SIZE, (y)/GamePanel.TILE_SIZE);
            }
            case Right -> {
                return tm.tileCollision((x + 24)/GamePanel.TILE_SIZE, (y)/GamePanel.TILE_SIZE);
            }
            default -> {
                return false;
            }
        }
    }

}

package tile;

import window.gamepanel.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tile {

    protected BufferedImage image;
    protected String path;
    protected boolean collision = false;

    public Tile(String path) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/tile/" + path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isCollision() {
        return collision;
    }

    public BufferedImage getImage() {
        return image;
    }

}

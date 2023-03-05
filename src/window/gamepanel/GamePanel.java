package window.gamepanel;

import entity.Entity;
import entity.player.Player;
import tile.Grass;
import tile.Tile;
import tile.TileManager;
import window.keyhandler.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GamePanel extends JPanel implements Runnable{

    //Screen setting
    public final static int ORIGINAL_TILE_SIZE = 16;
    public final static int SCALE = 3;
    public final static int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;

    public final static int MAX_SCREEN_COL = 16;
    public final static int MAX_SCREEN_ROW = 12;
    public final static int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;

    public final static int SCREEN_HIGH = TILE_SIZE * MAX_SCREEN_ROW;

    private int worldX;
    private int worldY;

    private int sizeWorldX;
    private int sizeWorldY;

    int FPS = 60;

    public static final KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    //Getters for getting screen settings

    ArrayList<Entity> entities;
    TileManager tileManager;

    Player pl;

    //The constructor get array of entities, which it's setting
    public GamePanel(Entity... entities) {

        this.entities = new ArrayList<>(Arrays.stream(entities).toList());
        this.pl = new Player(this.keyH);
        this.entities.add(pl);
        HashMap<Integer, Tile> tiles = new HashMap<>();

        tileManager = new TileManager(this);
        tileManager.readFile("map.txt");

        sizeWorldX = tileManager.getXTiles() * TILE_SIZE;
        sizeWorldY = tileManager.getYTiles() * TILE_SIZE;

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HIGH));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        this.addKeyListener(keyH);
        this.setFocusable(true);
        startGameThread();
        openWindowGame();
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getWorldX() {
        return worldX;
    }
    public int getWorldY() {
        return worldY;
    }

    public int getSizeWorldX() {
        return sizeWorldX;
    }

    public int getSizeWorldY() {
        return sizeWorldY;
    }

    public void openWindowGame() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("My Game");

        window.add(this);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }
    //
    @Override
    public void run() {

        double drawInterval = 1_000_000_000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) {

            repaint();
            worldY = pl.getY();
            worldX = pl.getX();
            if(worldY == 0) worldY ++;
            if(worldX == 0) worldX ++;

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1_000_000;

                if(remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void paintComponent(Graphics g) {
        //Count determinate when to change image position

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        tileManager.drawEnvironment(g2);
        g2.setColor(Color.WHITE);
        for(Entity entity: entities) {
            entity.update(tileManager);
            entity.draw(g2);
        }
        g2.dispose();
    }
}

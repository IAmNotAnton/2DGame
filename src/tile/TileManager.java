package tile;

import window.gamepanel.GamePanel;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TileManager {

    private GamePanel gp;
    private HashMap<Integer, Tile> tiles;
    private ArrayList<int[]> layoutTiles;

    public TileManager(GamePanel gp) {

        tiles = new HashMap<>();

        tiles.put(0, new Grass());
        tiles.put(1, new Tree());
        tiles.put(2, new Sea());

        this.gp = gp;
    }

    public void readFile(String path) {

        layoutTiles = new ArrayList<>();
        File file = new File("D:\\My2dGame2\\src\\res\\maps\\map.txt");
        FileReader fileReader = null;
        InputStream in = null;
        String map = "";
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {

                String[] lineChars = line.split(" ");
                int[] lineNums = new int[lineChars.length];
                for(int i = 0; i < lineNums.length; i++) {
                    lineNums[i] = Integer.parseInt(lineChars[i]);
                }
                layoutTiles.add(lineNums);
                line = bufferedReader.readLine();

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public boolean tileCollision(int x, int y) {
        return tiles.get(layoutTiles.get(y)[x]).isCollision();
    }

    public void drawEnvironment(Graphics2D g2) {

        int xRender = gp.getWorldX() - GamePanel.SCREEN_WIDTH/2;
        int yRender = gp.getWorldY() - GamePanel.SCREEN_HIGH/2;

        int xMinTilesRender = xRender / GamePanel.TILE_SIZE;
        if(xMinTilesRender < 0) xMinTilesRender = 0;

        int yMinTilesRender = yRender / GamePanel.TILE_SIZE;
        if(yMinTilesRender < 0) yMinTilesRender = 0;

        int xMaxTilesRender = xMinTilesRender + 17;
        if(xMaxTilesRender >= layoutTiles.get(0).length) xMaxTilesRender = layoutTiles.get(0).length - 1;

        int yMaxTilesRender = yMinTilesRender + 13;
        if(yMaxTilesRender >= layoutTiles.size()) yMaxTilesRender = layoutTiles.size() - 1;

        for(int y = yMinTilesRender; y < yMaxTilesRender; y++) {

            for(int x = xMinTilesRender; x < xMaxTilesRender; x++) {

                if(tiles.containsKey(layoutTiles.get(y)[x])) {
                    g2
                            .drawImage(tiles.get(layoutTiles.get(y)[x])
                            .getImage(), x * GamePanel.TILE_SIZE - xRender, y * GamePanel.TILE_SIZE - yRender
                                    , GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
                }
            }
        }
    }
    public int getYTiles() {
        return layoutTiles.size();
    }
    public int getXTiles() {
        return layoutTiles.get(0).length;
    }
}

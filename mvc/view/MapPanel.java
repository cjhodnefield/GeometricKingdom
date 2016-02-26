package GeometricKingdom.mvc.view;

import GeometricKingdom.mvc.controller.Game;
import GeometricKingdom.mvc.model.*;
import GeometricKingdom.mvc.model.enemy.*;
import GeometricKingdom.mvc.model.floater.*;
import GeometricKingdom.mvc.model.weapon.Web;

import java.awt.*;
import java.util.*;

/**
 * Created by Jonathan on 11/21/2015.
 */
public class MapPanel {
    public static enum MapZone {
        FOREST, CAVE, CASTLE, FINAL_BOSS
    }

    public static enum PanelType {
        HOME, EXIT, TETRIS, HEX, ALIEN, MISSILE, ITEM, BOSS, FINAL_BOSS
    }

    public static String[] levelText = new String[] {"LEVEL 1", "TETRIS FOREST", "LEVEL 2", "OCTARACHNID CAVE", "LEVEL 3", "CASTLE GEO"};

    private String panelName = " ";

    private MapZone mMapZone;
    private PanelType mPanelType;
    private String entranceSide = "";
    private String bossDir;
    private boolean bLocked = true;
    private boolean bDiscovered = false;
    private boolean bObstacles = false;
    private boolean bPowerUpDispensed = true;

    private String sDisplayString;
    private ArrayList<String> newLevelText = new ArrayList<String>();
    private MapPanel north = null;
    private MapPanel east = null;
    private MapPanel west = null;
    private MapPanel south = null;
    private ArrayList<Movable> mapPanelFoes = new ArrayList<GeometricKingdom.mvc.model.Movable>(200);
    private ArrayList<GeometricKingdom.mvc.model.Movable> mapPanelDebris = new ArrayList<GeometricKingdom.mvc.model.Movable>(300);
    private ArrayList<GeometricKingdom.mvc.model.Movable> mapPanelObstacles = new ArrayList<GeometricKingdom.mvc.model.Movable>(300);
    private ArrayList<GeometricKingdom.mvc.model.Movable> mapPanelFloaters = new ArrayList<GeometricKingdom.mvc.model.Movable>(300);

    public MapPanel(MapZone mapZone, PanelType panelType) {
        this(mapZone, panelType, "north");
    }

    public MapPanel(MapZone mapZone, PanelType panelType, String entranceSide) {
        mMapZone = mapZone;
        mPanelType = panelType;
        this.entranceSide = entranceSide;

        switch (panelType) {
            case HOME:
                newLevelText.add(levelText[mapZone.ordinal() * 2]);
                newLevelText.add(levelText[(mapZone.ordinal() * 2) + 1]);
                bPowerUpDispensed = true;
                createHomePanel();
                break;
            case EXIT:
                bPowerUpDispensed = true;
                createExitPanel();
                break;
            case ITEM:
                createItemPanel();
                break;
            case BOSS:
                createBossPanel();
                break;
        }
        spawnFoes();
    }

    public MapZone getMapZone() {
        return mMapZone;
    }

    public PanelType getPanelType() {
        return mPanelType;
    }

    public boolean getLocked() {
        return bLocked;
    }

    public boolean getDiscovered() {
        return bDiscovered;
    }

    public boolean getPowerUpDispensed() {
        return bPowerUpDispensed;
    }

    public String getDisplayString() {
        return sDisplayString;
    }

    public MapPanel getNorth() {
        return north;
    }

    public MapPanel getEast() {
        return east;
    }

    public MapPanel getWest() {
        return west;
    }

    public MapPanel getSouth() {
        return south;
    }

    public String getBossDir() {
        return bossDir;
    }

    public void setBossDir(String bossDir) {
        this.bossDir = bossDir;
    }

    public String getPanelName() {
        return panelName;
    }

    public ArrayList<Movable> getMapPanelFoes() {
        return mapPanelFoes;
    }

    public ArrayList<Movable> getMapPanelDebris() {
        return mapPanelDebris;
    }

    public ArrayList<Movable> getMapPanelFloaters() {
        return mapPanelFloaters;
    }

    public ArrayList<Movable> getMapPanelObstacles() {
        return mapPanelObstacles;
    }

    public ArrayList<String> getNewLevelText() {
        return newLevelText;
    }

    public void setLocked(boolean locked) {
        bLocked = locked;
    }

    public void setDiscovered(boolean discovered) {
        bDiscovered = discovered;
    }

    public void setPowerUpDispensed(boolean powerUpDispensed) {
        this.bPowerUpDispensed = powerUpDispensed;
    }

    public void setDisplayString(String displayString) {
        sDisplayString = displayString;
    }

    public void setNorth(MapPanel north) {
        this.north = north;
    }

    public void setEast(MapPanel east) {
        this.east = east;
    }

    public void setWest(MapPanel west) {
        this.west = west;
    }

    public void setSouth(MapPanel south) {
        this.south = south;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    // FOE SPAWNING METHODS //
    private void spawnFoes() {
        if (mMapZone != MapZone.CASTLE) {
            switch (mPanelType) {
                case TETRIS:
                    spawnTetrisBlocks();
                    break;
                case ALIEN:
                    spawnAliens();
                    break;
                case HEX:
                    spawnHexagons();
                    break;
                case MISSILE:
                    spawnMissileNodes();
                    break;
            }
        } else {
            if (mPanelType != PanelType.HOME && mPanelType != PanelType.EXIT && mPanelType != PanelType.ITEM && mPanelType != PanelType.BOSS) {
                int rand = Game.R.nextInt(3);
                if (rand == 0) {
                    spawnAliens();
                    spawnHexagons();
                } else if (rand == 1) {
                    spawnHexagons();
                    spawnMissileNodes();
                } else {
                    spawnAliens();
                    spawnMissileNodes();
                }
            }
        }
        if (mMapZone == MapZone.FOREST && mPanelType != PanelType.TETRIS && mPanelType != PanelType.BOSS && mPanelType != PanelType.HOME && mPanelType != PanelType.EXIT) {
            spawnTetrisBlocks();
        }
    }

    public void spawnTetrisBlocks() {
        int direction = Game.R.nextInt(2);
        int x = 0;
        if (direction == 0) {
            x = Game.DIM.width / 9;
        } else {
            x = Game.DIM.height / 9;
        }
        int[] Coords = new int[] {2*x, 3*x, 6*x, 7*x};
        for (int i = 0; i < Coords.length; i++) {
            int dir = Game.R.nextInt(2);
            TetrisBlock t = new TetrisBlock(Game.R.nextInt(2) + 1);
            if (direction == 0) {
                t.setCenter(new Point(Coords[i], Game.DIM.height / 2));
                if (dir == 0) {
                    t.setDeltaY(t.getSpd());
                } else {
                    t.setDeltaY(-1 * t.getSpd());
                }
            } else {
                t.setCenter(new Point(Game.DIM.width / 2, Coords[i]));
                if (dir == 0) {
                    t.setDeltaX(t.getSpd());
                } else {
                    t.setDeltaX(-1 * t.getSpd());
                }
            }
            t.setColor(new Color(40, 140, 60));
            mapPanelObstacles.add(t);
        }
    }

    public void spawnAliens() {
        int size = 4;
        Point[][] grid = Cc.getInstance().generatePointGrid(size);

        for (int i = 1; i < size; i++) {
            for (int j = 1; j < size; j++) {
                int rand = Game.R.nextInt(10);
                int bool = Game.R.nextInt(2);
                if (rand < 4 + mMapZone.ordinal() + 1) {
                    Alien a = new Alien();
                    a.setCenter(grid[i][j]);
                    if (bool == 1) {
                        a.setDeltaX(a.getSpd());
                    } else {
                        a.setDeltaX(-a.getSpd());
                    }
                    a.modStats(mMapZone);
                    mapPanelFoes.add(a);
                }
            }
        }
    }

    public void spawnHexagons() {
        int size = 4;
        Point[][] grid = Cc.getInstance().generatePointGrid(size);

        for (int i = 1; i < size; i++) {
            for (int j = 1; j < size; j++) {
                int rand = Game.R.nextInt(10);
                if (rand < 6 + mMapZone.ordinal() + 1) {
                    Hexagon hex = new Hexagon();
                    hex.setCenter(grid[i][j]);
                    hex.modStats(mMapZone);
                    mapPanelFoes.add(hex);
                }
            }
        }
    }

    public void spawnMissileNodes() {
        int w = Game.DIM.width;
        int h = Game.DIM.height;
        int b = Game.WALL_THICKNESS;

        int x = w / 9;
        int y = h / 9;
        int[] xCoords = new int[] {2*x, 3*x, 6*x, 7*x};
        int[] yCoords = new int[] {2*y, 3*y, 6*y, 7*y};
        int[] horiz = new int[] {0 + b, w - b};
        int[] vert = new int[] {0 + b, h - b};

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < xCoords.length; j++) {
                int bool = Game.R.nextInt(4);
                if (bool < mMapZone.ordinal() + 1) {
                    MissileCommand mcv = new MissileCommand();
                    mcv.setCenter(new Point(xCoords[j], vert[i]));
                    mcv.modStats(mMapZone);
                    mapPanelFoes.add(mcv);
                }
                bool = Game.R.nextInt(4);
                if (bool == mMapZone.ordinal() + 1) {
                    MissileCommand mcv = new MissileCommand();
                    mcv.setCenter(new Point(horiz[i], yCoords[j]));
                    mcv.modStats(mMapZone);
                    mapPanelFoes.add(mcv);
                }
            }
        }

//        int size = 10;
//        int b = Game.WALL_THICKNESS;
//        Point[][] grid = Cc.getInstance().generatePointGrid(size);
//
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                int bool = Game.R.nextInt(2);
//                if (bool == 1) {
//                    if (((i == 0 || i == size) && (j == 2 || j == 3 || j == 6 || j == 7)) ||
//                            ((j == 0 || j == size) && (i == 2 || i == 3 || i == 6 || i == 7))) {
//                        MissileCommand mcv = new MissileCommand();
//                        mcv.setCenter(grid[i][j]);
//                        mcv.modStats(mMapZone);
//                        mapPanelFoes.add(mcv);
//                    }
//                }
//            }
//        }
    }

    private Point randomCenter(int r) {
        int width = Game.DIM.width;
        int height = Game.DIM.height;
        double advR = Cc.getInstance().getAdventurer().getRadius() * 1.5;

        Point north = new Point(width / 2, 0);
        Point south = new Point(width / 2, height);
        Point east = new Point(width, height / 2);
        Point west = new Point(0, height / 2);

        Point p = north;
        while ((p.distance(north) < advR || p.distance(south) < advR || p.distance(east) < advR || p.distance(west) < advR) ||
                (p.x < 1.5*r || p.y < 1.5*r || p.x > width - 1.5*r || p.y > height - 1.5*r)) {
            int xCoord = Game.R.nextInt(width - r) + r;
            int yCoord = Game.R.nextInt(height - r) + r;
            p = new Point(xCoord, yCoord);
        }
        return p;
    }

    private void createHomePanel() {
        if (mMapZone != MapZone.FOREST) {
            MapZone previousMapZone = MapZone.values()[mMapZone.ordinal() - 1];
            Exit entrance = new Exit(previousMapZone);
            entrance.setCenter(new Point(Game.DIM.width / 2, (3 * Game.DIM.height) / 4));
            entrance.setExit(false);
            mapPanelFloaters.add(entrance);
        }
    }

    private void createExitPanel() {
        Exit exit = new Exit(mMapZone);
        exit.setExit(true);
        mapPanelFloaters.add(exit);
    }

    // ITEM PANEL METHODS //
    private void createItemPanel() {
        switch (mMapZone) {
            case FOREST:
                createBomb();
                break;
            case CAVE:
                PowerUp powerUp = new PowerUp(mMapZone);
                powerUp.setCenter(Game.getCenterOfPanel());
                mapPanelFloaters.add(powerUp);
                break;
            case CASTLE:
                createShield();
                break;
        }
    }

    private void createBomb() {
        mapPanelFloaters.add(new Bomb());
    }

    private void createShield() {
        mapPanelFloaters.add(new Shield());
    }

    // BOSS PANEL METHODS //
    private void createBossPanel() {
        switch (mMapZone) {
            case FOREST:
                createRubbleBoss();
                break;
            case CAVE:
                createSpiderBoss();
                break;
            case CASTLE:
                createArrowBoss();
                break;
        }
    }

    private void createRubbleBoss() {
        int numRubble = 11;
        int x, y;
        int gridInterval;
        int dim = Game.DIM.width / (numRubble * 2);
        for (int i = 0; i < numRubble; i++) {
            int mod = Game.R.nextInt(2 * dim);
            if (entranceSide.equals("north")) {
                y = Game.DIM.height / 2 - dim + mod;
                gridInterval = Game.DIM.width / numRubble;
                x = gridInterval * i;
            } else {
                x = Game.DIM.width / 2  - dim + mod;
                gridInterval = Game.DIM.height / numRubble;
                y = gridInterval * i;
            }
            Rubble r = new Rubble();
            r.setCenter(new Point(x, y));
            mapPanelObstacles.add(r);
        }
    }

    private void createSpiderBoss() {
        int width = Game.DIM.width;
        int height = Game.DIM.height;

        SpiderBoss spiderBoss = new SpiderBoss();
        mapPanelFoes.add(new Web(spiderBoss, new Point(0, 0)));
        mapPanelFoes.add(new Web(spiderBoss, new Point(width, 0)));
        mapPanelFoes.add(new Web(spiderBoss, new Point(width, height)));
        mapPanelFoes.add(new Web(spiderBoss, new Point(0, height)));
        mapPanelFoes.add(new SpiderBoss());
        Cc.getInstance().setSpiderBoss(spiderBoss);
    }

    private void createArrowBoss() {

    }
}

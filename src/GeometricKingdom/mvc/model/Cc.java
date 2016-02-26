package GeometricKingdom.mvc.model;

//import GeometricKingdom.sounds.Sound;

import GeometricKingdom.mvc.controller.Game;
import GeometricKingdom.mvc.model.adventurer.*;
import GeometricKingdom.mvc.model.debris.RubbleDebris;
import GeometricKingdom.mvc.model.enemy.SpiderBoss;
import GeometricKingdom.mvc.view.*;
import GeometricKingdom.sounds.Sound;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Cc {

	public static final Dimension DIM = Game.DIM; //the dimension of the game
    public static final Color[] zoneColors = new Color[] {Color.BLACK, Color.BLACK, Color.DARK_GRAY, Color.LIGHT_GRAY};

	private MapPanel.MapZone mMapZone;
    private Zone mZone;
    private MapPanel mCurrentMapPanel = new MapPanel(MapPanel.MapZone.FOREST, MapPanel.PanelType.HOME);
    private int mCurrentMapPanelIndex;
	private Adventurer mAdventurer;
    private SpiderBoss mSpiderBoss;

	private boolean bPlaying = false;
	private boolean bPaused = false;
    private boolean bGameOver = false;
    private boolean bGameWon = false;
    private boolean bGodMode = false;
    private boolean bHasBomb = false;
    private boolean bHasShield = false;
    private boolean bLocked = false;
    private boolean bBossActive = false;
    private boolean bInArrowRoom = false;
    private boolean bDisplayPowerUpText = false;

    private String powerUpText = "";
    private Point powerUpTextCenter = new Point(DIM.width / 2, DIM.height / 2);
    private int powerUpTextTick = 0;

    private String sCurrentDisplayString = "";

    private int nTick = 0;
	
	// These ArrayLists with capacities set
	private List<GeometricKingdom.mvc.model.Movable> movDebris = new ArrayList<GeometricKingdom.mvc.model.Movable>(300);
	private List<GeometricKingdom.mvc.model.Movable> movFriends = new ArrayList<GeometricKingdom.mvc.model.Movable>(100);
	private List<GeometricKingdom.mvc.model.Movable> movFoes = new ArrayList<GeometricKingdom.mvc.model.Movable>(200);
	private List<GeometricKingdom.mvc.model.Movable> movFloaters = new ArrayList<GeometricKingdom.mvc.model.Movable>(50);
    private List<GeometricKingdom.mvc.model.Movable> movObstacles = new ArrayList<GeometricKingdom.mvc.model.Movable>(200);

	private GameOpsList opsList = new GameOpsList();

	//added by Dmitriy
	private static Cc instance = null;

    // Constructor made private - static Utility class only
	private Cc() {}

	public static Cc getInstance(){
		if (instance == null){
			instance = new Cc();
		}
		return instance;
	}

	public void newGame(){
        clearAll();
        bPlaying = false;
        bPaused = false;
        bGameOver = false;
        bGodMode = false;
	}

	// The parameter is true if this is for the beginning of the game, otherwise false
	// When you spawn a new falcon, you need to decrement its number
//	public  void spawnFalcon(boolean bFirst) {
//		if (getNumFalcons() != 0) {
//			falShip = new Falcon();
//			//movFriends.enqueue(falShip);
//			opsList.enqueue(falShip, CollisionOp.Operation.ADD);
//			if (!bFirst)
//			    setNumFalcons(getNumFalcons() - 1);
//		}
//
//		Sound.playSound("shipspawn.wav");
//
//	}

	public GameOpsList getOpsList() {
		return opsList;
	}

	public void setOpsList(GameOpsList opsList) {
		this.opsList = opsList;
	}

	public void clearAll(){
		movDebris.clear();
		movFriends.clear();
		movFoes.clear();
		movFloaters.clear();
        movObstacles.clear();
	}

    public MapPanel.MapZone getMapZone() {
        return mMapZone;
    }

    public Color getZoneColor() {
        return zoneColors[mMapZone.ordinal()];
    }

    public void setMapZone(MapPanel.MapZone mMapZone) {
        this.mMapZone = mMapZone;
    }

    public Adventurer getAdventurer() {
        return mAdventurer;
    }

    public void setAdventurer(Adventurer adventurer) {
        mAdventurer = adventurer;
    }

    public SpiderBoss getSpiderBoss() {
        return mSpiderBoss;
    }

    public void setSpiderBoss(SpiderBoss spiderBoss) {
        this.mSpiderBoss = spiderBoss;
    }

    public boolean isPlaying() {
		return bPlaying;
	}

	public void setPlaying(boolean bPlaying) {
		this.bPlaying = bPlaying;
	}

	public boolean isPaused() {
		return bPaused;
	}

	public void setPaused(boolean bPaused) {
		this.bPaused = bPaused;
	}

    public boolean isGameWon() {
        return bGameWon;
    }

    public void setGameWon(boolean bGameWon) {
        this.bGameWon = bGameWon;
    }

    public boolean getGodMode() {
        return bGodMode;
    }

    public void setGodMode(boolean bGodMode) {
        this.bGodMode = bGodMode;
    }

    public boolean hasBomb() {
        return bHasBomb;
    }

    public void setHasBomb(boolean bHasBomb) {
        this.bHasBomb = bHasBomb;
    }

    public boolean hasShield() {
        return bHasShield;
    }

    public void setHasShield(boolean bHasShield) {
        this.bHasShield = bHasShield;
    }

    public boolean getLocked() {
        return bLocked;
    }

    public void setLocked(boolean locked) {
        this.bLocked = locked;
    }

    public boolean getBossActive() {
        return bBossActive;
    }

    public void setBossActive(boolean bossActive) {
        this.bBossActive = bossActive;
    }

    public boolean getInArrowRoom() {
        return bInArrowRoom;
    }

    public void setInArrowRoom(boolean inArrowRoom) {
        this.bInArrowRoom = inArrowRoom;
    }

    public boolean getDisplayPowerUpText() {
        return bDisplayPowerUpText;
    }

    public void setDisplayPowerUpText(boolean displayPowerUpText) {
        this.bDisplayPowerUpText = displayPowerUpText;
    }

    public String getPowerUpText() {
        return powerUpText;
    }

    public void setPowerUpText(String powerUpText) {
        this.powerUpText = powerUpText;
    }

    public Point getPowerUpTextCenter() {
        return powerUpTextCenter;
    }

    public void setPowerUpTextCenter(Point powerUpTextCenter) {
        this.powerUpTextCenter = powerUpTextCenter;
    }

    public int getPowerUpTextTick() {
        return powerUpTextTick;
    }

    public void setPowerUpTextTick(int powerUpTextTick) {
        this.powerUpTextTick = powerUpTextTick;
    }

    public void updateLockStatus() {
        if (movFoes.isEmpty()) {
            getCurrentMapPanel().setLocked(false);
            bLocked = false;
        }
    }

    public boolean isGameOver() {
        return bGameOver;
    }

    public void resetBooleans() {
        bPaused = false;
        bGodMode = false;
        bHasBomb = false;
        bHasShield = false;
        bLocked = false;
        bBossActive = false;
        bInArrowRoom = false;
        bDisplayPowerUpText = false;
    }

    public void setGameOver(boolean bGameOver) {
        this.bGameOver = bGameOver;
    }

    public String getCurrentDisplayString() {
        return sCurrentDisplayString;
    }

    public void setCurrentDisplayString(String newDisplayString) {
        sCurrentDisplayString = newDisplayString;
    }

	public List<GeometricKingdom.mvc.model.Movable> getMovDebris() {
		return movDebris;
	}

	public List<GeometricKingdom.mvc.model.Movable> getMovFriends() {
		return movFriends;
	}

	public List<GeometricKingdom.mvc.model.Movable> getMovFoes() {
		return movFoes;
	}

    public List<GeometricKingdom.mvc.model.Movable> getMovObstacles() {
        return movObstacles;
    }

	public List<Movable> getMovFloaters() {
		return movFloaters;
	}

    public Zone getZone() {
        return mZone;
    }

    public MapPanel getCurrentMapPanel() {
        return mCurrentMapPanel;
    }

    public int getCurrentMapPanelIndex() {
        return mCurrentMapPanelIndex;
    }

    public void setZone(Zone mZone) {
        this.mZone = mZone;
    }

    public void setCurrentMapPanel(MapPanel currentMapPanel) {
        mCurrentMapPanel = currentMapPanel;
    }

    public void setCurrentMapPanelIndex(int mCurrentMapPanelIndex) {
        this.mCurrentMapPanelIndex = mCurrentMapPanelIndex;
    }

    // creates a grid of points to use for generating sprites at specific places on a grid
    public Point[][] generatePointGrid(int size) {
        Point[][] grid = new Point[size + 1][size + 1];

        int x = DIM.width / size;
        int y = DIM.height / size;

        for (int i = 0; i <= size; i++) {
            for (int j = 0; j <= size; j++) {
                grid[i][j] = new Point(i*x, j*y);
            }
        }

        return grid;
    }

    public void detonateRubble() {
        if (mMapZone == MapPanel.MapZone.FOREST && getCurrentMapPanel().getPanelType() == MapPanel.PanelType.BOSS) {
            Sound.playSound("rubble_bomb.wav");
            movObstacles.clear();
            for (int i = 0; i < 20; i++) {
                RubbleDebris rb = new RubbleDebris();
                rb.setRandomCenter();
                movDebris.add(rb);
            }
        }
    }

    public void initializeZone(MapPanel.MapZone mapZone) {
        mMapZone = mapZone;
        mZone = new Zone(mapZone);
        mCurrentMapPanelIndex = mZone.getPanelIndex(mZone.getHomePanel());
        mCurrentMapPanel = mZone.getHomePanel();
        sCurrentDisplayString = mCurrentMapPanel.getDisplayString();
        mCurrentMapPanel.setDiscovered(true);
        bLocked = false;
        if (mCurrentMapPanel.getMapPanelFoes() != null) {
            movFoes = mCurrentMapPanel.getMapPanelFoes();
        }
    }

    public void swapCurrentMapPanel(MapPanel mapPanel) {
        mZone.updateMapPanel(mCurrentMapPanelIndex, mCurrentMapPanel);
        clearAll();
        nTick = 0;
        mCurrentMapPanel = mapPanel;
        setCurrentDisplayString(mCurrentMapPanel.getDisplayString());
        if (mCurrentMapPanel.getMapPanelFoes().isEmpty()) {
            bLocked = false;
        } else {
            bLocked = true;
        }
        mCurrentMapPanelIndex = mZone.getPanelIndex(mapPanel);
        mCurrentMapPanel.setDiscovered(true);

        // if we're in the arrow room, begin the arrow storm
        if (Cc.getInstance().getCurrentMapPanel().getMapZone() == MapPanel.MapZone.CASTLE &&
                Cc.getInstance().getCurrentMapPanel().getPanelType() == MapPanel.PanelType.BOSS) {
            Cc.getInstance().setInArrowRoom(true);
        } else {
            Cc.getInstance().setInArrowRoom(false);
        }

        // get all Movables from the stored MapPanel onto the current GamePanel
        for (Movable mov : mCurrentMapPanel.getMapPanelFoes()) {
            Cc.getInstance().getOpsList().enqueue(mov, CollisionOp.Operation.ADD);
        }
        for (Movable mov : mCurrentMapPanel.getMapPanelDebris()) {
            Cc.getInstance().getOpsList().enqueue(mov, CollisionOp.Operation.ADD);
        }
        for (Movable mov : mCurrentMapPanel.getMapPanelFloaters()) {
            Cc.getInstance().getOpsList().enqueue(mov, CollisionOp.Operation.ADD);
        }
        for (Movable mov : mCurrentMapPanel.getMapPanelObstacles()) {
            Cc.getInstance().getOpsList().enqueue(mov, CollisionOp.Operation.ADD);
        }
    }

    public void setTick(int tick) {
        this.nTick = tick;
    }

    public int getTick() {
        return nTick;
    }

    public void exitZone(MapPanel.MapZone oldZoneType) {
        // advance to a new Zone
        if (oldZoneType != MapPanel.MapZone.CASTLE) {
            Sound.playSound("new_zone.wav");
            mMapZone = MapPanel.MapZone.values()[oldZoneType.ordinal() + 1];

            Zone newZone = new Zone(mMapZone);
            swapCurrentMapPanel(newZone.getHomePanel());
            mCurrentMapPanelIndex = 0;
            mAdventurer.setCenter(new Point(DIM.width / 2, (3 * DIM.height) / 4));

            mZone = newZone;

        // otherwise, the game is over
        } else {
            bGameWon = true;
        }
    }
}

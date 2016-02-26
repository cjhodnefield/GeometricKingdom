package GeometricKingdom.mvc.view;

import GeometricKingdom.mvc.controller.Game;
import GeometricKingdom.mvc.model.*;
import GeometricKingdom.mvc.model.adventurer.Adventurer;
import GeometricKingdom.mvc.model.adventurer.Sorcircler;
import GeometricKingdom.mvc.model.adventurer.Squarior;
import GeometricKingdom.mvc.model.adventurer.Trisassin;
import GeometricKingdom.mvc.model.enemy.SkullLord;
import GeometricKingdom.mvc.model.weapon.Arrow;
import GeometricKingdom.mvc.model.weapon.TriKnife;
import GeometricKingdom.sounds.Sound;

import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends Panel {
	
	// ==============================================================
	// FIELDS 
	// ============================================================== 
	 
	// The following "off" vars are used for the off-screen double-buffered image.
	private Dimension dimOff;
	private Image imgOff;
	private Graphics grpOff;

	private GameFrame gmf;
    private MapPanel mapPanel;
	private Font fnt = new Font("SansSerif", Font.BOLD, 12);
	private Font fntBig = new Font("SansSerif", Font.BOLD + Font.ITALIC, 36);
	private FontMetrics fmt;
	private int nFontWidth;
	private int nFontHeight;
	private String strDisplay = "";

	// ==============================================================
	// CONSTRUCTOR
	// ==============================================================

	public GamePanel(Dimension dim){
	    gmf = new GeometricKingdom.mvc.view.GameFrame();
		gmf.getContentPane().add(this);
		gmf.pack();
		initView();

		gmf.setSize(dim);
		gmf.setTitle("GeometricKingdom");
		gmf.setResizable(false);
		gmf.setVisible(true);
		this.setFocusable(true);
	}

	// ==============================================================
	// METHODS
	// ==============================================================

	@SuppressWarnings("unchecked")
	public void update(Graphics g) {
		if (grpOff == null || Game.DIM.width != dimOff.width
				|| Game.DIM.height != dimOff.height) {
			dimOff = Game.DIM;
			imgOff = createImage(Game.DIM.width, Game.DIM.height);
			grpOff = imgOff.getGraphics();
		}
		// Fill in background with black
        if (Cc.getInstance().isPlaying()) {
            grpOff.setColor(Cc.getInstance().getZoneColor());
        } else {
            grpOff.setColor(Color.BLACK);
        }
		grpOff.fillRect(0, 0, Game.DIM.width, Game.DIM.height);

        grpOff.setColor(Color.white);
        grpOff.setFont(fnt);

        // frame-by-frame updates for each Adventurer class
        if (Cc.getInstance().getAdventurer() instanceof Squarior) {
            // Squarior block

        } else if (Cc.getInstance().getAdventurer() instanceof Trisassin) {
            // Trisassin firing TriKnife block
            if (((Trisassin) Cc.getInstance().getAdventurer()).attacking()) {
                int attackRate = Cc.getInstance().getAdventurer().getAttRate();
                if (Cc.getInstance().getTick() % attackRate == 0 && Cc.getInstance().getAdventurer().getUniqueResource() > 0) {
                    Cc.getInstance().getOpsList().enqueue(new TriKnife(Cc.getInstance().getAdventurer()), CollisionOp.Operation.ADD);
                    Cc.getInstance().getOpsList().enqueue(new TriKnife(Cc.getInstance().getAdventurer(), 0), CollisionOp.Operation.ADD);
                    Cc.getInstance().getOpsList().enqueue(new TriKnife(Cc.getInstance().getAdventurer(), 1), CollisionOp.Operation.ADD);
                    Sound.playSound("laser.wav");
                    Cc.getInstance().getAdventurer().setUniqueResource(Cc.getInstance().getAdventurer().getUniqueResource() - 2);
                }
            }
        } else if (Cc.getInstance().getAdventurer() instanceof Sorcircler) {
            // Sorcircler block

        }

        // UPDATE BLOCK FOR THE CASTLE BOSS
        if (Cc.getInstance().getMapZone() == MapPanel.MapZone.CASTLE && Cc.getInstance().getCurrentMapPanel().getPanelType() == MapPanel.PanelType.BOSS) {

        }

        if (!Cc.getInstance().isPlaying() && !Cc.getInstance().isPaused() && !Cc.getInstance().isGameOver() && !Cc.getInstance().isGameWon()) {
            displayStartScreen();
        } else if (Cc.getInstance().isPaused()) {
            strDisplay = "Game Paused";
            grpOff.drawString(strDisplay, (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4);
        } else if (Cc.getInstance().isGameOver()) {
            displayGameOverScreen();
        } else if (Cc.getInstance().isGameWon()) {
            displayVictoryScreen();

        // otherwise, the game is playing
        } else {
            if (!Cc.getInstance().getCurrentMapPanel().getNewLevelText().isEmpty()) {
                displayNewLevelText(grpOff);
            }

            if (Cc.getInstance().getInArrowRoom() && Cc.getInstance().getTick() % 10 == 0) {
                Cc.getInstance().setLocked(false);
                int size = 20;
                Point[][] grid = Cc.getInstance().generatePointGrid(20);
                int dir = 0;
                Point p;
                int spd = 10;
                int speed = 10;

                for (int i = 4; i <= size - 4; i++) {
                    Arrow arrow = new Arrow();
                    if (!Cc.getInstance().getCurrentMapPanel().getBossDir().equals("north")) {
                        if ((i % 2) == 0) {
                            p = grid[i][1];
                            dir = 180;
                            speed = spd;
                        } else {
                            p = grid[i][size - 1];
                            dir = 0;
                            speed = -spd;
                        }
                        arrow.setDeltaY(speed);
                    } else {
                        if ((i % 2) == 0) {
                            p = grid[1][i];
                            dir = -90;
                            speed = spd;
                        } else {
                            p = grid[size - 1][i];
                            dir = 90;
                            speed = -spd;
                        }
                        arrow.setDeltaX(speed);
                    }
                    arrow.setOrientation(dir);
                    arrow.setCenter(p);
                    Cc.getInstance().getOpsList().enqueue(arrow, CollisionOp.Operation.ADD);
                    if (Cc.getInstance().getTick() % 40 == 0) {
                        Sound.playSound("arrow_fired.wav");
                    }
                }
            }

            for (Movable movFoe : Cc.getInstance().getMovFoes()) {
                // call shootAdventurer method for every HPEntity foe
                if (movFoe instanceof HPEntity) {
                    HPEntity foe = ((HPEntity) movFoe);
                    int attackRate = foe.getAttRate();
                    int tempTick = Cc.getInstance().getTick();
                    if ((tempTick != 0) && (tempTick % attackRate == 0)) {
                        foe.shootAdventurer();
                    }
                }
            }

            iterateMovables(grpOff, Cc.getInstance().getAdventurer(),
                    (ArrayList<Movable>)  Cc.getInstance().getMovFriends(),
                    (ArrayList<Movable>)  Cc.getInstance().getMovDebris(),
                    (ArrayList<Movable>)  Cc.getInstance().getMovFloaters(),
					(ArrayList<Movable>)  Cc.getInstance().getMovFoes(),
                    (ArrayList<Movable>)  Cc.getInstance().getMovObstacles());

            displayCurrentDisplayString(grpOff);
            if (Cc.getInstance().getDisplayPowerUpText()) {
                displayPowerUpText(grpOff);
            }

            drawBorders(grpOff);
            displayStats(grpOff);
        }

        //draw the double-Buffered Image to the graphics context of the panel
        g.drawImage(imgOff, 0, 0, this);
	}

	//for each movable array, process it.
	private void iterateMovables(Graphics g, Movable adventurer, ArrayList<Movable>...movMovz){
		// DRAW EVERY MOVABLE IN EVERY LIST OF MOVABLES
        for (ArrayList<Movable> movMovs : movMovz) {
			for (Movable mov : movMovs) {
                mov.move();
				mov.draw(g);
			}
		}

        // DRAW THE ADVENTURER!
        adventurer.move();
        adventurer.draw(g);
	}

	private void initView() {
		Graphics g = getGraphics();			// get the graphics context for the panel
		g.setFont(fnt);	    				// take care of some simple font stuff
		fmt = g.getFontMetrics();
		nFontWidth = fmt.getMaxAdvance();
		nFontHeight = fmt.getHeight();
		g.setFont(fntBig);
	}

    private void displayStartScreen() {
        grpOff.setColor(Color.white);
        grpOff.setFont(fnt);
        fmt = grpOff.getFontMetrics();

        strDisplay = "CHOOSE AN ADVENTURER AND GO SAVE YOUR GEOMETRIC KINGDOM!";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4);

        strDisplay = "1 - SQUARIOR";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 40);

        strDisplay = "This rectangular warrior's strategy isn't very complicated, but it is very confident:";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 60);
        strDisplay = "Hit enemies by moving into them with WASD until they stop hitting back!";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 80);

        strDisplay = "2 - TRISASSIN";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 120);

        strDisplay = "A master of all things three-sided and pointy, it's a wonder that this triangular thief is helping the kingdom at all:";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 140);

        strDisplay = "Use the arrow keys to shoot three-sided pointy things at your enemies!";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 160);

        strDisplay = "3 - SORCIRCLER";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 200);

        strDisplay = "Years of research have allowed this adventurer to transcend the need for impurities such as 'sides' or 'corners':";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 220);

        strDisplay = "Point and click with the mouse to destroy lesser beings with circular sorcery!";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 240);
    }

    private void displayGameOverScreen() {
        grpOff.setColor(Color.white);
        grpOff.setFont(fntBig);
        fmt = grpOff.getFontMetrics();

        strDisplay = "GAME OVER";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4);

        strDisplay = "THE GEOMETRIC KINGDOM IS SURELY DOOMED";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 40);

        strDisplay = "PLAY AGAIN?";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 2
                        + nFontHeight + 40);

        strDisplay = "Y / N";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 2
                        + nFontHeight + 80);
    }

    private void displayVictoryScreen() {
        grpOff.setColor(Color.white);
        grpOff.setFont(fnt);
        fmt = grpOff.getFontMetrics();

        strDisplay = "YOUR MIGHTIEST OPPONENT YET AWAITS YOU AT THE TOP OF THE STAIRS...";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 8);

        grpOff.setColor(Color.RED);
        grpOff.fillRect(Game.DIM.width / 2 - 70, Game.DIM.height / 2 - 210, Game.DIM.width / 8, Game.DIM.height / 16);
        SkullLord skully = new SkullLord();
        skully.draw(grpOff);

        grpOff.setColor(Color.white);
        strDisplay = "...BUT THAT IS A STORY FOR ANOTHER DAY (BECAUSE I DIDN'T FINISH THE BOSS)";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, 7 * Game.DIM.height / 8);

        strDisplay = "THANK YOU FOR PLAYING GEOMETRIC KINGDOM!";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, 7 * Game.DIM.height / 8 + 40);
    }

    private void displayStats(Graphics g) {
        g.setColor(Color.white);
        g.setFont(fnt);
        fmt = g.getFontMetrics();

        if (Cc.getInstance().getAdventurer() != null) {
            displayPanelName(g);

            //HP STAT DISPLAY
            int x = 5 + Game.WALL_THICKNESS;
            int y = 5;
            int width = (int) (Game.DIM.width * 0.35);
            int height = (int) (Game.DIM.height * 0.01);
            int border = 4;

            Adventurer adv = Cc.getInstance().getAdventurer();
            int maxHP = adv.getMaxHP();
            int HP = adv.getHP();
            double percentHP = ((double) HP) / maxHP;

            g.setColor(Color.GRAY);
            g.fillRect(x, y, width + border, height + border);
            g.setColor(Color.BLACK);
            g.fillRect(x + (border/2), y + (border / 2), width, height);
            g.setColor(Color.RED);
            g.fillRect(x + (border/2), y + (border / 2), (int) (percentHP * width), height);

            g.setColor(Color.white);
            strDisplay = "HP";
            grpOff.drawString(strDisplay, x + width + border * 2, fmt.getHeight());

            //RESOURCE STAT DISPLAY
            x = Game.DIM.width - width - 5 - Game.WALL_THICKNESS;

            int resourceMax = adv.getUniqueResourceMax();
            int resource = adv.getUniqueResource();
            double percentResource = Math.max((((double) resource) / resourceMax), 0);

            g.setColor(Color.GRAY);
            g.fillRect(x, y, width + border, height + border);
            g.setColor(Color.BLACK);
            g.fillRect(x + (border/2), y + (border / 2), width, height);
            g.setColor(Color.CYAN);
            g.fillRect(x + (border/2), y + (border / 2), (int) (percentResource * width), height);

            g.setColor(Color.white);
            strDisplay = Cc.getInstance().getAdventurer().getUniqueResourceName();
            grpOff.drawString(strDisplay, x - fmt.stringWidth(strDisplay) - border, fmt.getHeight());
        }
    }

    private void displayPanelName(Graphics g) {
        g.setColor(Color.white);
        g.setFont(fnt);
        fmt = g.getFontMetrics();
        strDisplay = Cc.getInstance().getCurrentMapPanel().getPanelName();
        int width = Game.DIM.width;
        int height = (int) (Game.DIM.height * 0.001);
        int strWidth = fmt.stringWidth(strDisplay);
        g.drawString(strDisplay, (width - strWidth) / 2, nFontHeight - height);
    }

    private void displayCurrentDisplayString(Graphics g) {
        g.setColor(Color.white);
        g.setFont(fnt);
        fmt = g.getFontMetrics();
        strDisplay = Cc.getInstance().getCurrentDisplayString();
        g.drawString(strDisplay, (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, (int) (nFontHeight + Game.WALL_THICKNESS * 1.1));
    }

    private void displayPowerUpText(Graphics g) {
        g.setColor(Color.white);
        g.setFont(fnt);
        fmt = g.getFontMetrics();
        strDisplay = Cc.getInstance().getPowerUpText();
        Point p = Cc.getInstance().getPowerUpTextCenter();
        g.drawString(strDisplay, (p.x - (fmt.stringWidth(strDisplay) / 2)), p.y);
    }

    private void displayNewLevelText(Graphics g) {
        ArrayList<String> text = Cc.getInstance().getCurrentMapPanel().getNewLevelText();
        g.setColor(Color.white);
        g.setFont(fntBig);
        fmt = g.getFontMetrics();
        strDisplay = text.get(0);
        g.drawString(strDisplay, (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, (Game.DIM.height / 2) - 200);
        strDisplay = text.get(1);
        g.drawString(strDisplay, (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, (Game.DIM.height / 2) - 160);
    }

    private void drawBorders(Graphics g) {
        //EAST
        if (Cc.getInstance().getLocked() || Cc.getInstance().getCurrentMapPanel().getEast() == null) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(Game.DIM.width - Game.WALL_THICKNESS, 0, Game.WALL_THICKNESS, Game.DIM.height);
        }
        //WEST
        if (Cc.getInstance().getLocked() || Cc.getInstance().getCurrentMapPanel().getWest() == null) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, Game.WALL_THICKNESS, Game.DIM.height);
        }
        //NORTH
        if (Cc.getInstance().getLocked() || Cc.getInstance().getCurrentMapPanel().getNorth() == null) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, Game.DIM.width, Game.WALL_THICKNESS);
        }
        //SOUTH
        if (Cc.getInstance().getLocked() || Cc.getInstance().getCurrentMapPanel().getSouth() == null) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, Game.DIM.height - Game.WALL_THICKNESS - Game.southBump, Game.DIM.width, Game.WALL_THICKNESS);
        }
    }

    // This method draws some text to the middle of the screen before/after a game
    private void displayTextOnScreen() {

        strDisplay = "GAME OVER";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4);

        strDisplay = "use the arrow keys to turn and thrust";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 40);

        strDisplay = "use the space bar to fire";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 80);

        strDisplay = "'S' to Start";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 120);

        strDisplay = "'P' to Pause";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 160);

        strDisplay = "'Q' to Quit";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 200);
        strDisplay = "left pinkie on 'A' for Shield";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 240);

        strDisplay = "left index finger on 'F' for Guided Missile";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 280);

        strDisplay = "'Numeric-Enter' for Hyperspace";
        grpOff.drawString(strDisplay,
                (Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
                        + nFontHeight + 320);
    }

	public GameFrame getFrm() {return this.gmf;}

	public void setFrm(GameFrame frm) {this.gmf = frm;}

    public MapPanel getMapPanel() {
        return mapPanel;
    }

    public void setMapPanel(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
    }
}
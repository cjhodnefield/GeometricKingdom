// THERE IS A readme.txt FILE IN MY GeometricKingdom FOLDER WITH INSTRUCTIONS AND NOTES ABOUT MY GAME

package GeometricKingdom.mvc.controller;

import GeometricKingdom.mvc.model.*;
import GeometricKingdom.mvc.model.adventurer.*;
import GeometricKingdom.mvc.model.debris.*;
import GeometricKingdom.mvc.model.enemy.*;
import GeometricKingdom.mvc.model.floater.*;
import GeometricKingdom.mvc.model.weapon.*;
import GeometricKingdom.mvc.view.*;
import GeometricKingdom.sounds.Sound;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Game implements Runnable, KeyListener, MouseMotionListener, MouseListener {
    private static final int WIDTH = 1100;
    public static final Dimension DIM = new Dimension(WIDTH, (int) (.85 * WIDTH)); // the dimension of the game
    public static final int WALL_THICKNESS = (int) (DIM.width * 0.02);
    public static final int southBump = 20;
    public static Random R = new Random();
    public final static int ANI_DELAY = 30; // milliseconds between screen
    // updates (animation)

    private Thread thrAnim;
    private int nTick = 0;

    private GamePanel gmpPanel;

    //http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
    private final int
            PAUSE = 80,         // p key
            QUIT = 27,          // esc key
            ATTACK_UP = 38,     // attack up; up arrow
            ATTACK_LEFT = 37,   // attack left; left arrow
            ATTACK_RIGHT = 39,  // attack right; right arrow
            ATTACK_DOWN = 40,   // attack down; down arrow
            BOMB = 32,          // space key
            MUTE = 77,          // m key
            MOVE_UP = 87,       // move up; w key
            MOVE_LEFT = 65,     // move left; a key
            MOVE_RIGHT = 68,    // move right; s key
            MOVE_DOWN = 83,     // move down; d key
            LOCK = 76,		    // l key
            SELECT1 = 49,
            SELECT2 = 50,
            SELECT3 = 51,
            GODMODE = 71,       // enter key; toggles invulnerability
            YES = 89,           // y key; yes
            NO = 78;            // n key; no


    public Game() {
        gmpPanel = new GamePanel(DIM);
        gmpPanel.addKeyListener(this);
        gmpPanel.addMouseListener(this);
        gmpPanel.addMouseMotionListener(this);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() { // uses the Event dispatch thread from Java 5 (refactored)
            public void run() {
                try {
                    Game game = new Game(); // construct itself
                    game.fireUpAnimThread();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fireUpAnimThread() { // called initially
        if (thrAnim == null) {
            thrAnim = new Thread(this); // pass the thread a runnable object (this)
            thrAnim.start();
        }
    }

    @Override
    public void run() {
        // lower this thread's priority; let the "main" aka 'Event Dispatch'
        // thread do what it needs to do first
        thrAnim.setPriority(Thread.MIN_PRIORITY);

        // and get the current time
        long lStartTime = System.currentTimeMillis();

        while (Thread.currentThread() == thrAnim) {
            tick();
            gmpPanel.update(gmpPanel.getGraphics()); // update takes the graphics context we must
            // surround the sleep() in a try/catch block
            // this simply controls delay time between
            // the frames of the animation

            //this might be a good place to check for collisions
            checkCollisions();
            checkFoeDeaths();
            checkPowerUpSpawn();
            //this might be a good place to check if the level is clear (no more foes)
            //if the level is clear then spawn some big asteroids -- the number of asteroids
            //should increase with the level.
            //checkNewLevel();

            try {
                // The total amount of time is guaranteed to be at least ANI_DELAY long.  If processing (update)
                // between frames takes longer than ANI_DELAY, then the difference between lStartTime -
                // System.currentTimeMillis() will be negative, then zero will be the sleep time
                lStartTime += ANI_DELAY;
                Thread.sleep(Math.max(0,
                        lStartTime - System.currentTimeMillis()));
            } catch (InterruptedException e) {
                // just skip this frame -- no big deal
                continue;
            }
        }
    }

    //some methods for timing events in the game,
    //such as the appearance of UFOs, floaters (power-ups), etc.
    public void tick() {
        if (nTick == Integer.MAX_VALUE)
            nTick = 0;
        else
            nTick++;
        Cc.getInstance().setTick(nTick);
    }

    public int getTick() {
        return nTick;
    }

    public void gameStart() {
        Cc.getInstance().setPlaying(true);
        Cc.getInstance().initializeZone(MapPanel.MapZone.FOREST);
    }

    private void checkCollisions() {
        if (Cc.getInstance().isPlaying()) {
            Point pntFriendCenter, pntFoeCenter, pntFloaterCenter, pntObstCenter;
            int nFriendRadius, nFoeRadius, nFloaterRadius, nObstRadius;

            if (Cc.getInstance().getAdventurer() != null) {
                Adventurer adv = Cc.getInstance().getAdventurer();
                if (adv.getProtected()) {
                    if (adv.getProtectedTick() + 20 <= getTick()) {
                        Cc.getInstance().getAdventurer().setProtected(false);
                        Cc.getInstance().getAdventurer().setProtectedTick(-21);
                    }
                }

                if (Cc.getInstance().getPowerUpTextTick() + 50 < Cc.getInstance().getTick() || Cc.getInstance().getTick() < Cc.getInstance().getPowerUpTextTick()) {
                    Cc.getInstance().setDisplayPowerUpText(false);
                }

                for (Movable movFloater : Cc.getInstance().getMovFloaters()) {
                    pntFloaterCenter = movFloater.getCenter();
                    nFloaterRadius = movFloater.getRadius();

                    if (movFloater instanceof Exit) {
                        if ((adv.getCenter().distance(pntFloaterCenter) < ((adv.getRadius() + nFloaterRadius) / 2)) && ((Exit) movFloater).isExit()) {
                            Cc.getInstance().exitZone(((Exit) movFloater).getMapZone());
                            Cc.getInstance().getOpsList().enqueue(movFloater, CollisionOp.Operation.REMOVE);
                            return;
                        }
                    }

                    if (adv.getCenter().distance(pntFloaterCenter) < (adv.getRadius() + nFloaterRadius) && !(movFloater instanceof Exit)) {
                        if (movFloater instanceof PowerUp) {
                            adv.powerup((PowerUp) movFloater);
                        }
                        if (movFloater instanceof Bomb) {
                            Sound.playSound("item_acquired.wav");
                            adv.bombAcquired();
                        }
                        if (movFloater instanceof Shield) {
                            Sound.playSound("item_acquired.wav");
                            adv.shieldAcquired();
                        }
                        Cc.getInstance().getOpsList().enqueue(movFloater, CollisionOp.Operation.REMOVE);
                    }
                }

                for (Movable movObst : Cc.getInstance().getMovObstacles()) {
                    pntObstCenter = movObst.getCenter();
                    nObstRadius = movObst.getRadius();
                    if (adv.getCenter().distance(pntObstCenter) < (adv.getRadius() + nObstRadius)) {
                        int proximity = adv.proximity(movObst);
                        adv.bounce((Sprite) movObst, proximity);

                        if (!Cc.getInstance().getAdventurer().getProtected()) {
                            Cc.getInstance().getAdventurer().updateHP(-1 * ((HPEntity) movObst).getDmg());
                            Cc.getInstance().getAdventurer().setProtected(true);
                            Cc.getInstance().getAdventurer().setProtectedTick(getTick());
                        }
                    }
                }

                for (Movable movFoe : Cc.getInstance().getMovFoes()) {
                    pntFoeCenter = movFoe.getCenter();
                    nFoeRadius = movFoe.getRadius();

                    if (movFoe instanceof Boss) {
                        if (movFoe instanceof SpiderBoss) {
                            Cc.getInstance().setSpiderBoss((SpiderBoss) movFoe);
                        }
                    }

                    if (movFoe instanceof Web && ((Web) movFoe).distanceTo(adv) < adv.getRadius()) {
                        if (!Cc.getInstance().getAdventurer().getProtected()) {
                            int dmg = Cc.getInstance().getSpiderBoss().getDmg();
                            Cc.getInstance().getAdventurer().updateHP(-dmg);
                            Cc.getInstance().getAdventurer().setProtected(true);
                            Cc.getInstance().getAdventurer().setProtectedTick(getTick());
                        }
                    }

                    if (adv.getCenter().distance(pntFoeCenter) < (adv.getRadius() + nFoeRadius)) {
                        int proximity = adv.proximity(movFoe);

                        // if the movFoe is an enemy
                        if (movFoe instanceof HPEntity) {

                            if (((HPEntity) movFoe).getBounceable()) {
                                Cc.getInstance().getAdventurer().bounce((Sprite) movFoe, proximity);
                            }

                            // damage adventurer if not protected and the enemy is not a Boss
                            if (!Cc.getInstance().getAdventurer().getProtected() && !(movFoe instanceof Boss)) {
                                Cc.getInstance().getAdventurer().updateHP(-1 * ((HPEntity) movFoe).getDmg());
                                Cc.getInstance().getAdventurer().setProtected(true);
                                Cc.getInstance().getAdventurer().setProtectedTick(getTick());

                                // if the adv is the Squarior, damage the enemy too
                                if (adv instanceof Squarior) {
                                    ((Squarior) Cc.getInstance().getAdventurer()).initializeHitTick();
                                    ((HPEntity) movFoe).updateHP(-1 * adv.getDmg());
                                    Sound.playSound("squarior_attack.wav");
                                }
                            }

                            if (adv instanceof Squarior && movFoe instanceof SpiderBoss && (adv.getCenter().distance(pntFoeCenter) < ((adv.getRadius() + nFoeRadius) / 2))) {
                                ((Squarior) Cc.getInstance().getAdventurer()).initializeHitTick();
                                ((HPEntity) movFoe).updateHP(-1 * adv.getDmg());
                                Sound.playSound("squarior_attack.wav");
                            }

                            //Cc.getInstance().getOpsList().enqueue(movFoe, CollisionOp.Operation.REMOVE);
                            //Sound.playSound("kapow.wav");
                        }

                        // if the movFoe is an enemy projectile
                        if (movFoe instanceof FoeProjectile) {
                            if (!Cc.getInstance().getAdventurer().getProtected() && !(movFoe instanceof Arrow && Cc.getInstance().hasShield())) {
                                Cc.getInstance().getAdventurer().updateHP(-1 * ((Weapon) movFoe).getDmg());
                                Cc.getInstance().getAdventurer().setProtected(true);
                                Cc.getInstance().getAdventurer().setProtectedTick(getTick());
                                Cc.getInstance().getOpsList().enqueue(movFoe, CollisionOp.Operation.REMOVE);
                            }
                        }
                    }
                }
            }

            for (Movable movFriend : Cc.getInstance().getMovFriends()) {
                pntFriendCenter = movFriend.getCenter();
                nFriendRadius = movFriend.getRadius();

                for (Movable movFoe : Cc.getInstance().getMovFoes()) {
                    pntFoeCenter = movFoe.getCenter();
                    nFoeRadius = movFoe.getRadius();

                    if (pntFriendCenter.distance(pntFoeCenter) < (nFriendRadius + nFoeRadius + 5)) {

                        if (movFriend instanceof TriKnife && movFoe instanceof HPEntity) {
                            Cc.getInstance().getOpsList().enqueue(movFriend, CollisionOp.Operation.REMOVE);
                            ((HPEntity) movFoe).updateHP(-1 * ((TriKnife) movFriend).getDmg());
                            //kill the foe and if asteroid, then spawn new asteroids
                            //killFoe(movFoe);
                            //Sound.playSound("kapow.wav");
                        }

                        if (movFriend instanceof CircleBomb && movFoe instanceof HPEntity) {
                            if (((CircleBomb) movFriend).isBombActive()) {
                                ((HPEntity) movFoe).updateHP(-1 * ((CircleBomb) movFriend).getDmg());
                            }
                        }
                    }
                }
            }

            //we are dequeuing the opsList and performing operations in serial to avoid mutating the movable ArrayLists while iterating them above
            while (!Cc.getInstance().getOpsList().isEmpty()) {
                CollisionOp cop = Cc.getInstance().getOpsList().dequeue();
                Movable mov = cop.getMovable();
                CollisionOp.Operation operation = cop.getOperation();

                switch (mov.getTeam()) {
                    case FOE:
                        if (operation == CollisionOp.Operation.ADD) {
                            Cc.getInstance().getMovFoes().add(mov);
                        } else {
                            Cc.getInstance().getMovFoes().remove(mov);
                        }
                        break;

                    case FRIEND:
                        if (operation == CollisionOp.Operation.ADD) {
                            Cc.getInstance().getMovFriends().add(mov);
                        } else {
                            Cc.getInstance().getMovFriends().remove(mov);
                        }
                        break;

                    case FLOATER:
                        if (operation == CollisionOp.Operation.ADD) {
                            Cc.getInstance().getMovFloaters().add(mov);
                        } else {
                            Cc.getInstance().getMovFloaters().remove(mov);
                        }
                        break;

                    case DEBRIS:
                        if (operation == CollisionOp.Operation.ADD) {
                            Cc.getInstance().getMovDebris().add(mov);
                        } else {
                            Cc.getInstance().getMovDebris().remove(mov);
                        }
                        break;

                    case OBSTACLE:
                        if (operation == CollisionOp.Operation.ADD) {
                            Cc.getInstance().getMovObstacles().add(mov);
                        } else {
                            Cc.getInstance().getMovObstacles().remove(mov);
                        }
                        break;
                }
            }
            Cc.getInstance().updateLockStatus();

            //a request to the JVM is made every frame to garbage collect, however, the JVM will choose when and how to do this
            System.gc();
        }
    }

    public static Point getCenterOfPanel() {
        return new Point(DIM.width / 2, DIM.height / 2);
    }

    private void checkFoeDeaths() {
        if (Cc.getInstance().isPlaying()) {
            for (Movable movFoe : Cc.getInstance().getMovFoes()) {
                if (movFoe instanceof HPEntity) {
                    if (((HPEntity) movFoe).getHP() <= 0) {
                        Cc.getInstance().getOpsList().enqueue(movFoe, CollisionOp.Operation.REMOVE);
                        Sound.playSound("kapow.wav");
                        Cc.getInstance().getCurrentMapPanel().getMapPanelFoes().remove(movFoe);
                        Debris newCorpse = new SkullCorpse(movFoe);
                        Cc.getInstance().getOpsList().enqueue(newCorpse, CollisionOp.Operation.ADD);
                        Cc.getInstance().getCurrentMapPanel().getMapPanelDebris().add(newCorpse);

                        // if the player killed the SpiderBoss
                        if (movFoe instanceof SpiderBoss) {
                            Sound.playSound("spiderboss_death.wav");
                            // spawn a power for sure
                            PowerUp powerUp = new PowerUp(Cc.getInstance().getMapZone());
                            powerUp.setCenter(new Point(DIM.width / 2, DIM.height / 4));
                            Cc.getInstance().getOpsList().enqueue(powerUp, CollisionOp.Operation.ADD);

                            // and remove all lingering webs
                            for (Movable movOtherFoe : Cc.getInstance().getMovFoes()) {
                                Cc.getInstance().getOpsList().enqueue(movOtherFoe, CollisionOp.Operation.REMOVE);
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    public void checkPowerUpSpawn() {
        if (Cc.getInstance().isPlaying()) {
            if (!Cc.getInstance().getMovFoes().isEmpty()) {
                Cc.getInstance().getCurrentMapPanel().setPowerUpDispensed(false);
            }
            if (Cc.getInstance().getMovFoes().isEmpty() && !Cc.getInstance().getCurrentMapPanel().getPowerUpDispensed()) {
                // chance that a power up appears once a map is cleared
                int powerUpChance = 33 * (Cc.getInstance().getMapZone().ordinal());

                int rand = R.nextInt(100);
                if (rand >= powerUpChance) {
                    Cc.getInstance().getOpsList().enqueue(new PowerUp(Cc.getInstance().getMapZone()), CollisionOp.Operation.ADD);
                }
                Cc.getInstance().getCurrentMapPanel().setPowerUpDispensed(true);
            }
        }
    }

    // ===============================================
    // KEYLISTENER METHODS
    // ===============================================

    @Override
    public void keyPressed(KeyEvent e) {
        Adventurer adv = Cc.getInstance().getAdventurer();
        int nKey = e.getKeyCode();

        switch (nKey) {
            case MOVE_UP:
                if (Cc.getInstance().isPlaying()) {
                    Cc.getInstance().getAdventurer().moveUp(true);
                }
                break;
            case MOVE_DOWN:
                if (Cc.getInstance().isPlaying()) {
                    Cc.getInstance().getAdventurer().moveDown(true);
                }
                break;
            case MOVE_LEFT:
                if (Cc.getInstance().isPlaying()) {
                    Cc.getInstance().getAdventurer().moveLeft(true);
                }
                break;
            case MOVE_RIGHT:
                if (Cc.getInstance().isPlaying()) {
                    Cc.getInstance().getAdventurer().moveRight(true);
                }
                break;
            case ATTACK_UP:
                if (Cc.getInstance().isPlaying()) {
                    Cc.getInstance().getAdventurer().attackUp(true);
                    Cc.getInstance().getAdventurer().attack();
                }
                break;
            case ATTACK_DOWN:
                if (Cc.getInstance().isPlaying()) {
                    Cc.getInstance().getAdventurer().attackDown(true);
                    Cc.getInstance().getAdventurer().attack();
                }
                break;
            case ATTACK_LEFT:
                if (Cc.getInstance().isPlaying()) {
                    Cc.getInstance().getAdventurer().attackLeft(true);
                    Cc.getInstance().getAdventurer().attack();
                }
                break;
            case ATTACK_RIGHT:
                if (Cc.getInstance().isPlaying()) {
                    Cc.getInstance().getAdventurer().attackRight(true);
                    Cc.getInstance().getAdventurer().attack();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Adventurer adv = Cc.getInstance().getAdventurer();
        int nKey = e.getKeyCode();

        switch (nKey) {
            case MOVE_UP:
                if (Cc.getInstance().isPlaying()) {
                    Cc.getInstance().getAdventurer().moveUp(false);
                }
                break;
            case MOVE_DOWN:
                if (Cc.getInstance().isPlaying()) {
                    Cc.getInstance().getAdventurer().moveDown(false);
                }
                break;
            case MOVE_LEFT:
                if (Cc.getInstance().isPlaying()) {
                    Cc.getInstance().getAdventurer().moveLeft(false);
                }
                break;
            case MOVE_RIGHT:
                if (Cc.getInstance().isPlaying()) {
                    Cc.getInstance().getAdventurer().moveRight(false);
                }
                break;
            case BOMB:
                if (Cc.getInstance().isPlaying()) {
                    if (Cc.getInstance().getCurrentMapPanel().getMapZone() == MapPanel.MapZone.FOREST &&
                            Cc.getInstance().getCurrentMapPanel().getPanelType() == MapPanel.PanelType.BOSS) {
                        //if player has obtained the bomb
                        //DETONATE BOMB ITEM
                        Cc.getInstance().detonateRubble();
                    }
                }
                break;
            case ATTACK_UP:
                if (Cc.getInstance().isPlaying()) {
                    Cc.getInstance().getAdventurer().attackUp(false);
                }
                break;
            case ATTACK_DOWN:
                if (Cc.getInstance().isPlaying()) {
                    Cc.getInstance().getAdventurer().attackDown(false);
                }
                break;
            case ATTACK_LEFT:
                if (Cc.getInstance().isPlaying()) {
                    Cc.getInstance().getAdventurer().attackLeft(false);
                }
                break;
            case ATTACK_RIGHT:
                if (Cc.getInstance().isPlaying()) {
                    Cc.getInstance().getAdventurer().attackRight(false);
                }
                break;
            case SELECT1:
                if (!Cc.getInstance().isPlaying() && !Cc.getInstance().isGameOver()) {
                    Cc.getInstance().setAdventurer(new Squarior());
                    gameStart();
                }
                break;
            case SELECT2:
                if (!Cc.getInstance().isPlaying() && !Cc.getInstance().isGameOver()) {
                    Cc.getInstance().setAdventurer(new Trisassin());
                    gameStart();
                }
                break;
            case SELECT3:
                if (!Cc.getInstance().isPlaying() && !Cc.getInstance().isGameOver()) {
                    Cc.getInstance().setAdventurer(new Sorcircler());
                    gameStart();
                }
                break;
            case PAUSE:
                if (Cc.getInstance().isPlaying() || Cc.getInstance().isPaused()) {
                    Cc.getInstance().setPlaying(!Cc.getInstance().isPlaying());
                    Cc.getInstance().setPaused(!Cc.getInstance().isPaused());
                }
                break;
            case GODMODE:
                if (Cc.getInstance().isPlaying()) {
                    Cc.getInstance().setGodMode(!Cc.getInstance().getGodMode());
                }
                break;
            case LOCK:
                if (Cc.getInstance().getLocked()) {
                    Cc.getInstance().setLocked(false);
                } else {
                    Cc.getInstance().setLocked(true);
                }

                break;
            case YES:
                if (Cc.getInstance().isGameOver()) {
                    Cc.getInstance().newGame();
                }
                break;
            case NO:
                if (Cc.getInstance().isGameOver()) {
                    System.exit(0);
                }
                break;
            case QUIT:
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    // ===============================================
    // MOUSELISTENER METHODS
    // ===============================================

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (Cc.getInstance().isPlaying()) {
            Adventurer adv = Cc.getInstance().getAdventurer();
            if (adv instanceof Sorcircler) {
                Sorcircler sorcircler = (Sorcircler) adv;
                if (sorcircler.isBombReady()) {
                    Cc.getInstance().getOpsList().enqueue(new CircleBomb(e, sorcircler), CollisionOp.Operation.ADD);
                    ((Sorcircler) Cc.getInstance().getAdventurer()).setBombReady(false);
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}

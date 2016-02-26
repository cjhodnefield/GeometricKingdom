package GeometricKingdom.mvc.model.adventurer;

import GeometricKingdom.mvc.controller.Game;
import GeometricKingdom.mvc.model.Cc;
import GeometricKingdom.mvc.model.HPEntity;
import GeometricKingdom.mvc.model.floater.PowerUp;
import GeometricKingdom.sounds.Sound;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jonathan on 11/19/2015.
 */
public class Adventurer extends HPEntity {
    protected int dmg;                  // attack damage
    protected int attRate;              // attack rate
    protected int prjSpd;               // projectile speed
    protected int uniqueResourceMax;    // unique resource for each character
    protected int uniqueResource;
    protected int resourceRefillRate;

    protected String uniqueResourceName;

    private int protectedTick;

    private boolean bProtected = false;

    protected Color trueColor;
    protected ArrayList<Point> pntCs;
    private Color flickerColor = Color.BLACK;

    protected boolean bMovingLeft = false;
    protected boolean bMovingRight = false;
    protected boolean bMovingUp = false;
    protected boolean bMovingDown = false;

    protected boolean bAttackingLeft = false;
    protected boolean bAttackingRight = false;
    protected boolean bAttackingUp = false;
    protected boolean bAttackingDown = false;

    private ArrayList<String> items = new ArrayList<String>();

    public Adventurer() {
        super();
        setRadProportion(1/40.0);
        setRadius((int) (Game.DIM.width * getRadProportion()));
        setTeam(Team.FRIEND);

        maxHP = 50;
        HP = maxHP;
        dmg = 5;
        spd = 12;
        attRate = 8;
        prjSpd = 20;

        uniqueResourceMax = 20;
        uniqueResource = uniqueResourceMax;
        resourceRefillRate = 2;

        setBounceable(true);
    }

    public int getDmg() {
        return dmg;
    }

    public int getSpd() {
        return spd;
    }

    public int getAttRate() {
        return attRate;
    }

    public int getPrjSpd() {
        return prjSpd;
    }

    public int getUniqueResourceMax() {
        return uniqueResourceMax;
    }

    public int getUniqueResource() {
        return uniqueResource;
    }

    public String getUniqueResourceName() {
        return uniqueResourceName;
    }

    public boolean getProtected() {
        return bProtected;
    }

    public int getProtectedTick() {
        return protectedTick;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    public void setAttRate(int attRate) {
        this.attRate = attRate;
    }

    public void setPrjSpd(int prjSpd) {
        this.prjSpd = prjSpd;
    }

    public void setUniqueResource(int uniqueResource) {
        this.uniqueResource = uniqueResource;
    }

    public void modResource(int modValue) {
        uniqueResourceMax += modValue;
    }

    public void modResourceRefill (int modValue) {
        resourceRefillRate += modValue;
    }

    public void refillResource() {
        if (uniqueResource < uniqueResourceMax) {
            if (Cc.getInstance().getTick() % resourceRefillRate == 0) {
                uniqueResource = Math.min(uniqueResourceMax, uniqueResource + 1);
            }
        }
    }

    public void setProtected(boolean bProtected) {
        this.bProtected = bProtected;
    }

    public void setProtectedTick(int protectedTick) {
        this.protectedTick = protectedTick;
    }

    public void resetAttackOrientation() {
        bAttackingLeft = false;
        bAttackingRight = false;
        bAttackingUp = false;
        bAttackingDown = false;
    }

    public void attackDown(boolean bAttackingDown) {
        this.bAttackingDown = bAttackingDown;
    }

    public void attackLeft(boolean bAttackingLeft) {
        this.bAttackingLeft = bAttackingLeft;
    }

    public void attackRight(boolean bAttackingRight) {
        this.bAttackingRight = bAttackingRight;
    }

    public void attackUp(boolean bAttackingUp) {
        this.bAttackingUp = bAttackingUp;
    }

    public void moveLeft(boolean bMovingLeft) {
        this.bMovingLeft = bMovingLeft;
    }

    public void moveRight(boolean bMovingRight) {
        this.bMovingRight = bMovingRight;
    }

    public void moveUp(boolean bMovingUp) {
        this.bMovingUp = bMovingUp;
    }

    public void moveDown(boolean bMovingDown) {
        this.bMovingDown = bMovingDown;
    }

    public void checkGameOver() {
        if (HP <= 0 && !Cc.getInstance().getGodMode()) {
            Cc.getInstance().clearAll();

            Cc.getInstance().resetBooleans();

            Cc.getInstance().setGameOver(true);
            Cc.getInstance().setPlaying(false);
        }
    }

    public void move() {
        //CHECK FOR GAME OVER
        checkGameOver();

        //RESOURCE REFILL
        refillResource();

        //PROTECTED FLICKER
        if (bProtected) {
            if (getColor() == trueColor) {
                setColor(flickerColor);
            } else {
                setColor(trueColor);
            }
        }

        //MOVEMENT CONTROL
        Point pnt = getCenter();
        double dX = pnt.x + getDeltaX();
        double dY = pnt.y + getDeltaY();

        int rad = getRadius();
        int buffer = (int) (rad + Game.WALL_THICKNESS);

        String edge = mapEdgeEncountered();

        //EAST COLLISION
        if (edge.equals("east")) {
            if (Cc.getInstance().getCurrentMapPanel().getEast() != null && !Cc.getInstance().getLocked()) {
                Cc.getInstance().swapCurrentMapPanel(Cc.getInstance().getCurrentMapPanel().getEast());
                setCenter(new Point(buffer, getDim().height / 2));
            } else {
                setCenter(new Point(getDim().width - buffer, pnt.y));
            }

        //WEST COLLISION
        } else if (edge.equals("west")) {
            if (Cc.getInstance().getCurrentMapPanel().getWest() != null && !Cc.getInstance().getLocked()) {
                Cc.getInstance().swapCurrentMapPanel(Cc.getInstance().getCurrentMapPanel().getWest());
                setCenter(new Point(getDim().width - buffer, getDim().height / 2));
            } else {
                setCenter(new Point(buffer, pnt.y));
            }

        //SOUTH COLLISION
        } else if (edge.equals("south")) {
            if (Cc.getInstance().getCurrentMapPanel().getSouth() != null && !Cc.getInstance().getLocked()) {
                Cc.getInstance().swapCurrentMapPanel(Cc.getInstance().getCurrentMapPanel().getSouth());
                setCenter(new Point(getDim().width / 2, buffer));
            } else {
                setCenter(new Point(pnt.x, getDim().height - buffer - Game.southBump));
            }

        //NORTH COLLISION
        } else if (edge.equals("north")) {
            if (Cc.getInstance().getCurrentMapPanel().getNorth() != null && !Cc.getInstance().getLocked()) {
                Cc.getInstance().swapCurrentMapPanel(Cc.getInstance().getCurrentMapPanel().getNorth());
                setCenter(new Point(getDim().width / 2, getDim().height - buffer - Game.southBump));
            } else {
                setCenter(new Point(pnt.x, buffer));
            }

        //NO WALL COLLISION
        } else {
            setCenter(new Point((int) dX, (int) dY));
        }

        int x;
        int y;

        if (bMovingLeft && !bMovingRight) {
            x = -1;
        } else if (!bMovingLeft && bMovingRight) {
            x = 1;
        } else {
            x = 0;
        }
        if (bMovingUp && !bMovingDown) {
            y = -1;
        } else if (!bMovingUp && bMovingDown) {
            y = 1;
        } else {
            y = 0;
        }

        double mod = 1;
        if (x != 0 && y != 0) {
            mod = .707; //sqrt(2) / 2
        }
        double dAdjustX = x * spd * mod;
        double dAdjustY = y * spd * mod;
        setDeltaX(dAdjustX);
        setDeltaY(dAdjustY);

        //ORIENTATION CONTROL
        if (bAttackingLeft && !bAttackingRight) {
            x = -1;
        } else if (!bAttackingLeft && bAttackingRight) {
            x = 1;
        } else {
            x = 0;
        }
        if (bAttackingUp && !bAttackingDown) {
            y = -1;
        } else if (!bAttackingUp && bAttackingDown) {
            y = 1;
        } else {
            y = 0;
        }

        if (x == 0 && y == -1) {
            setOrientation(0);
        } else if (x == -1 && y == -1) {
            setOrientation(315);
        } else if (x == -1 && y == 0) {
            setOrientation(270);
        } else if (x == -1 && y == 1) {
            setOrientation(225);
        } else if (x == 0 && y == 1) {
            setOrientation(180);
        } else if (x == 1 && y == 1) {
            setOrientation(135);
        } else if (x == 1 && y == 0) {
            setOrientation(90);
        } else if (x == 1 && y == -1){
            setOrientation(45);
        }
    }

    public void powerup(PowerUp powerUp) {
        PowerUp.PowerUpType type = powerUp.getType();
        int valueFactor = powerUp.getValueFactor();
        String str = "";
        Sound.playSound("powerup.wav");

        switch (type) {
            case HP_UP:
                str = "Max HP up!";
                setMaxHP(getMaxHP() + valueFactor * 5);
                setHP(getHP() + valueFactor * 5);
                break;
            case DMG_UP:
                str = "Damage up!";
                setDmg(getDmg() + valueFactor * 2);
                break;
            case SPD_UP:
                str = "Speed up!";
                setSpd(getSpd() + valueFactor * 3);
                break;
            case ATT_RATE_UP:
                if (!(this instanceof Trisassin)) {
                    str = "Healed!";
                    updateHP(valueFactor * 5);
                } else {
                    str = "Attack rate up!";
                    setAttRate(getAttRate() - valueFactor);
                }
                break;
            case UNIQUE_UP:
                if (this instanceof Squarior) {
                    str = "Shield capacity up!";
                } else if (this instanceof Trisassin) {
                    str = "Ammo capacity up!";
                } else {
                    str = "Max mana up!";
                }
                modResource(valueFactor * 10);
                break;
            case REGEN_UP:
                if (this instanceof Squarior) {
                    str = "Shield regen up!";
                } else if (this instanceof Trisassin) {
                    str = "Ammo regen up!";
                } else {
                    str = "Max regen up!";
                }
                modResourceRefill(-valueFactor);
                break;
        }

        Cc.getInstance().setPowerUpText(str);
        Cc.getInstance().setPowerUpTextCenter(new Point(Game.DIM.width / 2, (int) (Game.DIM.height * 0.2)));
        Cc.getInstance().setDisplayPowerUpText(true);
        Cc.getInstance().setPowerUpTextTick(Cc.getInstance().getTick());
    }

    public void bombAcquired() {
        Cc.getInstance().setHasBomb(true);

        Cc.getInstance().setPowerUpText("You obtained a bomb! Use it wisely...");
        Cc.getInstance().setPowerUpTextCenter(new Point(Game.DIM.width / 2, (int) (Game.DIM.height * 0.2)));
        Cc.getInstance().setDisplayPowerUpText(true);
        Cc.getInstance().setPowerUpTextTick(Cc.getInstance().getTick());
    }

    public void shieldAcquired() {
        Cc.getInstance().setHasShield(true);

        Cc.getInstance().setPowerUpText("You obtained a shield! This might be useful against those pointy flying lines.");
        Cc.getInstance().setPowerUpTextCenter(new Point(Game.DIM.width / 2, (int) (Game.DIM.height * 0.2)));
        Cc.getInstance().setDisplayPowerUpText(true);
        Cc.getInstance().setPowerUpTextTick(Cc.getInstance().getTick());
    }

    public void updateHP(int HPChange) {
        if (HPChange >= 0) {
            if (HP + HPChange > maxHP) {
                HP = maxHP;
            } else {
                HP += HPChange;
            }
        } else {
            if (!bProtected && !Cc.getInstance().getGodMode()) {
                HP += HPChange;
                Sound.playSound("adventurer_hit.wav");
            }
        }
    }

    public void attack() {

    }
}

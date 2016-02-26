package GeometricKingdom.mvc.model;

import GeometricKingdom.mvc.controller.Game;
import GeometricKingdom.mvc.model.adventurer.Adventurer;
import GeometricKingdom.mvc.model.weapon.FoeProjectile;
import GeometricKingdom.mvc.view.MapPanel;
import GeometricKingdom.sounds.Sound;

import java.awt.*;

/**
 * Created by Jonathan on 11/21/2015.
 */
public class HPEntity extends Sprite {
    protected int maxHP;        // maximum hit points
    protected int HP;           // current hit points
    protected int dmg;          // damage
    protected int spd;          // speed
    protected double accel;

    protected boolean shootsAtAdventurer = false;
    protected int attRate = 25;
    protected int projSpd = 10;

    public HPEntity() {
        super();
        accel = 1;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getHP() {
        return HP;
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

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    public void modStats(MapPanel.MapZone mapZone) {
        int mod = mapZone.ordinal() + 1;
        maxHP *= mod;
        HP = maxHP;
        dmg *= mod;
        spd += mod;
        projSpd += mod;
    }

    public Color randomEnemyColor() {
        int randColorInd = Game.R.nextInt(4);
        Color color = Color.WHITE;
        Color[] colors = new Color[] {Color.CYAN, Color.YELLOW, Color.ORANGE, Color.WHITE};

        return colors[randColorInd];
    }

    public void updateHP(int HPChange) {
        // if a positive HPChange would put this HPEntity at more HP than it has maxHP, instead set HP = maxHP
        if (HP + HPChange > maxHP) {
            HP = maxHP;
        } else {
            HP += HPChange;
        }

        // if this HPEntity is dead, remove it (Adventurers have their own overloaded method for this)
//        if (HP <= 0) {
//            Cc.getInstance().getOpsList().enqueue(this, CollisionOp.Operation.REMOVE);
//            Cc.getInstance().getCurrentMapPanel().getMapPanelFoes().remove(this);
//            Debris newDebris = new SkullCorpse(this);
//            Cc.getInstance().getOpsList().enqueue(newDebris, CollisionOp.Operation.ADD);
//            Cc.getInstance().getCurrentMapPanel().getMapPanelFoes().add(newDebris);
//        }
    }

    public double angleToAdventurer() {
        Adventurer adv = Cc.getInstance().getAdventurer();
        double advX = adv.getCenter().x;
        double advY = adv.getCenter().y;
        double selfX = getCenter().x;
        double selfY = getCenter().y;
        double angle = Math.atan2((advY - selfY), (advX - selfX));
        return angle;
    }

    public void shootAdventurer() {
        shootAdventurer(new FoeProjectile());
    }

    public void shootAdventurer(FoeProjectile weapon) {
        if (shootsAtAdventurer) {
            Sound.playSound("alien_shoots.wav");
            double angle = angleToAdventurer();
            double degAngle = Math.toDegrees(angle);

            weapon.setOrientation((int) degAngle);

            int centerX = (int) (getCenter().getX() + Math.cos(angle) * getRadius());
            int centerY = (int) (getCenter().getY() + Math.sin(angle) * getRadius());
            weapon.setCenter(new Point(centerX, centerY));

            weapon.setDeltaX(getDeltaX() + Math.round(Math.cos(angle) * projSpd));
            weapon.setDeltaY(getDeltaY() + Math.round(Math.sin(angle) * projSpd));

            Cc.getInstance().getOpsList().enqueue(weapon, CollisionOp.Operation.ADD);
        }
    }

    public void moveTowardsAdventurer() {
        double angle = angleToAdventurer();

        setDeltaX(getDeltaX() + Math.round(Math.cos(angle) * accel));
        setDeltaY(getDeltaY() + Math.round(Math.sin(angle) * accel));
    }

    public void move() {
        super.move();
        double currentSpeed = Math.sqrt(Math.pow(getDeltaX(), 2) + Math.pow(getDeltaY(), 2));
        if (currentSpeed > spd * 1.5) {
            double friction = 0.8;
            setDeltaX(getDeltaX() * friction);
            setDeltaY(getDeltaY() * friction);
        }
    }
}

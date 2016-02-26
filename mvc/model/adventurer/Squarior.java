package GeometricKingdom.mvc.model.adventurer;

import GeometricKingdom.mvc.controller.Game;
import GeometricKingdom.mvc.model.Cc;
import GeometricKingdom.sounds.Sound;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jonathan on 11/19/2015.
 */
public class Squarior extends Adventurer {
    private int hitTickMax;
    private int hitTick;

    public Squarior() {
        super();

        dmg = 10;
        uniqueResourceMax = 10;
        uniqueResource = uniqueResourceMax;
        resourceRefillRate = 1;

        uniqueResourceName = "SHIELD";

        hitTickMax = 50;
        hitTick = 0;

        setTeam(Team.FRIEND);
        pntCs = getSquariorPoints();

        assignPolarPoints(pntCs);

        trueColor = Color.RED;
        setColor(trueColor);
        setCenter(new Point(Game.DIM.width / 2, Game.DIM.height / 2));
        setOrientation(0);
    }

    public ArrayList<Point> getSquariorPoints() {
        ArrayList<Point> pntCs = new ArrayList<Point>();

        pntCs.add(new Point(10, 10));
        pntCs.add(new Point(-10, 10));
        pntCs.add(new Point(-10, -10));
        pntCs.add(new Point(10, -10));
        pntCs.add(new Point(10, 10));

        return pntCs;
    }

    public void initializeHitTick() {
        this.hitTick = hitTickMax;
    }

    // the Squarior's shield cannot regenerate if he has been hit recently
    // decreases hit tick as a means of tracking how long it has been since the Squarior was hit
    public void decreaseHitTick() {
        if (hitTick > 0) {
            hitTick -= 1;
        }
    }

    public void refillResource() {
        if (hitTick <= 0) {
            super.refillResource();
        }
    }

    public void updateHP(int HPChange) {
        if (HPChange >= 0) {
            if (HP + HPChange > maxHP) {
                HP = maxHP;
            } else {
                HP += HPChange;
            }
        } else {
            if (!getProtected() && !Cc.getInstance().getGodMode()) {
                Sound.playSound("adventurer_hit.wav");
                initializeHitTick();
                if ((uniqueResource + HPChange) > 0) {
                    uniqueResource += HPChange;
                } else {
                    HPChange += uniqueResource;
                    uniqueResource = 0;
                    HP += HPChange;
                }
            }
        }
    }

    public void move() {
        super.move();
        decreaseHitTick();
    }
}

package GeometricKingdom.mvc.model.enemy;

import GeometricKingdom.mvc.controller.Game;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jonathan on 12/5/2015.
 */
public class SpiderBoss extends Boss {
    private ArrayList<Point> pntCs;

    public SpiderBoss() {
        setTeam(Team.FOE);

        pntCs = getSpiderPoints();
        assignPolarPoints(pntCs);
        setCenter(Game.getCenterOfPanel());

        setRadProportion(1/5.0);
        setRadius((int) (Game.DIM.width * getRadProportion()));

        maxHP = 500;
        HP = maxHP;
        spd = 2;
        dmg = 5;

        setBounceable(false);
        setColor(Color.MAGENTA);
        setOrientation(-90);

        setDeltaY(spd);
    }

    // spider points designed by my brother Scott Hodnefield
    public ArrayList<Point> getSpiderPoints() {
        ArrayList<Point> pntCs = new ArrayList<Point>();

        pntCs.add(new Point(1, 0));
        pntCs.add(new Point(-1, 0));
        pntCs.add(new Point(1, 0));
        pntCs.add(new Point(6, 4));
        pntCs.add(new Point(14, 5));
        pntCs.add(new Point(16, 9));
        pntCs.add(new Point(14, 5));
        pntCs.add(new Point(6, 4));
        pntCs.add(new Point(6, 8));
        pntCs.add(new Point(12, 10));
        pntCs.add(new Point(13, 13));
        pntCs.add(new Point(12, 10));
        pntCs.add(new Point(6, 8));
        pntCs.add(new Point(3, 12));
        pntCs.add(new Point(-2, 12));
        pntCs.add(new Point(0, 15));
        pntCs.add(new Point(0, 12));
        pntCs.add(new Point(0, 15));
        pntCs.add(new Point(2, 12));
        pntCs.add(new Point(-3, 12));
        pntCs.add(new Point(-6, 8));
        pntCs.add(new Point(-12, 10));
        pntCs.add(new Point(-13, 13));
        pntCs.add(new Point(-12, 10));
        pntCs.add(new Point(-6, 8));
        pntCs.add(new Point(-6, 4));
        pntCs.add(new Point(-14, 5));
        pntCs.add(new Point(-16, 9));
        pntCs.add(new Point(-14, 5));
        pntCs.add(new Point(-6, 4));
        pntCs.add(new Point(-1, 0));
        pntCs.add(new Point(-4, -2));
        pntCs.add(new Point(-9, -4));
        pntCs.add(new Point(-11, -8));
        pntCs.add(new Point(-9, -4));
        pntCs.add(new Point(-4, -2));
        pntCs.add(new Point(-4, -5));
        pntCs.add(new Point(-8, -9));
        pntCs.add(new Point(-6, -12));
        pntCs.add(new Point(-8, -9));
        pntCs.add(new Point(-4, -5));
        pntCs.add(new Point(-2, -9));
        pntCs.add(new Point(2, -9));
        pntCs.add(new Point(0, -11));
        pntCs.add(new Point(0, -12));
        pntCs.add(new Point(1, -10));
        pntCs.add(new Point(0, -12));
        pntCs.add(new Point(-1, -10));
        pntCs.add(new Point(0, -11));
        pntCs.add(new Point(-2, -9));
        pntCs.add(new Point(2, -9));
        pntCs.add(new Point(4, -5));
        pntCs.add(new Point(8, -9));
        pntCs.add(new Point(6, -12));
        pntCs.add(new Point(8, -9));
        pntCs.add(new Point(4, -5));
        pntCs.add(new Point(4, -2));
        pntCs.add(new Point(9, -4));
        pntCs.add(new Point(11, -8));
        pntCs.add(new Point(9, -4));
        pntCs.add(new Point(4, -2));
        pntCs.add(new Point(1, 0));

        return pntCs;
    }

    public void move() {
        if (getCenter().y > (5 * Game.DIM.height / 8)) {
            setDeltaY(-spd);
        } else if (getCenter().y < (3 * Game.DIM.height / 8)) {
            setDeltaY(spd);
        }
        super.move();
    }
}
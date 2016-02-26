package GeometricKingdom.mvc.model.enemy;

import GeometricKingdom.mvc.controller.Game;
import GeometricKingdom.mvc.model.Cc;
import GeometricKingdom.mvc.model.HPEntity;
import GeometricKingdom.mvc.view.MapPanel;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jonathan on 11/20/2015.
 */
public class Hexagon extends HPEntity {
    ArrayList<Point> pntCs;

    public Hexagon() {

        maxHP = 10;
        HP = maxHP;
        spd = Game.R.nextInt(4) + 3;
        dmg = 5;

        if (Cc.getInstance().getMapZone() == MapPanel.MapZone.CAVE) {
            pntCs = getSpiderPoints();
            setFilled(false);
        } else {
            pntCs = getHexagonPoints();
            setFilled(true);
        }
        assignPolarPoints(pntCs);

        setRadProportion(1/30.0);
        setRadius((int) (Game.DIM.width * getRadProportion()));

        setTeam(Team.FOE);
        setColor(randomEnemyColor());
        setOrientation(-90);
        setBounceable(true);
    }

    public void move() {
        moveTowardsAdventurer();
        super.move();
    }

    // Hexagon design
    public ArrayList<Point> getHexagonPoints() {
        ArrayList<Point> pntCs = new ArrayList<Point>();

        pntCs.add(new Point(0, 6));
        pntCs.add(new Point(-6, 3));
        pntCs.add(new Point(-6, -3));
        pntCs.add(new Point(0, -6));
        pntCs.add(new Point(6, -3));
        pntCs.add(new Point(6, 3));
        pntCs.add(new Point(0, 6));

        return pntCs;
    }

    // Spider design
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
}

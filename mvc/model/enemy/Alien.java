package GeometricKingdom.mvc.model.enemy;

import GeometricKingdom.mvc.controller.Game;
import GeometricKingdom.mvc.model.HPEntity;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jonathan on 12/3/2015.
 */
public class Alien extends HPEntity {
    private ArrayList<Point> pntCs;

    public Alien() {
        super();
        maxHP = 15;
        HP = maxHP;
        dmg = 5;
        spd = 10;

        setDeltaX(0);
        setDeltaY(0);

        shootsAtAdventurer = true;

        setRadProportion(1/30.0);
        setRadius((int) (Game.DIM.width * getRadProportion()));

        setTeam(Team.FOE);
        setColor(randomEnemyColor());
        setBounceable(true);

        pntCs = getAlienPoints();
        assignPolarPoints(pntCs);
        setOrientation(-90);

        setFilled(true);
    }

    // Alien points borrowed from my classmate Scott Fennelly
    public ArrayList<Point> getAlienPoints() {
        ArrayList<Point> pntCs = new ArrayList<Point>();

        // Alien design borrowed from my classmate Scott Fennelly
        pntCs.add(new Point(3, 4));
        pntCs.add(new Point(4, 4));
        pntCs.add(new Point(4, 3));
        pntCs.add(new Point(3, 3));
        pntCs.add(new Point(3, 2));
        pntCs.add(new Point(3, 1));
        pntCs.add(new Point(3, 0));
        pntCs.add(new Point(2, 0));
        pntCs.add(new Point(2, 1));
        pntCs.add(new Point(3, 1));
        pntCs.add(new Point(3, 2));
        pntCs.add(new Point(4, 2));
        pntCs.add(new Point(4, 1));
        pntCs.add(new Point(5, 1));
        pntCs.add(new Point(5, 0));
        pntCs.add(new Point(6, 0));
        pntCs.add(new Point(6, -1));
        pntCs.add(new Point(6, -2));
        pntCs.add(new Point(6, -3));
        pntCs.add(new Point(5, -3));
        pntCs.add(new Point(5, -2));
        pntCs.add(new Point(5, -1));
        pntCs.add(new Point(4, -1));
        pntCs.add(new Point(4, -2));
        pntCs.add(new Point(4, -3));
        pntCs.add(new Point(3, -3));
        pntCs.add(new Point(3, -4));
        pntCs.add(new Point(2, -4));
        pntCs.add(new Point(1, -4));
        pntCs.add(new Point(1, -3));
        pntCs.add(new Point(2, -3));
        pntCs.add(new Point(3, -3));
        pntCs.add(new Point(3, -2));
        pntCs.add(new Point(2, -2));
        pntCs.add(new Point(1, -2));
        pntCs.add(new Point(0, -2));
        pntCs.add(new Point(-1, -2));
        pntCs.add(new Point(-2, -2));
        pntCs.add(new Point(-3, -2));
        pntCs.add(new Point(-3, -3));
        pntCs.add(new Point(-2, -3));
        pntCs.add(new Point(-1, -3));
        pntCs.add(new Point(-1, -4));
        pntCs.add(new Point(-2, -4));
        pntCs.add(new Point(-3, -4));
        pntCs.add(new Point(-3, -3));
        pntCs.add(new Point(-4, -3));
        pntCs.add(new Point(-4, -2));
        pntCs.add(new Point(-4, -1));
        pntCs.add(new Point(-5, -1));
        pntCs.add(new Point(-5, -2));
        pntCs.add(new Point(-5, -3));
        pntCs.add(new Point(-6, -3));
        pntCs.add(new Point(-6, -2));
        pntCs.add(new Point(-6, -1));
        pntCs.add(new Point(-6, 0));
        pntCs.add(new Point(-5, 0));
        pntCs.add(new Point(-5, 1));
        pntCs.add(new Point(-4, 1));
        pntCs.add(new Point(-4, 2));
        pntCs.add(new Point(-3, 2));
        pntCs.add(new Point(-3, 1));
        pntCs.add(new Point(-3, 0));
        pntCs.add(new Point(-2, 0));
        pntCs.add(new Point(-2, 1));
        pntCs.add(new Point(-3, 1));
        pntCs.add(new Point(-3, 2));
        pntCs.add(new Point(-3, 3));
        pntCs.add(new Point(-4, 3));
        pntCs.add(new Point(-4, 4));
        pntCs.add(new Point(-3, 4));
        pntCs.add(new Point(-3, 3));
        pntCs.add(new Point(-2, 3));
        pntCs.add(new Point(-2, 2));
        pntCs.add(new Point(-1, 2));
        pntCs.add(new Point(0, 2));
        pntCs.add(new Point(1, 2));
        pntCs.add(new Point(2, 2));
        pntCs.add(new Point(2, 3));
        pntCs.add(new Point(3, 3));

        return pntCs;
    }

    public void move() {
        super.move();
    }
}

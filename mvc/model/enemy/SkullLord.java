package GeometricKingdom.mvc.model.enemy;

import GeometricKingdom.mvc.controller.Game;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jonathan on 12/6/2015.
 */
public class SkullLord extends Boss {
    private ArrayList<Point> pntCs;

    public SkullLord() {
        setTeam(Team.FOE);

        pntCs = getSkullPoints();
        assignPolarPoints(pntCs);
        setCenter(Game.getCenterOfPanel());

        setRadProportion(1/4.0);
        setRadius((int) (Game.DIM.width * getRadProportion()));

        maxHP = 100;
        HP = maxHP;
        spd = 2;
        dmg = 5;

        setBounceable(false);
        setColor(Color.LIGHT_GRAY);
        setFilled(true);
        setOrientation(-90);

        setDeltaY(spd);
    }

    public ArrayList<Point> getSkullPoints() {
        ArrayList<Point> pntCs = new ArrayList<Point>();

        //skull outline
        pntCs.add(new Point(4, -2));
        pntCs.add(new Point(4, 2));
        pntCs.add(new Point(6, 7));
        pntCs.add(new Point(3, 12));
        pntCs.add(new Point(-3, 12));
        pntCs.add(new Point(-6, 7));
        pntCs.add(new Point(-4, 2));
        pntCs.add(new Point(-4, -2));
        pntCs.add(new Point(4, -2));

        //right eye
        pntCs.add(new Point(3, -2));
        pntCs.add(new Point(3, 7));
        pntCs.add(new Point(3, 9));
        pntCs.add(new Point(2, 9));
        pntCs.add(new Point(2, 7));
        pntCs.add(new Point(3, 7));
        pntCs.add(new Point(3, -2));

        //left eye
        pntCs.add(new Point(-3, -2));
        pntCs.add(new Point(-3, 7));
        pntCs.add(new Point(-3, 9));
        pntCs.add(new Point(-2, 9));
        pntCs.add(new Point(-2, 7));
        pntCs.add(new Point(-3, 7));
        pntCs.add(new Point(-3, -2));

        //mouth
        pntCs.add(new Point(-2, -2));
        pntCs.add(new Point(-2, 0));
        pntCs.add(new Point(-2, 3));
        pntCs.add(new Point(-1, 2));
        pntCs.add(new Point(0, 3));
        pntCs.add(new Point(1, 2));
        pntCs.add(new Point(2, 3));
        pntCs.add(new Point(2, 0));
        pntCs.add(new Point(1, 1));
        pntCs.add(new Point(0, 0));
        pntCs.add(new Point(-1, 1));
        pntCs.add(new Point(-2, 0));
        pntCs.add(new Point(-2, -2));

        //nose
        pntCs.add(new Point(-4, -2));
        pntCs.add(new Point(-4, -2));
        pntCs.add(new Point(-1, 6));
        pntCs.add(new Point(1, 6));
        pntCs.add(new Point(0, 4));
        pntCs.add(new Point(-1, 6));
        pntCs.add(new Point(-4, -2));

        return pntCs;
    }
}

package GeometricKingdom.mvc.model.floater;

import GeometricKingdom.mvc.controller.Game;
import GeometricKingdom.mvc.model.Sprite;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jonathan on 12/6/2015.
 */
public class Bomb extends Sprite {
    private ArrayList<Point> pntCs;

    public Bomb() {
        setRadProportion(1/25.0);
        setRadius((int) (Game.DIM.width * getRadProportion()));

        setTeam(Team.FLOATER);
        setBounceable(true);
        setCenter(Game.getCenterOfPanel());

        pntCs = getBombPoints();
        assignPolarPoints(pntCs);
        setOrientation(-90);

        setColor(Color.WHITE);
        setFilled(false);
    }

    public ArrayList<Point> getBombPoints() {
        ArrayList<Point> pntCs = new ArrayList<Point>();

        pntCs.add(new Point(0, 4));
        pntCs.add(new Point(0, 5));
        pntCs.add(new Point(2, 5));
        pntCs.add(new Point(3, 4));
        pntCs.add(new Point(2, 5));
        pntCs.add(new Point(0, 5));
        pntCs.add(new Point(0, 4));
        pntCs.add(new Point(-1, 4));
        pntCs.add(new Point(-1, 3));
        pntCs.add(new Point(1, 3));
        pntCs.add(new Point(-1, 3));
        pntCs.add(new Point(-4, 2));
        pntCs.add(new Point(-5, 0));
        pntCs.add(new Point(-5, -2));
        pntCs.add(new Point(-4, -4));
        pntCs.add(new Point(-2, -5));
        pntCs.add(new Point(2, -5));
        pntCs.add(new Point(4, -4));
        pntCs.add(new Point(5, -2));
        pntCs.add(new Point(5, 0));
        pntCs.add(new Point(4, 2));
        pntCs.add(new Point(1, 3));
        pntCs.add(new Point(1, 4));
        pntCs.add(new Point(0, 4));

        return pntCs;
    }
}

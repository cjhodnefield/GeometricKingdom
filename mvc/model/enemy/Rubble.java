package GeometricKingdom.mvc.model.enemy;

import GeometricKingdom.mvc.controller.Game;
import GeometricKingdom.mvc.model.HPEntity;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jonathan on 12/5/2015.
 */
public class Rubble extends HPEntity {
    private ArrayList<Point> pntCs;

    public Rubble() {
        setTeam(Team.OBSTACLE);
        setColor(Color.GRAY);
        setTraced(true, Color.BLACK);
        setFilled(true);
        setBounceable(false);
        setCenter(Game.getCenterOfPanel());
        setRadProportion(1 / 10.0);
        setRadius((int) (Game.DIM.width * getRadProportion()));

        pntCs = getRubblePoints();
        assignPolarPoints(pntCs);

        int randOrientMod = Game.R.nextInt(181);
        setOrientation(-90 + randOrientMod);
    }

    public ArrayList<Point> getRubblePoints() {
        ArrayList<Point> pntCs = new ArrayList<Point>();

        // cave outer trace
        pntCs.add(new Point(1, 6));
        pntCs.add(new Point(5, 5));
        pntCs.add(new Point(6, 4));
        pntCs.add(new Point(7, 0));
        pntCs.add(new Point(7, -1));
        pntCs.add(new Point(5, -5));
        pntCs.add(new Point(2, -6));
        pntCs.add(new Point(-1, -6));
        pntCs.add(new Point(-5, -5));
        pntCs.add(new Point(-7, 1));
        pntCs.add(new Point(-5, 5));
        pntCs.add(new Point(-1, 6));
        pntCs.add(new Point(1, 6));

        return pntCs;
    }

    public void move() {

    }
}

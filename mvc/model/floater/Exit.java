package GeometricKingdom.mvc.model.floater;

import GeometricKingdom.mvc.controller.Game;
import GeometricKingdom.mvc.model.Sprite;
import GeometricKingdom.mvc.view.MapPanel;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jonathan on 12/4/2015.
 */
public class Exit extends Sprite {
    private MapPanel.MapZone mapZone;
    private ArrayList<Point> pntCs;
    private boolean isExit = false;

    public Exit(MapPanel.MapZone mapZone) {
        this.mapZone = mapZone;

        setTeam(Team.FLOATER);
        setFilled(true);
        setCenter(Game.getCenterOfPanel());
        setRadProportion(1/10.0);
        setRadius((int) (Game.DIM.width * getRadProportion()));

        switch (mapZone) {
            case FOREST:
                pntCs = getCavePoints();
                setColor(Color.DARK_GRAY);
                break;
            case CAVE:
                pntCs = getCastlePoints();
                setColor(Color.LIGHT_GRAY);
                setTraced(true, getColor());
                break;
            case CASTLE:
                pntCs = getStairPoints();
                setColor(Color.LIGHT_GRAY);
                break;
        }

        assignPolarPoints(pntCs);;
        setOrientation(-90);
    }

    public MapPanel.MapZone getMapZone() {
        return mapZone;
    }

    public ArrayList<Point> getCavePoints() {
        ArrayList<Point> pntCs = new ArrayList<Point>();

        // cave outer trace
        pntCs.add(new Point(-9, -5));
        pntCs.add(new Point(10, -5));
        pntCs.add(new Point(8, 4));
        pntCs.add(new Point(6, 6));
        pntCs.add(new Point(-1, 7));
        pntCs.add(new Point(-5, 6));
        pntCs.add(new Point(-8, 3));
        pntCs.add(new Point(-9, -5));

        // cave inner trace
        pntCs.add(new Point(-6, -4));
        pntCs.add(new Point(7, -4));
        pntCs.add(new Point(6, 2));
        pntCs.add(new Point(4, 4));
        pntCs.add(new Point(1, 5));
        pntCs.add(new Point(-3, 4));
        pntCs.add(new Point(-5, 3));
        pntCs.add(new Point(-6, -4));
        pntCs.add(new Point(-9, -5));

        return pntCs;
    }

    // castle points designed by my brother Scott Hodnefield
    public ArrayList<Point> getCastlePoints() {
        ArrayList<Point> pntCs = new ArrayList<Point>();

        pntCs.add(new Point(-6, -6));
        pntCs.add(new Point(-6, 6));
        pntCs.add(new Point(-5, 6));
        pntCs.add(new Point(-5, 5));
        pntCs.add(new Point(-4, 5));
        pntCs.add(new Point(-4, 6));
        pntCs.add(new Point(-3, 6));
        pntCs.add(new Point(-3, 2));
        pntCs.add(new Point(-2, 2));
        pntCs.add(new Point(-2, 3));
        pntCs.add(new Point(-1, 3));
        pntCs.add(new Point(-1, 2));
        pntCs.add(new Point(1, 2));
        pntCs.add(new Point(1, 3));
        pntCs.add(new Point(2, 3));
        pntCs.add(new Point(2, 2));
        pntCs.add(new Point(3, 2));
        pntCs.add(new Point(3, 6));
        pntCs.add(new Point(4, 6));
        pntCs.add(new Point(4, 5));
        pntCs.add(new Point(5, 5));
        pntCs.add(new Point(5, 6));
        pntCs.add(new Point(6, 6));
        pntCs.add(new Point(6, -6));
        pntCs.add(new Point(5, -6));
        pntCs.add(new Point(5, 3));
        pntCs.add(new Point(5, 4));
        pntCs.add(new Point(4, 4));
        pntCs.add(new Point(4, 3));
        pntCs.add(new Point(5, 3));
        pntCs.add(new Point(5, -6));
        pntCs.add(new Point(-5, -6));
        pntCs.add(new Point(-5, 3));
        pntCs.add(new Point(-5, 4));
        pntCs.add(new Point(-4, 4));
        pntCs.add(new Point(-4, 3));
        pntCs.add(new Point(-5, 3));
        pntCs.add(new Point(-5, -6));

        // Door frame
        pntCs.add(new Point(-2, -6));
        pntCs.add(new Point(-2, -2));
        pntCs.add(new Point(2, -2));
        pntCs.add(new Point(2, -6));
        pntCs.add(new Point(-2, -6));

        return pntCs;
    }

    // stairs points designed by my brother Scott Hodnefield
    public ArrayList<Point> getStairPoints() {
        ArrayList<Point> pntCs = new ArrayList<Point>();

        pntCs.add(new Point(-11, -11));
        pntCs.add(new Point(-11, 4));
        pntCs.add(new Point(-3, 13));
        pntCs.add(new Point(0, 13));
        pntCs.add(new Point(-8, 4));
        pntCs.add(new Point(-11, 4));
        pntCs.add(new Point(-8, 4));
        pntCs.add(new Point(-8, 1));
        pntCs.add(new Point(0, 10));
        pntCs.add(new Point(0, 13));
        pntCs.add(new Point(0, 10));
        pntCs.add(new Point(3, 10));
        pntCs.add(new Point(-5, 1));
        pntCs.add(new Point(-8, 1));
        pntCs.add(new Point(-5, 1));
        pntCs.add(new Point(-5, -2));
        pntCs.add(new Point(3, 7));
        pntCs.add(new Point(3, 10));
        pntCs.add(new Point(3, 7));
        pntCs.add(new Point(6, 7));
        pntCs.add(new Point(-2, -2));
        pntCs.add(new Point(-5, -2));
        pntCs.add(new Point(-2, -2));
        pntCs.add(new Point(-2, -5));
        pntCs.add(new Point(6, 4));
        pntCs.add(new Point(6, 7));
        pntCs.add(new Point(6, 4));
        pntCs.add(new Point(9, 4));
        pntCs.add(new Point(1, -5));
        pntCs.add(new Point(-2, -5));
        pntCs.add(new Point(1, -5));
        pntCs.add(new Point(1, -8));
        pntCs.add(new Point(9, 1));
        pntCs.add(new Point(9, 4));
        pntCs.add(new Point(9, 1));
        pntCs.add(new Point(12, 1));
        pntCs.add(new Point(4, -8));
        pntCs.add(new Point(1, -8));
        pntCs.add(new Point(4, -8));
        pntCs.add(new Point(4, -11));
        pntCs.add(new Point(12, -2));
        pntCs.add(new Point(12, 1));
        pntCs.add(new Point(12, -2));
        pntCs.add(new Point(4, -11));
        pntCs.add(new Point(-11, -11));

        return pntCs;
    }

    public boolean isExit() {
        return isExit;
    }

    public void setExit(boolean isExit) {
        this.isExit = isExit;
    }

    public void move() {

    }
}

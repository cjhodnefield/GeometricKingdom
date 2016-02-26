package GeometricKingdom.mvc.model.floater;

import GeometricKingdom.mvc.controller.Game;
import GeometricKingdom.mvc.model.Sprite;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jonathan on 12/6/2015.
 */
public class Shield extends Sprite {
    private ArrayList<Point> pntCs;

    public Shield() {
        setRadProportion(1/25.0);
        setRadius((int) (Game.DIM.width * getRadProportion()));

        setTeam(Team.FLOATER);
        setBounceable(true);
        setCenter(Game.getCenterOfPanel());

        pntCs = getShieldPoints();
        assignPolarPoints(pntCs);
        setOrientation(-90);

        setColor(Color.ORANGE);
        setFilled(true);
    }

    public ArrayList<Point> getShieldPoints() {
        ArrayList<Point> pntCs = new ArrayList<Point>();

        pntCs.add(new Point(0, -6));
        pntCs.add(new Point(4, -2));
        pntCs.add(new Point(4, 4));
        pntCs.add(new Point(2, 3));
        pntCs.add(new Point(0, 4));
        pntCs.add(new Point(-2, 3));
        pntCs.add(new Point(-4, 4));
        pntCs.add(new Point(-4, -2));
        pntCs.add(new Point(0, -6));

        return pntCs;
    }
}

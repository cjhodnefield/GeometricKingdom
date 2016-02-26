package GeometricKingdom.mvc.model.debris;

import GeometricKingdom.mvc.model.Sprite;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jonathan on 11/22/2015.
 */
public class Debris extends Sprite {

    public Debris() {
        super();
        setTeam(Team.DEBRIS);
        setColor(Color.RED);
        ArrayList<Point> pntCs = new ArrayList<Point>();

        pntCs.add(new Point(0, 0));
        pntCs.add(new Point(10, 10));
        pntCs.add(new Point(0, 0));
        pntCs.add(new Point(-10, 10));
        pntCs.add(new Point(0, 0));
        pntCs.add(new Point(-10, -10));
        pntCs.add(new Point(0, 0));
        pntCs.add(new Point(10, -10));
        pntCs.add(new Point(0, 0));

        assignPolarPoints(pntCs);
    }

    public void move() {

    }
}

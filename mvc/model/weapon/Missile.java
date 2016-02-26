package GeometricKingdom.mvc.model.weapon;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jonathan on 12/4/2015.
 */
public class Missile extends FoeProjectile {
    public Missile() {
        super();
        setTeam(Team.FOE);

        dmg = 5;
        setRadius(10);
        setExpire(150);

        // Hexagon design
        ArrayList<Point> pntCs = new ArrayList<Point>();
        pntCs.add(new Point(1, 3));
        pntCs.add(new Point(0, 5));
        pntCs.add(new Point(-1, 3));
        pntCs.add(new Point(-1, -3));
        pntCs.add(new Point(1, -3));
        pntCs.add(new Point(1, 3));
        assignPolarPoints(pntCs);

        setColor(Color.ORANGE);
        setFilled(true);
    }
}

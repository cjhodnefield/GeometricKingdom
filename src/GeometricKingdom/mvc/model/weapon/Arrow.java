package GeometricKingdom.mvc.model.weapon;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jonathan on 12/5/2015.
 */
public class Arrow extends FoeProjectile {
    public Arrow() {
        super();
        setTeam(Team.FOE);

        dmg = 1000;
        setRadius(8);
        setExpire(150);

        // Arrow design
        ArrayList<Point> pntCs = new ArrayList<Point>();
        pntCs.add(new Point(3, 0));
        pntCs.add(new Point(3, -1));
        pntCs.add(new Point(5, 0));
        pntCs.add(new Point(3, 1));
        pntCs.add(new Point(3, 0));
        pntCs.add(new Point(-1, 0));
        pntCs.add(new Point(-4, 0));
        pntCs.add(new Point(-1, 0));
        pntCs.add(new Point(-4, 1));
        pntCs.add(new Point(-1, 0));
        pntCs.add(new Point(-4, -1));
        pntCs.add(new Point(-1, 0));

        assignPolarPoints(pntCs);

        setColor(Color.YELLOW);
        setFilled(false);
    }
}

package GeometricKingdom.mvc.model.weapon;

import GeometricKingdom.mvc.model.Cc;
import GeometricKingdom.mvc.model.CollisionOp;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jonathan on 12/3/2015.
 */
public class FoeProjectile extends Weapon {

    public FoeProjectile() {
        super();
        setTeam(Team.FOE);

        dmg = 2;
        setRadius(5);
        setExpire(30);
        setColor(Color.MAGENTA);

        // Hexagon design
        ArrayList<Point> pntCs = new ArrayList<Point>();
        pntCs.add(new Point(1, 3));
        pntCs.add(new Point(-1, 3));
        pntCs.add(new Point(-1, -3));
        pntCs.add(new Point(1, -3));
        pntCs.add(new Point(1, 3));
        assignPolarPoints(pntCs);

        setFilled(true);
    }

    public void move(){

        String edge = mapEdgeEncountered();
        if (!edge.equals("none")) {
            Cc.getInstance().getOpsList().enqueue(this, CollisionOp.Operation.REMOVE);
        }

        super.move();

        if (getExpire() == 0)
            Cc.getInstance().getOpsList().enqueue(this, CollisionOp.Operation.REMOVE);
        else
            setExpire(getExpire() - 1);

    }
}

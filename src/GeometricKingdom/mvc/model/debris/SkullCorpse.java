package GeometricKingdom.mvc.model.debris;

import GeometricKingdom.mvc.controller.Game;
import GeometricKingdom.mvc.model.Movable;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jonathan on 12/4/2015.
 */
public class SkullCorpse extends Debris {

    public SkullCorpse(Movable mov) {
        super();
        setTeam(Team.DEBRIS);
        setCenter(mov.getCenter());
        setRadius(mov.getRadius());

        ArrayList<Point> pntCs = getSkullPoints();
        assignPolarPoints(pntCs);
        int orientation = Game.R.nextInt(41);
        setOrientation(-70 + orientation);

        setColor(Color.LIGHT_GRAY);
        setFilled(true);
    }

    // Skull design made by my brother Scott Hodnefield
    public ArrayList<Point> getSkullPoints() {
        ArrayList<Point> pntCs = new ArrayList<Point>();

        //upper right bone
        pntCs.add(new Point(4, -2));
        pntCs.add(new Point(9, 0));
        pntCs.add(new Point(10, 2));
        pntCs.add(new Point(12, -3));
        pntCs.add(new Point(10, -2));
        pntCs.add(new Point(0, -6));

        //lower right bone
        pntCs.add(new Point(9, -9));
        pntCs.add(new Point(10, -11));
        pntCs.add(new Point(12, -7));
        pntCs.add(new Point(10, -7));

        //upper left bone
        pntCs.add(new Point(-8, -1));
        pntCs.add(new Point(-9, 1));
        pntCs.add(new Point(-12, -2));
        pntCs.add(new Point(-10, -2));
        pntCs.add(new Point(0, -6));

        //lower left bone
        pntCs.add(new Point(-8, -9));
        pntCs.add(new Point(-9, -11));
        pntCs.add(new Point(-11, -7));
        pntCs.add(new Point(-9, -7));
        pntCs.add(new Point(4, -2));

        //skull outline
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

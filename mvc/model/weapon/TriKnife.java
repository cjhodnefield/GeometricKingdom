package GeometricKingdom.mvc.model.weapon;

import GeometricKingdom.mvc.model.Cc;
import GeometricKingdom.mvc.model.CollisionOp;
import GeometricKingdom.mvc.model.adventurer.Adventurer;

import java.awt.*;
import java.util.ArrayList;

/**
 * Slightly modified Bullet class from Asteroids source code
 */
public class TriKnife extends Weapon {

    private final int EXPIRE_TIME = 20;

	public TriKnife(Adventurer adventurer) {

		super();
		setTeam(Team.FRIEND);

        dmg = 5;

		//defined the points on a cartesian grid
		ArrayList<Point> pntCs = new ArrayList<Point>();

        pntCs.add(new Point(10, 0));
        pntCs.add(new Point(-10, -10));
        pntCs.add(new Point(-10, 10));

		assignPolarPoints(pntCs);

		setColor(Color.GREEN);

		//a bullet expires after 20 frames
		setExpire(EXPIRE_TIME);
		setRadius((int) (adventurer.getRadius() * .125));

        int tipOrientation = adventurer.getOrientation() - 90;
        if (tipOrientation > 360) {
            tipOrientation -= 360;
        }

		//everything is relative to the falcon ship that fired the bullet
		setDeltaX( adventurer.getDeltaX() +
				Math.cos( Math.toRadians(tipOrientation) ) * adventurer.getPrjSpd());
		setDeltaY( adventurer.getDeltaY() +
				Math.sin( Math.toRadians(tipOrientation) ) * adventurer.getPrjSpd());

        int centerX = (int) (adventurer.getCenter().getX() + Math.cos(Math.toRadians(tipOrientation)) * adventurer.getRadius());
        int centerY = (int) (adventurer.getCenter().getY() + Math.sin(Math.toRadians(tipOrientation)) * adventurer.getRadius());
        setCenter( new Point(centerX, centerY) );

		//set the bullet orientation to the falcon (ship) orientation
		setOrientation(adventurer.getOrientation());
	}

	public TriKnife(Adventurer adventurer, int num) {
        this(adventurer);
        int tipOrientation = adventurer.getOrientation() - 90;
        if (num == 0) {
            tipOrientation = adventurer.getOrientation() - 90 - 30;
            setOrientation(adventurer.getOrientation() - 30);
        } else if (num == 1) {
            tipOrientation = adventurer.getOrientation() - 90 + 30;
            setOrientation(adventurer.getOrientation() + 30);
        }
        //everything is relative to the falcon ship that fired the bullet
        setDeltaX( adventurer.getDeltaX() +
                Math.cos( Math.toRadians(tipOrientation) ) * adventurer.getPrjSpd());
        setDeltaY( adventurer.getDeltaY() +
                Math.sin( Math.toRadians(tipOrientation) ) * adventurer.getPrjSpd());

        int centerX = (int) (adventurer.getCenter().getX() + Math.cos(Math.toRadians(tipOrientation)) * adventurer.getRadius());
        int centerY = (int) (adventurer.getCenter().getY() + Math.sin(Math.toRadians(tipOrientation)) * adventurer.getRadius());
        setCenter( new Point(centerX, centerY) );
    }

	//implementing the expire functionality in the move method - added by Dmitriy
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

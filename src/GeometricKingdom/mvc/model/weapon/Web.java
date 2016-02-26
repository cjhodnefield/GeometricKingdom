package GeometricKingdom.mvc.model.weapon;

import GeometricKingdom.mvc.controller.Game;
import GeometricKingdom.mvc.model.Cc;
import GeometricKingdom.mvc.model.CollisionOp;
import GeometricKingdom.mvc.model.adventurer.Adventurer;
import GeometricKingdom.mvc.model.enemy.SpiderBoss;

import java.awt.*;

/**
 * Created by Jonathan on 12/5/2015.
 */
public class Web extends FoeProjectile {
    private Point wallPoint;
    private Point spiderPoint;
    private SpiderBoss spiderBoss;
    private int spd;
    private double radRatio;

    public Web(SpiderBoss spiderBoss, Point wallPoint) {
        setTeam(Team.FOE);

        this.spiderBoss = spiderBoss;
        this.wallPoint = wallPoint;
        radRatio = 0.81;
        this.spiderPoint = new Point(spiderBoss.getCenter().x, (int) (spiderBoss.getCenter().y - spiderBoss.getRadius() * radRatio));

        dmg = 0;
        spd = 10;
        setDeltaY(spiderBoss.getDeltaY());
    }

    // distance from a point to a line using the endpoints of the line
    // taken from here: http://www.ahristov.com/tutorial/geometry-games/point-line-distance.html
    public int distanceTo(Adventurer adv) {
        Point P = adv.getCenter();
        Point A = wallPoint;
        Point B = spiderPoint;

        double denominator = Math.sqrt((B.x - A.x) * (B.x - A.x) + (B.y - A.y) * (B.y - A.y));
        double numerator = Math.abs((B.y - A.y)*P.x - (B.x - A.x)*P.y + B.x*A.y - B.y*A.x);
        return (int) (numerator / denominator);
//        return (int) (Math.abs((P.x - A.x) * (B.y - A.y) - (P.y - A.y) * (B.x - A.x)) / normalLength);
    }

    public void move() {
        if (spiderBoss.getHP() <= 0) {
            Cc.getInstance().getOpsList().enqueue(this, CollisionOp.Operation.REMOVE);
            System.out.println("gone");
        }

        int X = spiderPoint.x;

        spiderPoint.setLocation(new Point(X, (int) (Cc.getInstance().getSpiderBoss().getCenter().y - Cc.getInstance().getSpiderBoss().getRadius() * radRatio)));

        int width = Game.DIM.width;
        int height = Game.DIM.height;
        int x = wallPoint.x;
        int y = wallPoint.y;

        int update;

        if (y == 0 && x < width) {
            update = Math.min(x + spd, width);
            wallPoint.setLocation(update, y);
        } else if (x == width && y < height) {
            update = Math.min(y + spd, height);
            wallPoint.setLocation(x, update);
        } else if (y == height && x > 0) {
            update = Math.max(x - spd, 0);
            wallPoint.setLocation(update, y);
        } else {
            update = Math.max(y - spd, 0);
            wallPoint.setLocation(x, update);
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawLine(wallPoint.x, wallPoint.y, spiderPoint.x, spiderPoint.y);
    }
}

package GeometricKingdom.mvc.model.weapon;

import GeometricKingdom.mvc.model.Cc;
import GeometricKingdom.mvc.model.CollisionOp;
import GeometricKingdom.mvc.model.adventurer.Sorcircler;
import GeometricKingdom.sounds.Sound;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Slightly modified Bomb class from Asteroids source code
 */
public class CircleBomb extends Weapon {
    private int bigRadius;
    private boolean bombActive;

    public CircleBomb(MouseEvent e, Sorcircler sorcircler){

        super();
        setTeam(Team.FRIEND);

        dmg = 1;

        bigRadius = sorcircler.getRadius() * sorcircler.getBombRadMod();
        bombActive = false;

        //defined the points on a cartesian grid
        dLengths = new double[628];
        dDegrees = new double[628];
        for (int i = 0; i < dLengths.length; i++) {
            dLengths[i] = 1;
            dDegrees[i] = 0.01 * i;
        }
        setColor(Color.BLUE);

        //expires after 40 frames
        setExpire(40);
        setRadius(bigRadius);

        setCenter(e.getPoint());


    }

    //implementing the expire functionality in the move method - added by Dmitriy
    public void move(){
        if (getExpire() == 0) {
            Cc.getInstance().getOpsList().enqueue(this, CollisionOp.Operation.REMOVE);
        } else {
            setExpire(getExpire() - 1);
        }

        if (getExpire() == 20) {
            update();
        }
    }

    public boolean isBombActive() {
        return bombActive;
    }

    public void setBombActive(boolean bombActive) {
        this.bombActive = bombActive;
    }

    public void update() {
        setFilled(true);
        bombActive = true;
        Cc.getInstance().getAdventurer().setUniqueResource(0);
        Sound.playSound("sorcircler_attack.wav");
    }
}

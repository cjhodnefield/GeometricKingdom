package GeometricKingdom.mvc.model.enemy;

import GeometricKingdom.mvc.controller.Game;
import GeometricKingdom.mvc.model.HPEntity;
import GeometricKingdom.mvc.model.weapon.*;
import GeometricKingdom.sounds.Sound;

import java.awt.*;

/**
 * Created by Jonathan on 12/3/2015.
 */
public class MissileCommand extends HPEntity {

    public MissileCommand() {

        maxHP = 5;
        HP = maxHP;
        dmg = 10;

        dLengths = new double[628];
        dDegrees = new double[628];
        for (int i = 0; i < dLengths.length; i++) {
            dLengths[i] = 1;
            dDegrees[i] = 0.01 * i;
        }

        setTeam(Team.FOE);
        setColor(Color.WHITE);
        setFilled(true);
        setBounceable(false);
        shootsAtAdventurer = true;
        setRadProportion(1/25.0);
        setRadius((int) (Game.DIM.width * getRadProportion()));
    }

    public void move() {

    }

    public void shootAdventurer() {
        Sound.playSound("missile_fired.wav");
        shootAdventurer(new Missile());
    }
}

package GeometricKingdom.mvc.model.weapon;

import GeometricKingdom.mvc.model.Sprite;

/**
 * Created by Jonathan on 12/2/2015.
 */
public class Weapon extends Sprite {

    protected int dmg;

    public Weapon() {
        super();
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }
}

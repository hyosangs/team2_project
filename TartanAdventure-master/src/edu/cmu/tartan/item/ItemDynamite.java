package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Explodable;
import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Valuable;
import edu.cmu.tartan.room.RoomObscured;

/**
 * This class for dynamite, which can be held and explode.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemDynamite extends Item implements Explodable, Holdable {
    // Indicates whether dynamite has been detonated
    private boolean exploded;

    /**
     * Constructor
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemDynamite(String s, String sd, String[] a) {
        super(s, sd, a);
        this.exploded = false;
        setValue(25);
    }

    /**
     * Explode the dynamite. Can be used to clear the way to a room
     *
     * @return true when the explosion occurs
     */
    @Override
    public Boolean explode() {
        if (!this.exploded) {
            if (this.relatedRoom != null && this.relatedRoom instanceof RoomObscured) {
                ((RoomObscured) this.relatedRoom).setObscured(false);
                System.out.println(((RoomObscured) this.relatedRoom).unobscureMessage());
            }
            this.exploded = true;
            this.detailDescription = "pile of smithereens";
        } else {
            System.out.println("The dynamite has already been detonated.");
        }
        return exploded;
    }

    public void setExplodeMessage(String s) {
        if (this.relatedRoom != null && this.relatedRoom instanceof RoomObscured) {
            ((RoomObscured) this.relatedRoom).setUnobscureMessage(s);
        }
    }

    /**
     * Has the dynamite been detonated?
     * @return true if the detonation occurred
     */
    public Boolean getExploded() {
        return exploded;
    }
}

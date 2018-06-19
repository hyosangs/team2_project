package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Luminous;

/**
 * This class for a desklamp, which can be held and produce light.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemDeskLight extends Item implements Holdable, Luminous {

    /**
     * Constructor for a desklight
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemDeskLight(String s, String sd, String[] a) {
        super(s, sd, a);
        setValue(10);
    }
}

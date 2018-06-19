package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Luminous;
import edu.cmu.tartan.properties.Valuable;

/**
 * This class for a keycard, which can be held and provide light.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemTorch extends Item implements Holdable, Luminous {

    public ItemTorch(String d, String sd, String[] a) {

        super(d, sd, a);
        setValue(10);
    }
}
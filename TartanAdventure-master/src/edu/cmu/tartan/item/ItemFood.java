package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Edible;
import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Meltable;
import edu.cmu.tartan.properties.Valuable;

/**
 * This class for food, which can be held and eaten.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemFood extends Item implements Edible, Holdable, Meltable {

    private Item hiddenItem = null;

    /**
     * Constructor for food item
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemFood(String s, String sd, String[] a) {

        super(s, sd, a);
        setValue(3);
    }

    /**
     * Eat the food
     */
    @Override
    public void eat() {
        System.out.println("Yummy");
    }

    /**
     * Set the item to reveal when the food is melted
     * @param item the item to melt
     */
    @Override
    public void setMeltItem(Item item) {
        hiddenItem = item;

    }

    /**
     * Reveal the hidden item
     * @return the hidden item
     */
    @Override
    public Item meltItem() {
        return hiddenItem;
    }
}

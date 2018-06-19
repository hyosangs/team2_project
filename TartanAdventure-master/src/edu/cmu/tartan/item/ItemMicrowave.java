package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.properties.Meltable;
import edu.cmu.tartan.properties.Startable;

/**
 * This class for a microwave, which can hold something and be started.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemMicrowave extends Item implements Hostable, Startable {

    private Item installedItem;

    /**
     * Constructor
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemMicrowave(String s, String sd, String[] a) {
        super(s, sd, a);
        this.installedItem = null;
        setValue(5);
    }

    /**
     * Start the microwave. If the installed item is meltable, then melt it
     * @return true if started
     */
    @Override
    public Boolean start() {

        for (int i = 0; i < 3; i++) {
            System.out.println("...");
            try {
                Thread.sleep(1000);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        System.out.println("Beep beep beep");

        // Only meltable things can be microwaved
        if (this.installedItem instanceof Meltable) {
            Item item = ((Meltable) this.installedItem).meltItem();
            System.out.println("You melted the " + this.installedItem.detailDescription() + ", and it revealed a " + item.detailDescription() + "!");
            this.installedItem = item;
            return true;
        }
        return false;
    }

    /**
     * Install an item in the microwave
     * @param i the item to install
     */
    public void install(Item i) {
        this.installedItem = i;
    }

    public boolean uninstall(Item i) {
        if (this.installedItem == null) {
            return false;
        } else if (this.installedItem == i) {
            this.installedItem = null;
            return true;
        } else {
            return false;
        }
    }

    public Item installedItem() {
        return this.installedItem;
    }
}

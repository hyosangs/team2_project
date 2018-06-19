package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Hostable;

/**
 * This class for a keycard reader, which can hold a keycard.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemKeycardReader extends Item implements Hostable {

    private Item installedItem;
    private String installMessage;

    /**
     * Constructor
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemKeycardReader(String s, String sd, String[] a) {
        super(s, sd, a);
        this.installMessage = null;
        setValue(25);
    }

    /**
     * Install a keycard
     * @param item the keycard to install in the reader
     */
    public void install(Item item) {
        if (!(item instanceof ItemKeycard)) return;
        this.installedItem = item;

        for (int i = 0; i < 3; i++) {
            System.out.println("...");
            try {
                Thread.sleep(1000);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        if (this.installMessage != null) {
            System.out.println(this.installMessage);
        }
        this.relatedItem.setVisible(true);
    }

    /**
     * Unstall the keycard
     * @param i the keycard to uninstall
     * @return true if uninstalled; false otherwise
     */
    @Override
    public boolean uninstall(Item i) {
        if (!(i instanceof ItemKeycard)) return false;

        if (this.installedItem == null) {
            return false;
        } else if (this.installedItem == i) {
            this.installedItem = null;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Fetch the installed keycard
     * @return
     */
    public Item installedItem() {
        return this.installedItem;
    }

    /**
     * Set the install message
     * @param s the message
     */
    public void setInstallMessage(String s) {
        this.installMessage = s;
    }


}

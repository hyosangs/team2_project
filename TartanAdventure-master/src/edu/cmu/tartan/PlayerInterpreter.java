package edu.cmu.tartan;

import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.item.Item;

import java.util.Arrays;

/**
 * This class attempts to interpret player input in a flexible way. It is experimental at best!
 */
public class PlayerInterpreter {

    /**
     * Interpret the input in terms of its action.
     * @param string input string.
     * @return an Action corresponding to the input.
     */
    public Action interpretString(String string) {

        if(string.equals("")) {
            return Action.ActionPass;
        }
        return action(string.toLowerCase().split(" "));
    }

    /**
     * Attempt to select the appropriate action for the given input string
     * @param string the description of what is to be done
     * @return
     * @throws ArrayIndexOutOfBoundsException
     */
    private Action action(String[] string) throws ArrayIndexOutOfBoundsException {

        if(string == null || string.length == 0) {
            return Action.ActionPass;
        }
        if(string[0].compareTo("go") == 0 || string[0].compareTo("travel") == 0 || string[0].compareTo("move") == 0){
            String[] command = Arrays.copyOfRange(string, 1, string.length);
            return action(command);
        }
        else {
            // input could be northeast, put cpu in vax, throw shovel, examine bin

            String s = string[0];
            Action action = null;
            out:{
                for( Action a : Action.values()) {
                    for(String alias : a.getAliases()) {
                        if(s.compareTo(alias) == 0) {
                            action = a;
                            break out;
                        }
                    }
                }
            }
            if(action == null) {
                return Action.ActionError;
            }
            switch(action.type()) {
                case TYPE_DIRECTIONAL:
                    return action;
                case TYPE_HASDIRECTOBJECT:
                    if(string.length > 1) {

                        String d = string[1];
                        Item item = Item.getInstance(d);
                        // item is the direct object of the action
                        action.setDirectObject(item);
                        return action;
                    }
                    else {
                        System.out.println("You must supply a direct object.");
                        return Action.ActionPass;
                    }
                case TYPE_HASINDIRECTOBJECT:

                    // test if it has indirect object
                    // "Take Diamond from Microwave"

                    if(string.length > 0) {

                        String d = string[1];
                        Item item = Item.getInstance(d);
                        // item is the direct object of the action
                        action.setDirectObject(item);

                        if(string.length > 2) {
                            String in = string[2];
                            if(in.equals("in") || in.equals("from")) {

                                if(string.length > 3) {
                                    String io = string[3];
                                    Item indob = Item.getInstance(io);
                                    action.setIndirectObject(indob);
                                    return action;
                                }
                                else {
                                    System.out.println("You must supply an indirect object.");
                                    return Action.ActionError;
                                }
                            }
                            else {
                                return Action.ActionPass;
                            }
                        }

                    }
                    else {
                        System.out.println("You must supply a direct object.");
                        return Action.ActionError;
                    }
                    break;
                case TYPE_HASNOOBJECT:
                    return action;
                case TYPE_UNKNOWN:
                    return Action.ActionError;
                default:
                    System.out.println("Unknown type");
                    break;
            }
        }

        return Action.ActionPass;
    }
}

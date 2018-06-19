package edu.cmu.tartan.games;

/**
 * An error to indicate that a game is improperly configured.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class InvalidGameException extends Exception {
    public InvalidGameException(String m) {
        super(m);
    }
}

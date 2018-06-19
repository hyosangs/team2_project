package edu.cmu.tartan;

import edu.cmu.tartan.games.InvalidGameException;

/**
 * The abstract class that all games must extend. Essentially all games must provide a configuration
 */
public abstract class GameConfiguration {

    /**
     * Configure the game
     * @param game the Game object that will manage exectuion
     * @throws InvalidGameException indicates configuration error
     * @see Game
     */
    public abstract void configure(Game game) throws InvalidGameException;

    /**
     * The name of this game
     */
    public String name;
};



package io.github.sdxqw.cmd.client;

/**
 * The CmdClient interface defines the methods that a client class must implement
 * in order to interact with a command-line-based game.
 */
public interface CmdClient {

    /**
     * Initializes the client, setting up any necessary resources.
     */
    void initialize();

    /**
     * Renders a single frame of the game's user interface.
     */
    void renderFrame();

    /**
     * Updates the game's state, based on user input or other events.
     */
    void updateGameState();

    /**
     * Cleans up any resources used by the client.
     */
    void cleanup();
}

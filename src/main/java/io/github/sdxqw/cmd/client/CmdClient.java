package io.github.sdxqw.cmd.client;

public interface CmdClient {
    void initialize();

    void renderFrame();

    void updateGameState();

    void cleanup();
}

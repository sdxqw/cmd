package io.github.sdxqw.cmd.core;

/**
 * The FPSCounter class provides a utility for measuring the frame rate of a game.
 */
public class FPSCounter {
    private long lastTime;
    private int fps;
    private int frames;

    public FPSCounter() {
        lastTime = System.nanoTime();
        fps = 0;
        frames = 0;
    }

    /**
     * Updates the FPS counter based on the current time and number of frames since the last update.
     */
    public void update() {
        long currentTime = System.nanoTime();
        double elapsedSeconds = (currentTime - lastTime) / 1e9;
        if (elapsedSeconds > 1.0) {
            fps = (int) (frames / elapsedSeconds);
            lastTime = currentTime;
            frames = 0;
        }
        frames++;
    }

    /**
     * Returns the current FPS value.
     * @return the current FPS value
     */
    public int getFPS() {
        return fps;
    }

    /**
     * Resets the FPS counter to its initial state.
     */
    public void clear() {
        lastTime = System.nanoTime();
        fps = 0;
        frames = 0;
    }
}

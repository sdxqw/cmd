package io.github.sdxqw.cmd.core;

public class FPSCounter {
    private long lastTime;
    private int fps;
    private int frames;

    public FPSCounter() {
        lastTime = System.nanoTime();
        fps = 0;
        frames = 0;
    }

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

    public int getFPS() {
        return fps;
    }

    public void clear() {
        lastTime = System.nanoTime();
        fps = 0;
        frames = 0;
    }
}


package io.github.sdxqw.cmd.ui;

import io.github.sdxqw.cmd.client.Window;

public class ButtonImage extends Button {
    public ButtonImage(float x, float y, float width, float height, Runnable onClick) {
        super(x, y, width, height, "", onClick);
    }

    public void render() {
        NanoVG.drawRect(this.x, Window.height - y - height, width, height, NanoVG.color(0.0f, 0.0f, 0.0f, hovered ? 0.6f : 0.4f));
    }

    @Override
    public void handleInput(long window) {
        super.handleInput(window);
    }
}

package io.github.sdxqw.cmd.ui;

import io.github.sdxqw.cmd.client.Window;
import io.github.sdxqw.cmd.utils.NanoVG;

public class ButtonImage extends Button {
    public Image image;
    public float sizeImage;

    public ButtonImage(String imageName, float x, float y, float width, float height, float sizeImage, Runnable onClick) {
        super(x, y, width, height, "", onClick);
        this.sizeImage = sizeImage;
        this.image = new Image(imageName);
    }

    public void render() {
        float centerX = this.x + width / 2;
        float centerY = Window.height - y - height / 2;
        float iconX = centerX - sizeImage / 2;
        float iconY = centerY - sizeImage / 2;

        NanoVG.drawRect(this.x, Window.height - y - height, width, height, NanoVG.color(0.0f, 0.0f, 0.0f, hovered ? 0.6f : 0.4f));
        NanoVG.drawImage(this.image, iconX, iconY, sizeImage, sizeImage, 255);
    }

    @Override
    public void handleInput(long window) {
        super.handleInput(window);
    }
}

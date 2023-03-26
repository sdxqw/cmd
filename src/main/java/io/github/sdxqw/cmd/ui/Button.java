package io.github.sdxqw.cmd.ui;

import io.github.sdxqw.cmd.client.Window;
import io.github.sdxqw.cmd.font.FontManager;
import org.lwjgl.glfw.GLFW;

public class Button {

    protected final float x;
    protected final float y;
    protected final float width;
    protected final float height;
    protected final String label;
    protected final Runnable onClick;

    protected boolean hovered = false;

    public Button(float x, float y, float width, float height, String label, Runnable onClick) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
        this.onClick = onClick;
    }

    public void render() {
        float fontHeight = 20.0f; // Change as needed
        float labelWidth = FontManager.measureTextWidth(label, FontManager.ROBOTO, fontHeight);
        float labelX = x + width / 2 - labelWidth / 2;
        float labelY = Window.height - y - height / 2 + fontHeight / 2;

        NanoVG.drawRect(x, Window.height - y - height, width, height, NanoVG.color(0.0f, 0.0f, 0.0f, hovered ? 0.6f : 0.4f));
        FontManager.drawRobotoText(label, labelX, labelY, fontHeight, NanoVG.color(1.0f, 1.0f, 1.0f, 1.0f));
    }

    public void handleInput(long window) {
        double[] mouseX = new double[1];
        double[] mouseY = new double[1];
        GLFW.glfwGetCursorPos(window, mouseX, mouseY);

        mouseY[0] = Window.height - mouseY[0];

        if (mouseX[0] >= x && mouseX[0] <= x + width && mouseY[0] >= y && mouseY[0] <= y + height) {
            if (!hovered) {
                hovered = true;
            }

            if (GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS) {
                onClick.run();
            }
        } else {
            hovered = false;
        }
    }

}

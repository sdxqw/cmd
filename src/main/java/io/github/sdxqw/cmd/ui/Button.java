package io.github.sdxqw.cmd.ui;

import io.github.sdxqw.cmd.client.Window;
import io.github.sdxqw.cmd.font.FontManager;
import io.github.sdxqw.cmd.utils.NanoVG;
import org.lwjgl.glfw.GLFW;

/**
 * Represents a clickable button on a UI screen.
 */
public class Button {

    protected final float x;
    protected final float y;
    protected final float width;
    protected final float height;
    protected final String label;
    protected final Runnable onClick;

    protected boolean hovered = false;


    /**
     * Constructor for the Button class.
     *
     * @param x         The x position of the button on the screen.
     * @param y         The y position of the button on the screen.
     * @param width     The width of the button.
     * @param height    The height of the button.
     * @param label     The text label to display on the button.
     * @param onClick   The action to perform when the button is clicked.
     */
    public Button(float x, float y, float width, float height, String label, Runnable onClick) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
        this.onClick = onClick;
    }

    /**
     * Renders the button on the screen.
     */
    public void render() {
        float fontHeight = FontManager.getTextHeight("roboto");
        float labelWidth = FontManager.measureTextWidth(label, FontManager.ROBOTO, fontHeight);
        float centerX = x + width / 2;
        float centerY = Window.height - y - height / 2;
        float labelX = centerX - labelWidth / 2;
        float labelY = centerY + fontHeight / 2;

        NanoVG.drawRoundedRectBorder(x, Window.height - y - height, width, height, 12, 3, NanoVG.color(0.0f, 0.0f, 0.0f, hovered ? 0.6f : 0.4f));

        NanoVG.drawRoundedRect(x, Window.height - y - height, width, height, 12, NanoVG.color(0.0f, 0.0f, 0.0f, hovered ? 0.6f : 0.4f));
        FontManager.drawRobotoText(label, labelX, labelY, fontHeight, NanoVG.color(1.0f, 1.0f, 1.0f, 1.0f));
    }

    /**
     * Handles input for the button.
     *
     * @param window    The GLFW window to get input from.
     */
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

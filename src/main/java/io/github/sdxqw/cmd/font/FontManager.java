package io.github.sdxqw.cmd.font;

import org.lwjgl.nanovg.NVGColor;

/**
 * The FontManager class is responsible for managing fonts used in the application.
 * It provides methods to load fonts, draw text using the loaded fonts, and measure the width of text.
 */
public class FontManager {

    private static final Font font = new Font();

    public static String ROBOTO = "roboto";

    /**
     * Initializes the Roboto font by loading it from a file.
     * This method should be called once at the start of the application.
     */
    public static void loadFonts() {
        font.init(ROBOTO, "Roboto-Regular");
    }

    /**
     * Draws text using the Roboto font.
     * @param text the text to draw
     * @param x the x-coordinate of the text position
     * @param y the y-coordinate of the text position
     * @param size the size of the text
     * @param color the color of the text
     */
    public static void drawRobotoText(String text, float x, float y, float size, NVGColor color) {
        font.drawText(text, x, y, ROBOTO, size, color);
    }

    /**
     * Measures the width of text using a specified font and font size.
     * @param text the text to measure
     * @param name the name of the font to use
     * @param fontSize the size of the font to use
     * @return the width of the text
     */
    public static float measureTextWidth(String text, String name, float fontSize) {
        return font.measureTextWidth(text, name, fontSize);
    }
}

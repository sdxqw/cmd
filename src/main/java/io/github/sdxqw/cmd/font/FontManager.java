package io.github.sdxqw.cmd.font;

import org.lwjgl.nanovg.NVGColor;

public class FontManager {

    private static final Font font = new Font();

    public static String ROBOTO = "roboto";

    public static void loadFonts() {
        font.init(ROBOTO, "Roboto-Regular");
    }

    public static void drawRobotoText(String text, float x, float y, float size, NVGColor color) {
        font.drawText(text, x, y, ROBOTO, size, color);
    }

    public static float measureTextWidth(String text, String name, float fontSize) {
        return font.measureTextWidth(text, name, fontSize);
    }
}

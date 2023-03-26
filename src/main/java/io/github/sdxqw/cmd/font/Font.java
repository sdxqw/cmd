package io.github.sdxqw.cmd.font;

import io.github.sdxqw.cmd.client.Window;
import io.github.sdxqw.cmd.utils.Utils;
import lombok.SneakyThrows;
import org.lwjgl.BufferUtils;
import org.lwjgl.nanovg.NVGColor;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.file.Paths;
import java.util.Objects;

import static org.lwjgl.nanovg.NanoVG.*;

/**
 * The Font class provides functionality for loading and drawing fonts using NanoVG.
 */
public class Font {

    /**
     * Initializes a font with the given name and font file.
     *
     * @param name     the name of the font
     * @param fontName the name of the font file
     */
    @SneakyThrows
    public void init(String name, String fontName) {
        ByteBuffer fontBuffer = Utils.readFile(Paths.get(Objects.requireNonNull(Utils.class.getResource("/fonts/" + fontName + ".ttf")).toURI()));
        nvgCreateFontMem(Window.nvg, name, fontBuffer, 0);
    }

    /**
     * Draws the specified text with the given font, size, and color at the specified position.
     *
     * @param text     the text to draw
     * @param x        the x-coordinate of the starting position
     * @param y        the y-coordinate of the starting position
     * @param font     the name of the font to use
     * @param fontSize the size of the font to use
     * @param color    the color of the text to draw
     */
    public void drawText(String text, float x, float y, String font, float fontSize, NVGColor color) {
        nvgFontFace(Window.nvg, font);
        nvgFontSize(Window.nvg, fontSize);
        nvgFillColor(Window.nvg, color);
        nvgText(Window.nvg, x, y, text);
    }

    /**
     * Returns the width of the specified text when drawn with the given font and size.
     *
     * @param text     the text to measure
     * @param font     the name of the font to use
     * @param fontSize the size of the font to use
     * @return the width of the text in pixels
     */
    public float measureTextWidth(String text, String font, float fontSize) {
        nvgFontFace(Window.nvg, font);
        nvgFontSize(Window.nvg, fontSize);

        return nvgTextBounds(Window.nvg, 0f, 0f, text, (FloatBuffer) null);
    }

    /**
     * Returns the height of the specified text when drawn with the given font and size.
     *
     * @param font the name of the font to use
     * @return the height of the font in pixels
     */
    public float getTextHeight(String font) {
        nvgFontFace(Window.nvg, font);
        FloatBuffer lineh = BufferUtils.createFloatBuffer(1);
        nvgTextMetrics(Window.nvg, null, null, lineh);
        return lineh.get(0);
    }


}

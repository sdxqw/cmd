package io.github.sdxqw.cmd.utils;

import io.github.sdxqw.cmd.client.Window;
import io.github.sdxqw.cmd.ui.Image;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;

import static org.lwjgl.nanovg.NanoVG.*;

/**
 * The NanoVG class provides static utility methods for rendering graphics using the NanoVG library.
 */
public class NanoVG {

    /**
     * Returns an NVGColor object with the specified RGBA values.
     *
     * @param r The red value, ranging from 0.0 to 1.0.
     * @param g The green value, ranging from 0.0 to 1.0.
     * @param b The blue value, ranging from 0.0 to 1.0.
     * @param a The alpha value, ranging from 0.0 to 1.0.
     * @return The NVGColor object with the specified RGBA values.
     */
    public static NVGColor color(float r, float g, float b, float a) {
        return NVGColor.calloc().a(a).r(r).g(g).b(b);
    }

    /**
     * Draws a rectangle with the specified position, size, and color.
     *
     * @param x      The x-coordinate of the rectangle's top-left corner.
     * @param y      The y-coordinate of the rectangle's top-left corner.
     * @param width  The width of the rectangle.
     * @param height The height of the rectangle.
     * @param color  The color to fill the rectangle with.
     */
    public static void drawRect(float x, float y, float width, float height, NVGColor color) {
        nvgBeginPath(Window.nvg);
        nvgRect(Window.nvg, x, y, width, height);
        nvgFillColor(Window.nvg, color);
        nvgFill(Window.nvg);
    }

    /**
     * Draws a rounded rectangle with the specified position, size, and color.
     *
     * @param x          The x-coordinate of the rectangle's top-left corner.
     * @param y          The y-coordinate of the rectangle's top-left corner.
     * @param width      The width of the rectangle.
     * @param height     The height of the rectangle.
     * @param radius     The radius of the rectangle's corners.
     * @param color      The color to fill the rectangle with.
     */
    public static void drawRoundedRect(float x, float y, float width, float height, float radius, NVGColor color) {
        nvgBeginPath(Window.nvg);
        nvgRoundedRect(Window.nvg, x, y, width, height, radius);
        nvgFillColor(Window.nvg, color);
        nvgFill(Window.nvg);
    }

    /**
     * Draws the border of a rounded rectangle with the specified position, size, color, radius, and thickness.
     *
     * @param x        The x-coordinate of the rectangle's top-left corner.
     * @param y        The y-coordinate of the rectangle's top-left corner.
     * @param width    The width of the rectangle.
     * @param height   The height of the rectangle.
     * @param radius   The radius of the rounded corners.
     * @param thickness The thickness of the border.
     * @param color    The color to fill the border with.
     */
    public static void drawRoundedRectBorder(float x, float y, float width, float height, float radius, float thickness, NVGColor color) {
        nvgBeginPath(Window.nvg);
        nvgRoundedRect(Window.nvg, x, y, width, height, radius);
        nvgStrokeWidth(Window.nvg, thickness);
        nvgStrokeColor(Window.nvg, color);
        nvgStroke(Window.nvg);
    }


    /**
     * Draws an image onto the screen using NanoVG.
     *
     * @param image  the Image to be drawn
     * @param x      the x coordinate of the top-left corner of the image
     * @param y      the y coordinate of the top-left corner of the image
     * @param width  the width of the image to be drawn
     * @param height the height of the image to be drawn
     * @param alpha  the alpha value of the image (between 0 and 255)
     */
    public static void drawImage(Image image, float x, float y, float width, float height, int alpha) {
        nvgBeginPath(Window.nvg);
        NVGPaint paint = nvgImagePattern(Window.nvg, x, y, width, height, 0.0F, image.getImageId(), alpha / 255.0F, NVGPaint.create());
        nvgRect(Window.nvg, x, y, width, height);
        nvgFillPaint(Window.nvg, paint);
        nvgFill(Window.nvg);
    }
}


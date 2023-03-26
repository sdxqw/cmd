package io.github.sdxqw.cmd.ui;

import io.github.sdxqw.cmd.client.Window;
import lombok.SneakyThrows;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.concurrent.Future;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;

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
     * Renders an image from a file and returns the image handle.
     *
     * @param image  The filename of the image.
     * @param x      The x-coordinate of the image's top-left corner.
     * @param y      The y-coordinate of the image's top-left corner.
     * @param width  The width of the image.
     * @param height The height of the image.
     * @return The image handle.
     * @throws Exception If there is an error rendering the image.
     */
    @SneakyThrows
    public static int renderImage(String image, float x, float y, float width, float height) {
        Path imagePath = Paths.get(Objects.requireNonNull(NanoVG.class.getResource(image)).toURI());
        int imageHandle;

        try (AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(imagePath, StandardOpenOption.READ)) {
            ByteBuffer imageBuffer = ByteBuffer.allocateDirect((int) fileChannel.size());

            Future<Integer> readResult = fileChannel.read(imageBuffer, 0);

            while (true) {
                if (readResult.isDone()) break;
            }

            readResult.get(); // Check for errors during the read operation
            imageBuffer.flip();

            imageHandle = nvgCreateImageMem(Window.nvg, NVG_IMAGE_GENERATE_MIPMAPS, imageBuffer);

            if (imageHandle == 0) {
                throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
            }

            nvgBeginPath(Window.nvg);
            NVGPaint paint = nvgImagePattern(Window.nvg, x, y, width, height, 0.0f, imageHandle, 1.0f, NVGPaint.create());
            nvgRect(Window.nvg, x, y, width, height);
            nvgFillPaint(Window.nvg, paint);
            nvgFill(Window.nvg);
        }

        return imageHandle;
    }

    /**
     * Deletes an image from memory.
     *
     * @param imageHandle The image handle.
     */
    public static void cleanUpImage(int imageHandle) {
        nvgDeleteImage(Window.nvg, imageHandle);
    }
}


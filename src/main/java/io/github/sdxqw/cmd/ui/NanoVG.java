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

public class NanoVG {

    public static NVGColor color(float r, float g, float b, float a) {
        return NVGColor.calloc().a(a).r(r).g(g).b(b);
    }

    public static void drawRect(float x, float y, float width, float height, NVGColor color) {
        nvgBeginPath(Window.nvg);
        nvgRect(Window.nvg, x, y, width, height);
        nvgFillColor(Window.nvg, color);
        nvgFill(Window.nvg);
    }

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

    public static void cleanUpImage(int imageHandle) {
        nvgDeleteImage(Window.nvg, imageHandle);
    }
}


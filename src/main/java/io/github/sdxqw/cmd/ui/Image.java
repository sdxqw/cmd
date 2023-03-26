package io.github.sdxqw.cmd.ui;

import io.github.sdxqw.cmd.client.Window;
import io.github.sdxqw.cmd.utils.Utils;
import lombok.Getter;
import lombok.SneakyThrows;

import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.Objects;

import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Represents an image that can be rendered on the screen using NanoVG.
 * Images are loaded from a file path, and can be cleaned up when no longer needed.
 */
public class Image {
    @Getter
    public int imageId;

    /**
     * Creates a new image from the given file name.
     *
     * @param name the name of the image file, without the ".png" extension
     */
    @SneakyThrows
    public Image(String name) {
        ByteBuffer imageBuffer = Utils.readFile(Paths.get(Objects.requireNonNull(Utils.class.getResource("/image/" + name + ".png")).toURI()));
        this.imageId = nvgCreateImageMem(Window.nvg, NVG_IMAGE_GENERATE_MIPMAPS, imageBuffer);
    }

    /**
     * Cleans up the resources used by this image.
     */
    public void cleanup() {
        nvgDeleteImage(Window.nvg, imageId);
    }
}

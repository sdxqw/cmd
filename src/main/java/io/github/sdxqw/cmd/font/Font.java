package io.github.sdxqw.cmd.font;

import io.github.sdxqw.cmd.client.Window;
import lombok.SneakyThrows;
import org.lwjgl.nanovg.NVGColor;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import static org.lwjgl.nanovg.NanoVG.*;

public class Font {

    @SneakyThrows
    public void init(String name, String fontName) {
        Path fontPath = Paths.get(Objects.requireNonNull(getClass().getResource("/fonts/" + fontName + ".ttf")).toURI());
        try (FileChannel fc = FileChannel.open(fontPath, StandardOpenOption.READ)) {
            ByteBuffer fontBuffer = ByteBuffer.allocateDirect((int) fc.size());
            fc.read(fontBuffer);
            fontBuffer.flip();
            nvgCreateFontMem(Window.nvg, name, fontBuffer, 0);
        } finally {
            Files.delete(fontPath);
        }
    }

    public void drawText(String text, float x, float y, String font, float fontSize, NVGColor color) {
        nvgFontFace(Window.nvg, font);
        nvgFontSize(Window.nvg, fontSize);
        nvgFillColor(Window.nvg, color);
        nvgText(Window.nvg, x, y, text);
    }

    public float measureTextWidth(String text, String font, float fontSize) {
        nvgFontFace(Window.nvg, font);
        nvgFontSize(Window.nvg, fontSize);
        return nvgTextBounds(Window.nvg, 0f, 0f, text, (FloatBuffer) null);
    }

}
package io.github.sdxqw.cmd.core;

import io.github.sdxqw.cmd.client.CmdClient;
import io.github.sdxqw.cmd.client.Window;
import io.github.sdxqw.cmd.font.FontManager;
import io.github.sdxqw.cmd.ui.Button;
import io.github.sdxqw.cmd.ui.NanoVG;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CmdCore implements CmdClient {
    @Getter
    private static final CmdCore instance = new CmdCore();
    private final List<Button> buttons;
    private final FPSCounter fps;

    int image = 0;

    private CmdCore() {
        fps = new FPSCounter();
        buttons = new ArrayList<>();
    }

    public void initialize() {
        FontManager.loadFonts();
        buttons.add(new Button(90, 90, 100, 100, "Hello, World!", () -> System.out.println("Hello, World!")));
    }

    public void renderFrame() {
        //image = NanoVG.renderImage("/image/ig.png", 90, 90, 100, 100);

        buttons.forEach(Button::render);

        FontManager.drawRobotoText("FPS: " + fps.getFPS(), 30, 30, 20, NanoVG.color(1.0f, 1.0f, 1.0f, 1.0f));
    }

    public void updateGameState() {
        fps.update();
        buttons.forEach((e) -> e.handleInput(Window.window));
    }

    public void cleanup() {
        buttons.clear();
        fps.clear();
        NanoVG.cleanUpImage(image);
    }
}

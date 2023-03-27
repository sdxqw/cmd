package io.github.sdxqw.cmd.core;

import io.github.sdxqw.cmd.client.CmdClient;
import io.github.sdxqw.cmd.client.Window;
import io.github.sdxqw.cmd.core.commands.Clear;
import io.github.sdxqw.cmd.core.commands.Echo;
import io.github.sdxqw.cmd.font.FontManager;
import io.github.sdxqw.cmd.ui.Button;
import io.github.sdxqw.cmd.ui.ButtonImage;
import io.github.sdxqw.cmd.ui.TextBox;
import io.github.sdxqw.cmd.ui.TextInput;
import io.github.sdxqw.cmd.utils.NanoVG;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The CmdCore class implements the CmdClient interface and provides the core functionality
 * for a command-line-based game.
 */
public class CmdCore implements CmdClient {

    @Getter
    private static final CmdCore instance = new CmdCore();

    private final List<Button> buttons;
    private final FPSCounter fps;

    private final TextInput field;
    private final CmdTypes types;
    private final TextBox textBox;

    private CmdCore() {
        fps = new FPSCounter();
        buttons = new ArrayList<>();
        field = new TextInput(400, 200, 320, 60, 20);
        types = new CmdTypes(new Echo(), new Clear());
        textBox = new TextBox(field.commandHistory, 400, 240, 320, 120);
    }

    /**
     * Initializes the game, loading fonts and adding a button to the UI.
     */
    public void initialize() {
        FontManager.loadFonts();
        buttons.add(new Button(90, 90, 100, 100, "Hello, World!", () -> System.out.println("Hello, World!")));
        buttons.add(new ButtonImage("ig", 90, 200, 100, 100, 32, () -> System.exit(0)));
    }

    /**
     * Renders a single frame of the game's UI, including buttons and FPS counter.
     */
    public void renderFrame() {
        buttons.forEach((e) -> {
            if (e instanceof ButtonImage) e.render();
            else e.render();
        });

        field.render();
        textBox.render();

        FontManager.drawRobotoText("FPS: " + fps.getFPS(), 30, 30, 20, NanoVG.color(1.0f, 1.0f, 1.0f, 1.0f));
    }

    /**
     * Updates the game's state, including the FPS counter and button input handling.
     */
    public void updateGameState() {
        fps.update();
        buttons.forEach((e) -> {
            if (e instanceof ButtonImage) e.handleInput(Window.window);
            else e.handleInput(Window.window);
        });

        field.registerCallbacks(Window.window);
        field.runCommands(types.getCmd(), field.getLastText());
    }

    /**
     * Cleans up any resources used by the game, including buttons and images.
     */
    public void cleanup() {
        buttons.forEach((e) -> {
            if (e instanceof ButtonImage) ((ButtonImage) e).image.cleanup();
        });
        buttons.clear();
        fps.clear();
    }
}

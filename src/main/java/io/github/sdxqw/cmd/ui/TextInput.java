package io.github.sdxqw.cmd.ui;

import io.github.sdxqw.cmd.core.Command;
import io.github.sdxqw.cmd.font.FontManager;
import io.github.sdxqw.cmd.utils.NanoVG;
import lombok.Getter;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class TextInput {
    private final long cursorBlinkTime = 500L;
    public float x;
    public float y;
    public float width;
    public float height;
    public float fontSize;
    public String text = "";
    public String lastText;
    public List<String> commandHistory = new ArrayList<>();
    private boolean showCursor = false;
    private int cursorPosition;
    private boolean commandHasBeenSent = false;

    public TextInput(float x, float y, float width, float height, float fontSize) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fontSize = fontSize;
    }

    public void render() {
        // Draw rounded rectangle
        NanoVG.drawRoundedRect(x, y, width, height, 12, NanoVG.color(.5f, .5f, .5f, .5f));

        float fontHeight = FontManager.getTextHeight("roboto");
        float centerY = y + height / 2;
        float labelY = centerY + fontHeight / 2;

        float textX = x + 10;

        // Only draw the portion of the text that fits within the box
        String visibleText = getVisibleText();
        FontManager.drawRobotoText(visibleText, textX, labelY, fontSize, NanoVG.color(1f, 1f, 1f, 1f));

        if (showCursor && (System.currentTimeMillis() - cursorBlinkTime) % 1000 < 500) {
            float cursorX = textX + FontManager.measureTextWidth(text.substring(0, cursorPosition), "roboto", fontSize);
            if (cursorPosition < 0) cursorPosition = 0;

            float cursorHeight = fontSize / 10f;
            float cursorBottomY = labelY - fontHeight / 2 - 8;
            float cursorTopY = labelY + fontHeight / 2 - 6;

            // Only draw cursor if it's within the boundaries of the text field
            if (cursorX >= textX && cursorX <= textX + width - 20) {
                NanoVG.drawLine(cursorX, cursorBottomY, cursorX, cursorTopY, cursorHeight, NanoVG.color(1f, 1f, 1f, 1f));
            }
        }
    }

    public void runCommands(List<Command> commands, String commandToCheck) {
        boolean found = false;
        if (isCommandHasBeenSent()) {
            String[] commandTokens = commandToCheck.split("\\s+"); // split command into tokens
            for (Command command : commands) {
                if (command.matches(Arrays.asList(commandTokens))) { // check for matches using the tokens
                    command.execute(commandHistory, Arrays.asList(commandTokens)); // pass tokens to command
                    found = true;
                    break;
                }
                // check aliases for the command
                for (String alias : command.getAliases()) {
                    if (alias.equals(commandTokens[0])) {
                        command.execute(commandHistory, Arrays.asList(commandTokens)); // pass tokens to command
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
            if (!found) {
                commandHistory.add("Command not found");
            }
            commandHasBeenSent = false;
        }
    }

    public void onMouse(long window, int button, int action, int mods) {
        double[] mouseX = new double[1];
        double[] mouseY = new double[1];
        GLFW.glfwGetCursorPos(window, mouseX, mouseY);
        boolean mouseWithinBounds = mouseX[0] >= x && mouseX[0] <= x + width && mouseY[0] >= y && mouseY[0] <= y + height;

        if (action == GLFW.GLFW_PRESS && mouseWithinBounds) {
            showCursor = true;

            // Calculate cursor position based on mouse click
            float textX = x + 10;
            String visibleText = getVisibleText();
            int visibleChars = Math.min(visibleText.length(), (int) ((mouseX[0] - textX) / (fontSize / 2)));
            cursorPosition = Math.min(visibleChars, text.length());
        } else if (action == GLFW.GLFW_PRESS) {
            showCursor = false;
        }
    }

    private String getVisibleText() {
        float textWidth = FontManager.measureTextWidth(text, "roboto", fontSize);
        if (textWidth <= width - 20) {
            return text;
        }
        int visibleChars = text.length() - 1;
        float ellipsisWidth = FontManager.measureTextWidth("...", "roboto", fontSize);
        while (visibleChars > 0 && FontManager.measureTextWidth(text.substring(0, visibleChars), "roboto", fontSize) + ellipsisWidth > width - 20) {
            visibleChars--;
        }
        return text.substring(0, visibleChars) + "...";
    }

    public void moveCursorLeft() {
        if (cursorPosition > 0) {
            cursorPosition--;
        }
    }

    public void moveCursorRight() {
        if (cursorPosition < text.length()) {
            cursorPosition++;
        }
    }

    private void onKey(long window, int key, int scancode, int action, int mods) {
        boolean shiftPressed = (mods & GLFW.GLFW_MOD_SHIFT) != 0;

        // Handle key presses
        if (action == GLFW.GLFW_PRESS) {
            if (key == GLFW.GLFW_KEY_BACKSPACE && cursorPosition > 0) {
                text = text.substring(0, cursorPosition - 1) + text.substring(cursorPosition);
                cursorPosition--;
            } else if (key == GLFW.GLFW_KEY_DELETE && cursorPosition < text.length()) {
                text = text.substring(0, cursorPosition) + text.substring(cursorPosition + 1);
            } else if (key == GLFW.GLFW_KEY_ENTER) {
                this.commandHasBeenSent = true;
                commandHistory.add(text);
                lastText = text;
                clearTextInput();
            } else if (key == GLFW.GLFW_KEY_SPACE) {
                text = text.substring(0, cursorPosition) + " " + text.substring(cursorPosition);
                cursorPosition++;
            } else if (key == GLFW.GLFW_KEY_LEFT) {
                moveCursorLeft();
            } else if (key == GLFW.GLFW_KEY_RIGHT) {
                moveCursorRight();
            } else {
                String keyName = GLFW.glfwGetKeyName(key, scancode);
                if (keyName != null && !keyName.isEmpty()) {
                    char c = keyName.charAt(0);
                    if (shiftPressed) {
                        c = Character.toUpperCase(c);
                    }
                    text = text.substring(0, cursorPosition) + c + text.substring(cursorPosition);
                    cursorPosition++;
                }
            }
        }
    }

    public void clearTextInput() {
        this.text = "";
        cursorPosition = 0;
    }

    public void registerCallbacks(long window) {
        GLFW.glfwSetKeyCallback(window, this::onKey);
        GLFW.glfwSetMouseButtonCallback(window, this::onMouse);
    }
}

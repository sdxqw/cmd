package io.github.sdxqw.cmd.ui;

import io.github.sdxqw.cmd.client.Window;
import io.github.sdxqw.cmd.font.FontManager;
import io.github.sdxqw.cmd.utils.NanoVG;

import java.util.List;

public class TextBox {

    private final List<String> text;
    private final float x;
    private final float y;
    private final float width;
    private final float height;


    public TextBox(List<String> text, float x, float y, float width, float height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render() {
        float padding = 8;
        float roundedRectY = Window.height - y - height - padding;
        float roundedRectHeight = height + padding * 2;

        NanoVG.drawRoundedRect(x, roundedRectY, width, roundedRectHeight, 3, NanoVG.color(.5f, .5f, .5f, .5f));

        // Calculate the position for the text
        float textX = x + 15;
        float textY = Window.height - y - height + padding + FontManager.getTextHeight("roboto");

        for (String line : text) {
            textY += FontManager.getTextHeight("roboto") + padding;
            FontManager.drawRobotoText(line, textX, textY, 20, NanoVG.color(1.0f, 1.0f, 1.0f, 1.0f));
        }
    }

}

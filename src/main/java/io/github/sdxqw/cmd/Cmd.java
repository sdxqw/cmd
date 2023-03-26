package io.github.sdxqw.cmd;

import io.github.sdxqw.cmd.client.CmdClient;
import io.github.sdxqw.cmd.core.CmdCore;
import io.github.sdxqw.logger.Logger;
import lombok.Getter;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL;

import static io.github.sdxqw.cmd.client.Window.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVGGL3.nvgDelete;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Cmd class represents the main entry point for the CMD game.
 * It initializes the GLFW window, OpenGL context and NanoVG context.
 * It also updates and renders the game state continuously until the window is closed.
 */
@Getter
public class Cmd implements CmdClient {

    public Cmd() {
    }

    /**
     * Starts the game loop.
     */
    public void start() {
        initialize();
        while (!glfwWindowShouldClose(window)) {
            renderFrame();
            updateGameState();
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
        cleanup();
    }

    /**
     * Initializes GLFW, creates the window, creates the OpenGL context and NanoVG context.
     */
    public void initialize() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new RuntimeException("Unable to initialize GLFW");
        }

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        window = glfwCreateWindow(width, height, "CMD", 0, 0);
        if (window == NULL) {
            glfwTerminate();
            throw new IllegalArgumentException("Failed to create the GLFW window");
        }

        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);

        glfwMakeContextCurrent(window);
        glfwShowWindow(window);

        GL.createCapabilities();

        nvg = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS | NanoVGGL3.NVG_STENCIL_STROKES);
        if (nvg == NULL) {
            throw new IllegalStateException("Failed to create NVG context");
        }

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0, width, 0.0, height, 1.0, -1.0);
        glMatrixMode(GL_MODELVIEW);

        Logger.info("OpenGL version: " + glGetString(GL_VERSION));
        Logger.info("GLFW version: " + glfwGetVersionString());
        Logger.info("Window: " + window);
        Logger.info("NanoVG: " + nvg);

        CmdCore.getInstance().initialize();
    }

    /**
     * Clears the screen, begins the NanoVG frame, renders the game frame and ends the NanoVG frame.
     */
    public void renderFrame() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0.2f, 0.2f, 0.2f, 0.2f);
        nvgBeginFrame(nvg, width, height, 1);
        CmdCore.getInstance().renderFrame();
        nvgEndFrame(nvg);
    }

    /**
     * Updates the game state by calling the updateGameState() method from the CmdCore singleton instance.
     * This method is called once per frame in the main game loop.
     */
    public void updateGameState() {
        CmdCore.getInstance().updateGameState();
    }

    /**
     * Cleans up resources used by the game, including the GLFW window and NanoVG context, by calling the cleanup()
     * This method is called when the game is exiting.
     */
    public void cleanup() {
        CmdCore.getInstance().cleanup();

        if (window != NULL) glfwDestroyWindow(window);

        if (nvg != NULL) nvgDelete(nvg);

        glfwTerminate();
    }
}

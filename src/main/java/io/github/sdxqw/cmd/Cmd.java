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

@Getter
public class Cmd implements CmdClient {

    public Cmd() {
    }

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

    public void renderFrame() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0.2f, 0.2f, 0.2f, 0.2f);
        nvgBeginFrame(nvg, width, height, 1);
        CmdCore.getInstance().renderFrame();
        nvgEndFrame(nvg);
    }

    public void updateGameState() {
        CmdCore.getInstance().updateGameState();
    }

    public void cleanup() {
        CmdCore.getInstance().cleanup();

        if (window != NULL)
            glfwDestroyWindow(window);

        if (nvg != NULL)
            nvgDelete(nvg);

        glfwTerminate();
    }
}

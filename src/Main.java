
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.*;
import org.lwjgl.system.*;
import org.lwjgl.bgfx.*;

import java.nio.ByteBuffer;

import static org.lwjgl.bgfx.BGFX.*;
import static org.lwjgl.bgfx.BGFXPlatform.bgfx_set_platform_data;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {
    public static void main(String[] args) {

        int width = 1280;
        int height = 720;

        //GLFWErrorCallback.createThrow().set();
        if(!glfwInit()) {
            throw new RuntimeException("Error initializing GLFW");
        }

        //if(Platform.get() == Platform.MACOSX) {
        //    glfwWindowHint(GLFW_COCOA_RETINA_FRAMEBUFFER, GLFW_FALSE);
        //}

        glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);

        long window = glfwCreateWindow(width, height, "Mech Battle Engine", 0, 0);
        if(window == NULL) {
            throw new RuntimeException("Error creating GLFW window");
        }

        glfwSetKeyCallback(window, (windowHnd, key, scancode, action, mods) -> {
            if (action != GLFW_RELEASE) {
                return;
            }

            switch (key) {
                case GLFW_KEY_ESCAPE:
                    glfwSetWindowShouldClose(windowHnd, true);
                    break;
            }
        });

        try (MemoryStack stack = stackPush()) {
            BGFXPlatformData platformData = BGFXPlatformData.callocStack(stack);

            switch (Platform.get()) {
                case LINUX:
                    platformData.ndt(GLFWNativeX11.glfwGetX11Display());
                    platformData.nwh(GLFWNativeX11.glfwGetX11Window(window));
                    break;
                case MACOSX:
                    platformData.nwh(GLFWNativeCocoa.glfwGetCocoaWindow(window));
                    break;
                case WINDOWS:
                    platformData.nwh(GLFWNativeWin32.glfwGetWin32Window(window));
                    break;
            }

            bgfx_set_platform_data(platformData);
        }

        if(!bgfx_init(BGFX_RENDERER_TYPE_COUNT, BGFX_PCI_ID_NONE, 0, null, null)) {
            throw new RuntimeException("Error initializing bgfx renderer");
        }

        System.out.println("bgfx renderer: " + bgfx_get_renderer_name(bgfx_get_renderer_type()));

        bgfx_reset(width, height, BGFX_RESET_VSYNC);

        bgfx_set_debug(BGFX_DEBUG_TEXT);

        bgfx_set_view_clear(0, BGFX_CLEAR_COLOR | BGFX_CLEAR_DEPTH, 0x474747ff, 1.0f, 0);

        //ByteBuffer logo = Logo.createLogo();

        while(!glfwWindowShouldClose(window)) {
            glfwPollEvents();

            bgfx_set_view_rect(0, 0, 0, width, height);

            bgfx_touch((byte)0);


            bgfx_frame(false);

        }

        bgfx_shutdown();

        glfwDestroyWindow(window);
        glfwTerminate();
    }
}
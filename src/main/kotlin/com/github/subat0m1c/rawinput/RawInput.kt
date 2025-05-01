package com.github.subat0m1c.rawinput

import com.github.subat0m1c.rawinput.RawInputMain.Companion.mc
import net.java.games.input.Controller
import net.java.games.input.ControllerEnvironment
import net.java.games.input.Mouse
import net.minecraft.util.ChatComponentText

object RawInput {
    var mouse: Mouse? = null

    /**
     * Controllers are only grabbed once on launch, so we need to force a reset in the event of mouse connections changing during runtime.
     * using reflection for this is really slow i think but i dont wanna make a mixin for this garbage
     */
    fun resetControllers() = runCatching {
        ControllerEnvironment::class.java.getDeclaredField("defaultEnvironment").apply {
            isAccessible = true
            set(null, Class.forName("net.java.games.input.DefaultControllerEnvironment").getDeclaredConstructor().apply {
                isAccessible = true
            }.newInstance())
        }
    }

    fun findMouse() {
        ControllerEnvironment.getDefaultEnvironment().controllers.forEach { controller ->
            if (controller.type == Controller.Type.MOUSE) {
                controller.poll()
                val mouseController = controller as Mouse
                if (mouseController.x.pollData != 0f || mouseController.y.pollData != 0f) {
                    mouse = mouseController
                    mc.thePlayer?.addChatMessage(ChatComponentText("Mouse found: ${mouseController.name}"))
                        ?: println("Mouse found: ${mouseController.name}")
                    return
                }
            }
        }
    }
}
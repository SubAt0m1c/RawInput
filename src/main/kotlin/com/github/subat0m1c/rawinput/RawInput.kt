package com.github.subat0m1c.rawinput

import com.github.subat0m1c.rawinput.RawInputMain.Companion.mc
import net.java.games.input.Controller
import net.java.games.input.ControllerEnvironment
import net.java.games.input.Mouse
import net.minecraft.util.ChatComponentText

object RawInput {
    var mouse: Mouse? = null

    fun findMouse() {
        for (controller in ControllerEnvironment.getDefaultEnvironment().controllers) {
            if (controller.type == Controller.Type.MOUSE) {
                controller.poll()
                val mouseController = controller as Mouse
                if (mouseController.x.pollData != 0f || mouseController.y.pollData != 0f) {
                    mouse = mouseController
                    mc.thePlayer?.addChatMessage(ChatComponentText("Mouse found: ${mouseController.name}"))
                        ?: println("Mouse found: ${mouseController.name}")
                    break
                }
            }
        }
    }
}
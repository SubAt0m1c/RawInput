package com.github.subat0m1c.rawinput

import com.github.subat0m1c.rawinput.RawInputMain.Companion.mc
import com.github.subat0m1c.rawinput.RawInputMain.Companion.scope
import com.github.subat0m1c.rawinput.commands.ConfigCommand.Companion.waitTime
import com.github.subat0m1c.rawinput.commands.ToggleCommand.Companion.enabled
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import net.java.games.input.Controller
import net.java.games.input.ControllerEnvironment
import net.java.games.input.Mouse
import net.minecraft.util.ChatComponentText
import net.minecraft.util.IChatComponent

object RawInput {
    var rawInputJob: Job? = null

    var dx = 0
    var dy = 0
    var controllers: Array<Controller>? = null
    var mouse: Mouse? = null

    fun closeRawInput(message: String) {
        rawInputJob?.cancel(message)
        rawInputJob = null
    }

    fun relaunchRawInput(message: String) {
        rawInputJob?.cancel(message)
        controllers = ControllerEnvironment.getDefaultEnvironment().controllers
        rawInputJob = launchRawInput()
    }

    private fun launchRawInput() = scope.launch {
        controllers?.let { controllers ->
            while (enabled) {
                if (mouse == null) {
                    for (controller in controllers) {
                        if (controller.type == Controller.Type.MOUSE) {
                            controller.poll()
                            val mouseController = controller as Mouse
                            if (mouseController.x.pollData != 0f || mouseController.y.pollData != 0f) {
                                mouse = mouseController
                                mc.thePlayer?.addChatMessage(ChatComponentText("Mouse found: ${mouseController.name}") as IChatComponent) ?: println("Mouse found: ${mouseController.name}")
                                break
                            }
                        }
                    }
                }

                mouse?.let { mouse ->
                    mouse.poll()
                    dx += mouse.x.pollData.toInt()
                    dy += mouse.y.pollData.toInt()
                }

                Thread.sleep(0, waitTime)
            }
        }
    }
}
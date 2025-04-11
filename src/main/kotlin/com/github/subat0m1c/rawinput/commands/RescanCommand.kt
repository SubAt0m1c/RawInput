package com.github.subat0m1c.rawinput.commands

import com.github.subat0m1c.rawinput.RawInput
import com.github.subat0m1c.rawinput.RawInput.relaunchRawInput
import net.java.games.input.ControllerEnvironment
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText
import net.minecraft.util.IChatComponent


class RescanCommand : CommandBase() {
    override fun getCommandName(): String = "rescan"
    override fun getCommandUsage(sender: ICommandSender): String = "Rescans input devices: /rescan <view?|reset?>"

    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        if (args != null && args.getOrNull(0) != null) {
            when (args[0]) {
                "view" -> {
                    ControllerEnvironment.getDefaultEnvironment().controllers.forEach { controller ->
                        sender?.addChatMessage(ChatComponentText("Controller: ${controller.name} (${controller.type})"))
                    }
                }
                "reset" -> {
                    relaunchRawInput("Reset input job command called.")
                }
            }
        }
        sender?.addChatMessage(ChatComponentText("Rescanning input devices...") as IChatComponent)
        RawInput.mouse = null
    }

    override fun getRequiredPermissionLevel(): Int = 0
}
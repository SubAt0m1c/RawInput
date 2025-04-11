package com.github.subat0m1c.rawinput.commands

import com.github.subat0m1c.rawinput.RawInput.relaunchRawInput
import com.github.subat0m1c.rawinput.config.Config
import com.github.subat0m1c.rawinput.config.ConfigManager
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText

class ConfigCommand : CommandBase() {
    override fun getCommandName(): String = "rawconfig"

    override fun getCommandUsage(sender: ICommandSender?): String = "Usage: /rawconfig <waittime|maxchange|view> <int?>"

    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        if (args == null || args.isEmpty()) {
            sender?.addChatMessage(ChatComponentText("Usage: /rawconfig <waittime|maxchange|view> <int?>"))
            return
        }

        when (args.getOrNull(0)) {
            "waittime" -> {
                if (args.size < 2) {
                    sender?.addChatMessage(ChatComponentText("Usage: /rawconfig waittime <int>"))
                    return
                }
                args[1].toIntOrNull()?.let {
                    waitTime = it
                    ConfigManager.save()
                    relaunchRawInput("Wait time updated")
                    sender?.addChatMessage(ChatComponentText("Wait time set to $waitTime"))
                }
            }
            "maxchange" -> {
                if (args.size < 2) {
                    sender?.addChatMessage(ChatComponentText("Usage: /rawconfig maxchange <int>"))
                    return
                }
                args[1].toIntOrNull()?.let {
                    maxChange = it
                    ConfigManager.save()
                    sender?.addChatMessage(ChatComponentText("Max change set to $maxChange"))
                }
            }
            "view" -> {
                sender?.addChatMessage(ChatComponentText("Current wait time: $waitTime"))
                sender?.addChatMessage(ChatComponentText("Current max change: $maxChange"))
            }
            else -> {
                sender?.addChatMessage(ChatComponentText("Unknown command: ${args[0]}. Usage: /rawconfig <waittime|maxchange|view> <int?>"))
                return
            }
        }
    }

    override fun getRequiredPermissionLevel(): Int = 0

    companion object {
        var waitTime by Config("waitTime", Config.intSetting(500000))
        var maxChange by Config("maxChange", Config.intSetting(-1))
    }
}
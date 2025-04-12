package com.github.subat0m1c.rawinput.commands

import com.github.subat0m1c.rawinput.config.Config
import com.github.subat0m1c.rawinput.config.ConfigManager
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText

class ConfigCommand : CommandBase() {
    override fun getCommandName(): String = "rawconfig"

    override fun getCommandUsage(sender: ICommandSender?): String = "Usage: /rawconfig <sens|maxchange|view> <float?|int?>"

    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        if (args == null || args.isEmpty()) {
            sender?.addChatMessage(ChatComponentText("Usage: /rawconfig <sens|maxchange|view> <float?|int?>")) // waittime|
            return
        }

        when (args.getOrNull(0)) {
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
            "sens" -> {
                if (args.size < 2) {
                    sender?.addChatMessage(ChatComponentText("Usage: /rawconfig sense <float>"))
                    return
                }
                args[1].toFloatOrNull()?.let {
                    sens = it
                    ConfigManager.save()
                    sender?.addChatMessage(ChatComponentText("Sensitivity set to $sens"))
                }
            }
            "view" -> {
                sender?.addChatMessage(ChatComponentText("Current sensitivity: $sens"))
                sender?.addChatMessage(ChatComponentText("Current max change: $maxChange"))
            }
            else -> {
                sender?.addChatMessage(ChatComponentText("Unknown command: ${args[0]}. Usage: /rawconfig <sens|maxchange|view> <float?|int?>"))
                return
            }
        }
    }

    override fun getRequiredPermissionLevel(): Int = 0

    companion object {
        var sens by Config("sens", Config.floatSetting(1f))
        var maxChange by Config("maxChange", Config.intSetting(-1))
    }
}
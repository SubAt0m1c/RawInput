package com.github.subat0m1c.rawinput.commands

import com.github.subat0m1c.rawinput.RawInput.closeRawInput
import com.github.subat0m1c.rawinput.RawInput.relaunchRawInput
import com.github.subat0m1c.rawinput.RawInputMain.Companion.mc
import com.github.subat0m1c.rawinput.RawMouseHelper
import com.github.subat0m1c.rawinput.config.Config
import com.github.subat0m1c.rawinput.config.ConfigManager
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText
import net.minecraft.util.IChatComponent
import net.minecraft.util.MouseHelper


class ToggleCommand : CommandBase() {
    override fun getCommandName(): String = "rawinput"
    override fun getCommandUsage(sender: ICommandSender?): String = "Toggles Raw Input (/rawinput)"

    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        enabled = !enabled
        ConfigManager.save()
        if (mc.mouseHelper is RawMouseHelper) {
            mc.mouseHelper = MouseHelper()
            closeRawInput("Toggled rawinput off")
            sender?.addChatMessage(ChatComponentText("Toggled OFF.") as IChatComponent)
        } else {
            mc.mouseHelper = RawMouseHelper()
            relaunchRawInput("Toggled rawinput on")
            sender?.addChatMessage(ChatComponentText("Toggled ON.") as IChatComponent)
        }
    }

    override fun getRequiredPermissionLevel(): Int = 0

    companion object {
        var enabled by Config("enabled", Config.booleanSetting(true))
    }
}
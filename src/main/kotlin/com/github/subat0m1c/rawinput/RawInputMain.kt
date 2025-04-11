package com.github.subat0m1c.rawinput

import com.github.subat0m1c.rawinput.RawInput.relaunchRawInput
import com.github.subat0m1c.rawinput.commands.ConfigCommand
import com.github.subat0m1c.rawinput.commands.RescanCommand
import com.github.subat0m1c.rawinput.commands.ToggleCommand
import com.github.subat0m1c.rawinput.commands.ToggleCommand.Companion.enabled
import com.github.subat0m1c.rawinput.config.ConfigManager
import kotlinx.coroutines.*
import net.minecraft.client.Minecraft
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

@Mod(modid = "rawinput", useMetadata = true)
class RawInputMain {
    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        MinecraftForge.EVENT_BUS.register(this)
        setOf(ToggleCommand(), RescanCommand(), ConfigCommand()).forEach { ClientCommandHandler.instance.registerCommand(it) }
        ConfigManager.awaitLoad()

        if (enabled) {
            mc.mouseHelper = RawMouseHelper()
            relaunchRawInput("Initial rawinput launch")
        }
    }

    companion object {
        val mc = Minecraft.getMinecraft()
        val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    }
}

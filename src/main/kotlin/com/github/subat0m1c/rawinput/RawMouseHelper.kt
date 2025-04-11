package com.github.subat0m1c.rawinput

import com.github.subat0m1c.rawinput.commands.ConfigCommand.Companion.maxChange
import net.minecraft.util.MouseHelper
import kotlin.math.abs

class RawMouseHelper : MouseHelper() {
    override fun mouseXYChange() {
        if ((abs(RawInput.dx) > maxChange || abs(RawInput.dy) > maxChange) && maxChange > 0) {
            RawInput.dx = 0
            RawInput.dy = 0
            return
        }
        deltaX = RawInput.dx
        RawInput.dx = 0
        deltaY = -RawInput.dy
        RawInput.dy = 0
    }
}
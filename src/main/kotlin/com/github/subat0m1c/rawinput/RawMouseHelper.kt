package com.github.subat0m1c.rawinput

import com.github.subat0m1c.rawinput.RawInput.findMouse
import com.github.subat0m1c.rawinput.RawInput.mouse
import com.github.subat0m1c.rawinput.commands.ConfigCommand.Companion.maxChange
import com.github.subat0m1c.rawinput.commands.ToggleCommand.Companion.enabled
import net.minecraft.util.MouseHelper
import kotlin.math.abs

class RawMouseHelper : MouseHelper() {
    override fun grabMouseCursor() {
        if (enabled) mouse?.poll()
        super.grabMouseCursor()
    }

    override fun mouseXYChange() {
        if (!enabled) return super.mouseXYChange()

        if (mouse == null) findMouse()

        mouse?.apply {
            poll()
            val (x, y) = x.pollData.toInt() to -y.pollData.toInt()
            if (maxChange > 0 && (abs(x) > maxChange || abs(y) > maxChange)) {
                deltaX = 0
                deltaY = 0
            } else {
                deltaX = x
                deltaY = y
            }
        }
    }
}
package il.co.galex.namethatcolor.plugin.util

import il.co.galex.namethatcolor.core.model.HexColor

private val nonWordCharRegEx by lazy {
    "\\W+".toRegex()
}

fun xmlOutput(name: String, hexColor: String) = "<color name=\"$name\">$hexColor</color>"
fun kotlinOutput(name: String, hexColor: String): String {
    return "val $name = Color(0xff${
        hexColor.replace(
            nonWordCharRegEx, ""
        )
    })"
}

fun kotlinOutputs(name: String, shades: List<HexColor>, isDarkColors: Boolean): String {
    return StringBuilder().apply {
        for ((index, shade) in shades.withIndex()) {
            val colorId = if (isDarkColors) {
                index + 8
            } else {
                index + 1
            }
            val colorLine = "val ${name}_${colorId}00 = Color(0x${shade.toString().replace("#", "")})"
            append(colorLine).append("\n")
        }
    }.toString()
}
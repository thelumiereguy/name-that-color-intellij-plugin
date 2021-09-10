package il.co.galex.namethatcolor.core.util

import il.co.galex.namethatcolor.core.manager.ColorNameFinder
import il.co.galex.namethatcolor.core.model.Color
import il.co.galex.namethatcolor.core.model.HexColor
import il.co.galex.namethatcolor.core.model.Rgb

object ColorShadeGenerator {
    private const val FACTOR = 0.8f

    fun getBrighterShades(hexColor: HexColor): Triple<HexColor, Color, List<HexColor>> {
        return getShades(isDarker = false, hexColor)
    }

    fun getDarkerShades(hexColor: HexColor): Triple<HexColor, Color, List<HexColor>> {
        return getShades(isDarker = true, hexColor)
    }

    private fun getShades(
        isDarker: Boolean,
        inputHexColor: HexColor
    ): Triple<HexColor, Color, List<HexColor>> {
        val inputAlphaPerc = inputHexColor.percentAlpha ?: 100

        val shades = mutableListOf<HexColor>().apply {
            // First shade
            add(inputHexColor.rgb().copy().toHexColor(inputAlphaPerc))

            // The other six shades
            repeat(6) { currentCount ->
                var rgb = inputHexColor.rgb().copy()
                repeat(currentCount + 1) {
                    rgb = if (isDarker) {
                        rgb.darkerr()
                    } else {
                        rgb.brighterr()
                    }
                }
                add(rgb.toHexColor(inputAlphaPerc))
            }
        }
        val color = ColorNameFinder.findColor(inputHexColor)
        return Triple(inputHexColor, color.second, shades)
    }

    private fun Rgb.toHexColor(alphaPerc: Int): HexColor {
        val finalColor = String.format("%02x%02x%02x", r, g, b);
        return HexColor("#${alphaPerc}%$finalColor")
    }

    private fun Rgb.darkerr(): Rgb {
        return Rgb(
            ((r * FACTOR).toInt()).coerceAtLeast(0),
            ((g * FACTOR).toInt()).coerceAtLeast(0),
            ((b * FACTOR).toInt()).coerceAtLeast(0),
        )
    }

    private fun Rgb.brighterr(): Rgb {
        var red: Int = r
        var green: Int = g
        var blue: Int = b
        val i = (1.0 / (1.0 - FACTOR)).toInt()
        if (red == 0 && green == 0 && blue == 0) {
            return Rgb(i, i, i)
        }
        if (red in 1 until i) red = i
        if (green in 1 until i) green = i
        if (blue in 1 until i) blue = i
        return Rgb(
            (red / FACTOR).toInt().coerceAtMost(255),
            (green / FACTOR).toInt().coerceAtMost(255),
            (blue / FACTOR).toInt().coerceAtMost(255)
        )
    }
}
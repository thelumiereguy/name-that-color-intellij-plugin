package il.co.galex.namethatcolor.core.util

import il.co.galex.namethatcolor.core.model.HexColor
import org.junit.Test
import kotlin.test.assertEquals


class ColorShadeGeneratorTest {

    @Test
    fun findBrightShades() {
        val input = HexColor("#80056df5")
        val output = ColorShadeGenerator.getBrighterShades(input)
        assertEquals(
            "[#80056DF5, #800688FF, #8007AAFF, #8008D4FF, #800AFFFF, #800CFFFF, #800FFFFF]",
            output.third.toString()
        )
    }

    @Test
    fun findDarkShades() {
        val input = HexColor("#80056df5")
        val output = ColorShadeGenerator.getDarkerShades(input)
        assertEquals(
            "[#80056DF5, #800457C4, #8003459C, #8002377C, #80012C63, #8000234F, #80001C3F]",
            output.third.toString()
        )
    }
}
package il.co.galex.namethatcolor.plugin.completion

import com.intellij.codeInsight.completion.*
import com.intellij.openapi.application.ex.ClipboardUtil
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import il.co.galex.namethatcolor.core.manager.ColorNameFinder
import il.co.galex.namethatcolor.core.util.ColorShadeGenerator
import il.co.galex.namethatcolor.plugin.util.*

/**
 *  Completes the color from the clipboard
 */
class ClipboardCompletionKotlinContributor : CompletionContributor() {

    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(),
            object : CompletionProvider<CompletionParameters>() {

                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    resultSet: CompletionResultSet
                ) {

                    ClipboardUtil.getTextInClipboard()?.let {
                        resultSet.addKotlinElement(NAME_THAT_COLOR, it, ColorNameFinder::findColor)
                        resultSet.addKotlinElement(NAME_THAT_MATERIAL_COLOR, it, ColorNameFinder::findMaterialColor)
                        resultSet.addMultipleKotlinElements(NAME_7_SEVEN_BRIGHT_SHADES, it, ColorShadeGenerator::getBrighterShades)
                        resultSet.addMultipleKotlinElements(NAME_7_SEVEN_DARK_SHADES, it, ColorShadeGenerator::getDarkerShades)
                    }
                }
            })
    }
}

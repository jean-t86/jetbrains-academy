package signature

import java.io.File

class Font(fontPath: String, fontSpacing: Int) {

    private val file = File(fontPath)

    private val font = mutableMapOf<String, MutableList<String>>()

    val charHeight: Int

    init {
        if (!file.exists()) throw NoSuchFileException(file)
        if (!file.canRead()) throw AccessDeniedException(file)

        val fontDescriptor = file.readLines()

        charHeight = fontDescriptor[0].split(" ")[0].toInt()

        // Load alphabet from font descriptor file in font map variable
        for (i in 1..fontDescriptor.lastIndex step charHeight + 1) {
            val char = fontDescriptor[i].split(" ").getOrNull(0)
            char?.let {
                font[it] = mutableListOf()
                for (j in 1..charHeight) {
                    val line = fontDescriptor[i + j]
                    font[it]?.add(line)
                }
            }
        }

        // Add spacing character to font map variable
        font[" "] = mutableListOf()
        for (j in 1..charHeight) {
            font[" "]?.add(" ".repeat(fontSpacing))
        }
    }

    fun asciiSentence(sentence: String) = sentence.map { font[it.toString()]!! }

    fun measureSentence(sentence: String) = sentence.sumOf { measureLetter(it.toString()) }

    private fun measureLetter(letter: String) = font[letter]!!.maxOf { it.length }

}
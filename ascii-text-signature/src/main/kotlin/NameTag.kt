package signature

const val TAG_PADDING = 2
const val BEGIN_AND_END_SEPARATORS = 4
const val NO_PADDING = 0

class NameTag(
    private val name: String,
    private val nameFont: Font,
    private val status: String,
    private val statusFont: Font,
    private val separator: String = "*"
) {

    fun print() {
        val nameLength = nameFont.measureSentence(name)
        val statusLength = statusFont.measureSentence(status)

        val leftPadding: Int
        val rightPadding: Int

        if (nameLength > statusLength) {
            leftPadding = (nameLength - statusLength) / 2
            rightPadding = nameLength - (leftPadding + statusLength)

            printHeaderFooter(nameLength)
            printAscii(name, nameFont, NO_PADDING, NO_PADDING)
            printAscii(status, statusFont, leftPadding, rightPadding)
            printHeaderFooter(nameLength)
        } else {
            leftPadding = (statusLength - nameLength) / 2
            rightPadding = statusLength - (leftPadding + nameLength)

            printHeaderFooter(statusLength)
            printAscii(name, nameFont, leftPadding, rightPadding)
            printAscii(status, statusFont, NO_PADDING, NO_PADDING)
            printHeaderFooter(statusLength)
        }
    }

    private fun printHeaderFooter(length: Int) =
        println(separator.repeat(length + TAG_PADDING * 2 + BEGIN_AND_END_SEPARATORS))

    private fun printAscii(sentence: String, font: Font, leftPadding: Int, rightPadding: Int) {
        val asciiSentence = font.asciiSentence(sentence)
        for (i in 0 until font.charHeight) {
            print(separator.repeat(2))
            print(" ".repeat(TAG_PADDING + leftPadding))

            for (j in 0..asciiSentence.lastIndex) {
                val char = asciiSentence[j][i]
                print(char)
                if (j == asciiSentence.lastIndex) {
                    print(" ".repeat(TAG_PADDING + rightPadding))
                    print(separator.repeat(2))
                }
            }

            println()
        }
    }

}
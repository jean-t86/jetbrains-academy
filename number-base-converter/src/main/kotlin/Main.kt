package converter

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

const val EXIT_COMMAND = "/exit"
const val BACK_COMMAND = "/back"
const val UTF_CODE_TO_FROM_CONVERSION = 55

fun main() {

    do {
        print("Enter two numbers in format: {source base} {target base} (To quit type /exit) > ")
        val input = readLine()

        if (input != null && input.isNotEmpty()) {
            val (sourceBase, targetBase) = try {
                val bases = input.split(" ").map(String::toIntOrNull)
                if (bases.size != 2) throw IllegalArgumentException()
                bases
            } catch (e: Exception) {
                continue
            }
            if (sourceBase == null || targetBase == null) continue

            do {
                print("Enter number in base $sourceBase to convert to base $targetBase (To go back type /back) > ")
                val conversionInput = readLine()
                if (conversionInput == BACK_COMMAND) continue
                if (conversionInput != null && conversionInput.isNotEmpty()) {
                    if (conversionInput.contains(".")) {
                        val (integer, fraction) = conversionInput.split(".").take(2)
                        val integerInBase10 = convertToBase10(integer, sourceBase)
                        val fractionInBase10 = convertFractionToBase10(fraction, sourceBase)
                        val integerInTargetBase = convertFromBase10(integerInBase10, targetBase)
                        val fractionInTargetBase = convertFractionFromBase10(fractionInBase10, targetBase)
                        println("Conversion result: $integerInTargetBase.$fractionInTargetBase")
                        println()
                    } else {
                        val inBase10 = convertToBase10(conversionInput, sourceBase)
                        val inTargetBase = convertFromBase10(inBase10, targetBase)
                        println("Conversion result: $inTargetBase")
                        println()
                    }
                }
            } while (conversionInput != BACK_COMMAND)
        }
    } while (input != EXIT_COMMAND)
}

fun convertFractionToBase10(sourceFraction: String, sourceBase: Int): BigDecimal {
    if (sourceBase == 10) return BigDecimal("0.$sourceFraction")

    val fraction = sourceFraction.uppercase()
    var result = BigDecimal.ZERO
    for (i in 0..fraction.lastIndex) {
        val digit = if (fraction[i] in 'A'..'Z') {
            convertLetterToNumber(fraction[i])
        } else {
            fraction[i].toString()
        }
        result += BigDecimal(digit) * BigDecimal.valueOf(sourceBase.toDouble().pow(-i - 1))
    }

    return result
}

fun convertToBase10(sourceNumber: String, sourceBase: Int): BigDecimal {
    if (sourceBase == 10) return BigDecimal(sourceNumber)

    var result = BigDecimal.ZERO
    val reversedSourceNumber = sourceNumber.uppercase().reversed()
    for (i in reversedSourceNumber.lastIndex downTo 0) {
        val digit = if (reversedSourceNumber[i] in 'A'..'Z') {
            convertLetterToNumber(reversedSourceNumber[i])
        } else {
            reversedSourceNumber[i].toString()
        }
        result += BigDecimal(digit) * BigDecimal.valueOf(sourceBase.toDouble().pow(i).toLong())
    }
    return result
}

fun convertFractionFromBase10(fraction: BigDecimal, radix: Int): String {
    val remainders = mutableListOf<String>()
    var count = 0
    var mutableFraction = fraction
    do {
        var result = (mutableFraction * BigDecimal(radix)).setScale(15, RoundingMode.HALF_UP)

        val digit = result.toInt().toBigDecimal()
        val remainder = if (digit >= BigDecimal.TEN) {
            convertNumberToLetter(digit.toInt())
        } else {
            digit
        }

        remainders.add(remainder.toString())

        result = if (result > BigDecimal.ONE) {
            result - digit
        } else {
            result
        }
        mutableFraction = result

        count++
        if (count >= 5) break
    } while (result.setScale(0, RoundingMode.DOWN) != BigDecimal.ONE)

    while (remainders.size != 5) {
        remainders.add("0")
    }

    return remainders.joinToString("")
}

fun convertFromBase10(decimal: BigDecimal, radix: Int): String {
    var remainders = mutableListOf<String>()
    var quotient = decimal
    while (quotient != BigDecimal.ZERO) {
        val quotientAndRemainder = quotient.divideAndRemainder(BigDecimal.valueOf(radix.toLong()))
        remainders.add(quotientAndRemainder[1].toString())
        quotient = quotientAndRemainder[0]
    }

    remainders = remainders.map {
        val number = it.toInt()
        if (number in 10..36) {
            convertNumberToLetter(number)
        } else {
            number.toString()
        }
    }.toMutableList()

    val result = if (remainders.isEmpty()) {
        "0"
    } else {
        remainders.joinToString("").reversed()
    }
    return result
}

fun convertLetterToNumber(letter: Char): String = (letter.code - UTF_CODE_TO_FROM_CONVERSION).toString()

fun convertNumberToLetter(number: Int) = Char((number + UTF_CODE_TO_FROM_CONVERSION)).toString()

package cinema

import java.math.BigDecimal
import java.math.RoundingMode

const val MAX_SEATS_AT_10_DOLLARS = 60
const val TEN_DOLLARS_PER_SEAT = 10
const val EIGHT_DOLLARS_PER_SEAT = 8

fun main() {
    var rows: Int?
    do {
        println("Enter the number of rows:")
        rows = readLine()!!.toIntOrNull()
    } while (rows == null)

    var seatsPerRow: Int?
    do {
        println("Enter the number of seats in each row:")
        seatsPerRow = readLine()!!.toIntOrNull()
    } while (seatsPerRow == null)

    println()

    val cinema = List(rows) {
        MutableList(seatsPerRow) { "S" }
    }

    do {
        println(
            """
            1. Show the seats
            2. Buy a ticket
            3. Statistics
            0. Exit
            """.trimIndent()
        )
        val menuChoice = readLine()!!.toIntOrNull()
        when (menuChoice) {
            1 -> printCinema(cinema)
            2 -> buyTicket(rows, seatsPerRow, cinema)
            3 -> printStatistics(cinema)
            0 -> return
            else -> continue
        }
    } while (menuChoice != 0)
}

fun printStatistics(cinema: List<MutableList<String>>) {
    val allSeats = cinema.flatten()
    val totalPurchasedTickets = allSeats.count { it == "B" }
    val percentageTicketsSold = BigDecimal((100.0 / allSeats.size) * totalPurchasedTickets)
        .setScale(2, RoundingMode.HALF_UP)

    var currentIncome = 0
    var totalPotentialIncome = 0
    for (row in cinema.indices) {
        for (col in cinema[row].indices) {
            if (cinema[row][col] == "B") {
                currentIncome += getTicketPrice(allSeats.size, cinema.size, row + 1)
            }
            totalPotentialIncome += getTicketPrice(allSeats.size, cinema.size, row + 1)
        }
    }

    println()
    println("Number of purchased tickets: $totalPurchasedTickets")
    println("Percentage: $percentageTicketsSold%")
    println("Current income: $$currentIncome")
    println("Total income: $$totalPotentialIncome")
    println()
}

fun printCinema(cinema: List<List<String>>) {
    println()
    println("Cinema:")
    print("  ")
    for (i in cinema[0].indices) {
        if (i != cinema[0].lastIndex) {
            print("${i + 1} ")
        } else {
            print("${i + 1}")
        }
    }
    println()

    for (i in cinema.indices) {
        print("${i + 1} ")
        for (j in cinema[i].indices) {
            val seat = cinema[i][j]
            if (j != cinema[i].lastIndex) {
                print("$seat ")
            } else {
                print(seat)
            }
        }
        println()
    }
    println()
}

fun buyTicket(rows: Int, seatsPerRow: Int, cinema: List<MutableList<String>>) {
    println()
    println("Enter a row number:")
    var rowNumber = readLine()!!.toInt()
    println("Enter a seat number in that row:")
    var seatNumber = readLine()!!.toInt()
    while (outOfBounds(rowNumber, seatNumber, cinema) ||
        seatTaken(rowNumber, seatNumber, cinema)

    ) {
        println()
        if (outOfBounds(rowNumber, seatNumber, cinema)) {
            println("Wrong input!")
        } else if (seatTaken(rowNumber, seatNumber, cinema)) {
            println("That ticket has already been purchased!")
        }
        println()
        println("Enter a row number:")
        rowNumber = readLine()!!.toInt()
        println("Enter a seat number in that row:")
        seatNumber = readLine()!!.toInt()
    }

    val totalSeats = rows * seatsPerRow
    val ticketPrice = getTicketPrice(totalSeats, rows, rowNumber)

    println()
    println("Ticket price: $$ticketPrice")
    println()

    cinema[rowNumber - 1][seatNumber - 1] = "B"
}

private fun getTicketPrice(totalSeats: Int, rows: Int, rowNumber: Int) =
    if (totalSeats <= MAX_SEATS_AT_10_DOLLARS) {
        TEN_DOLLARS_PER_SEAT
    } else {
        val frontHalfSeats = rows / 2
        if (rowNumber <= frontHalfSeats) {
            TEN_DOLLARS_PER_SEAT
        } else {
            EIGHT_DOLLARS_PER_SEAT
        }
    }

fun outOfBounds(rowNumber: Int, seatNumber: Int, cinema: List<MutableList<String>>) =
    rowNumber - 1 >= cinema.size || seatNumber - 1 >= cinema[0].size

fun seatTaken(rowNumber: Int, seatNumber: Int, cinema: List<MutableList<String>>) =
    cinema[rowNumber - 1][seatNumber - 1] == "B"

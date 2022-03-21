package machine

class CoffeeMachine(
    private var water: Int,
    private var milk: Int,
    private var coffeeBeans: Int,
    private var disposableCups: Int,
    private var money: Int
) {
    private data class Coffee(
        val water: Int = 0,
        val milk: Int = 0,
        val coffeeBeans: Int = 0,
        val cost: Int = 0
    )

    private val espresso = Coffee(
        water = 250,
        coffeeBeans = 16,
        cost = 4
    )

    private val latte = Coffee(
        water = 350,
        milk = 75,
        coffeeBeans = 20,
        cost = 7
    )

    private val cappuccino = Coffee(
        water = 200,
        milk = 100,
        coffeeBeans = 12,
        cost = 6
    )

    private fun printState() {
        println(
            """
            The coffee machine has:
            $water of water
            $milk of milk
            $coffeeBeans of coffee beans
            $disposableCups of disposable cups
        """.trimIndent()
        )
        println(if (money != 0) "$$money of money" else "0 of money")
    }

    fun queryUserAction() {
        do {
            print("Write action (buy, fill, take, remaining, exit): ")
            val action = readLine()!!
            println()
            when (action) {
                "buy" -> {
                    print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ")
                    when (readLine()!!) {
                        "1" -> make(espresso)
                        "2" -> make(latte)
                        "3" -> make(cappuccino)
                        "back" -> {
                            println()
                            continue
                        }
                    }

                }
                "fill" -> fill()
                "take" -> cashOut()
                "remaining" -> printState()
            }
            println()
        } while (action != "exit")
    }

    private fun cashOut() {
        println("I gave you \$$money")
        money = 0
    }

    private fun fill() {
        print("Write how many ml of water do you want to add: ")
        water += readLine()!!.toInt()
        print("Write how many ml of milk do you want to add: ")
        milk += readLine()!!.toInt()
        print("Write how many grams of coffee beans do you want to add: ")
        coffeeBeans += readLine()!!.toInt()
        print("Write how many disposable cups of coffee do you want to add: ")
        disposableCups += readLine()!!.toInt()
    }

    private fun make(coffee: Coffee) {
        val (hasEnoughResources, message) = hasEnoughResources(coffee)
        println(message)
        if (hasEnoughResources) {
            water -= coffee.water
            milk -= coffee.milk
            coffeeBeans -= coffee.coffeeBeans
            disposableCups--
            money += coffee.cost
        }
    }

    private fun hasEnoughResources(coffee: Coffee): Pair<Boolean, String> {
        return when {
            water >= coffee.water &&
                milk >= coffee.milk &&
                coffeeBeans >= coffee.coffeeBeans &&
                disposableCups > 0 -> Pair(true, "I have enough resources, making you a coffee!")
            water < coffee.water -> Pair(false, "Sorry, not enough water!")
            milk < coffee.milk -> Pair(false, "Sorry, not enough milk!")
            coffeeBeans < coffee.coffeeBeans -> Pair(false, "Sorry, not enough coffee beans!")
            else -> Pair(false, "Sorry, not enough disposable pods!")
        }
    }
}
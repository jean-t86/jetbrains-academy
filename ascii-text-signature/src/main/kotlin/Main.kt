package signature

fun main() {
    print("Enter name and surname: ")
    val name = readLine()!!
    print("Enter person's status: ")
    val status = readLine()!!
    NameTag(
        name,
        nameFont = Font("/Users/jean/Downloads/roman.txt", 10),
        status,
        statusFont = Font("/Users/jean/Downloads/medium.txt", 5),
        "8"
    ).print()
}

fun main(args: Array<String>) {
    if (args.size != 3) {
        println("Invalid number of program arguments")
        return
    }

    args.forEachIndexed { i, s -> println("Argument ${i + 1}: $s. It has ${s.length} characters") }
}

fun solution(numbers: List<Int>, number: Int): MutableList<Int> {
    val newList = numbers.toMutableList()
    newList.add(number)
    return newList
}
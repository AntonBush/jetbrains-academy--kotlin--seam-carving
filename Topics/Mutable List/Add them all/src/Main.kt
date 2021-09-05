fun solution(first: List<Int>, second: List<Int>): MutableList<Int> {
    val newList = first.toMutableList()
    newList.addAll(second)
    return newList
}
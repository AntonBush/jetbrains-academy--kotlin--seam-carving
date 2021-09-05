package seamcarving

typealias Matrix<E> = List<List<E>>
typealias MutableMatrix<E> = MutableList<MutableList<E>>

fun <E> Matrix<E>.getWidth(): Int {
    return this[0].size
}

fun <E> Matrix<E>.getHeight(): Int {
    return this.size
}

operator fun <E> Matrix<E>.get(point: MatrixPoint): E {
    return this[point.y][point.x]
}

fun <E> Matrix<E>.transpose(): Matrix<E> {
    return Matrix(getHeight(), getWidth()) {
        i, j -> this[j][i]
    }
}

data class MatrixPoint(val x: Int, val y: Int)

fun <E> Matrix(width: Int, height: Int, init: (Int, Int) -> E): Matrix<E> {
    return List(height) { i ->
        List(width) { j ->
            init(i, j)
        }
    }
}

fun <E> MutableMatrix(width: Int, height: Int, init: (Int, Int) -> E): MutableMatrix<E> {
    return MutableList(height) { i ->
        MutableList(width) { j ->
            init(i, j)
        }
    }
}

fun sqrInt(a: Int): Int {
    return a * a
}

fun pushIntIntoBounds(value: Int, lowerBound: Int, upperBound: Int): Int {
    return when {
        value < lowerBound -> lowerBound
        upperBound < value -> upperBound
        else -> value
    }
}

//val doubleComparator: (Double, Double) -> Int = { d1, d2 ->
//    when {
//        d1 < d2 -> -1
//        d1 > d2 -> 1
//        else -> 0
//    }
//}
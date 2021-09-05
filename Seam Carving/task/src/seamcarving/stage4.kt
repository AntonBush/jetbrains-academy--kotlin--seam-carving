package seamcarving

import java.awt.Color
import kotlin.collections.ArrayDeque

interface Seam {
    fun getPoints(): List<MatrixPoint>
    val length: Double
}

class MutableSeam(initialCapacity: Int = 0): Seam {
    private val points = ArrayDeque<MatrixPoint>(initialCapacity)
    override var length: Double = 0.0
        private set

    constructor(seam: Seam) : this(seam.length.toInt()) {
        for (point in seam.getPoints()) {
            points.addLast(point)
        }
        length = seam.length
    }

    override fun getPoints(): List<MatrixPoint> {
        return points
    }

    fun add(point: MatrixPoint, length: Double) {
        points.addLast(point)
        this.length += length
    }
}

fun findShortestSeam(point: MatrixPoint, matrix: Matrix<Double>, seams: MutableMatrix<Seam?>): Seam {
    if (point.y == matrix.getHeight() - 1) {
        val seam = MutableSeam()
        seam.add(point, matrix[point])
        return seam
    }

    val xUpperBound = matrix[0].lastIndex
    val leftX = pushIntIntoBounds(point.x - 1, 0, xUpperBound)
    val rightX = pushIntIntoBounds(point.x + 1, 0, xUpperBound)

    val y = point.y + 1
    var minVal = seams[y][leftX]!!.length
    var minSeam = seams[y][leftX]!!
    for (x in leftX..rightX) {
//        println("${matrix[point.y][point.x]} : ${matrix[y][x]}")
        if (minVal > seams[y][x]!!.length) {
            minVal = seams[y][x]!!.length
            minSeam = seams[y][x]!!
        }
    }

    val shortestSeam = MutableSeam(minSeam)
    shortestSeam.add(point, matrix[point])
    return shortestSeam
}

fun findSeamToRemove(energyMatrix: EnergyMatrix): Seam {
    val width = energyMatrix.getWidth()
    val height = energyMatrix.getHeight()
    val seams = MutableMatrix<Seam?>(width, height) { _, _ -> null }
    val points = Matrix(width, height) { i, j -> MatrixPoint(j, i) }

    for (i in height - 1 downTo 0) {
        for (j in 0 until width) {
            seams[i][j] = findShortestSeam(points[i][j], energyMatrix, seams)
//            println("i: $i, j: $j -> ${seams[i][j]!!.length}")
            if (i < height - 2) {
                seams[i + 2][j] = null
            }
        }
    }

    val minLength = seams[0].minOf { it!!.length }
    val minLengthSeams = seams[0].filter { it!!.length == minLength }
    return minLengthSeams.first()!!
}

fun markIntColorMatrixBySeam(seam: Seam, intColorMatrix: Matrix<Int>): Matrix<Int> {
    return Matrix(intColorMatrix.getWidth(), intColorMatrix.getHeight()) { y, x ->
        if (seam.getPoints().contains(MatrixPoint(x, y))) Color.RED.rgb else intColorMatrix[y][x]
    }
}
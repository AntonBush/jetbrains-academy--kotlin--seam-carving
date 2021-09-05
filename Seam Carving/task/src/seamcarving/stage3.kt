package seamcarving

import java.awt.Color
import kotlin.math.sqrt

typealias ColorMatrix = Matrix<Color>
typealias EnergyMatrix = Matrix<Double>
typealias IntensityMatrix = Matrix<Int>

fun intMatrixOf(width: Int, height: Int, intArray: IntArray): Matrix<Int> {
    return Matrix(width, height) { i, j -> intArray[i * width + j] }
}

fun colorMatrixOf(argbMatrix: Matrix<Int>): ColorMatrix {
    val width = argbMatrix.getWidth()
    val height = argbMatrix.getHeight()
    return Matrix(width, height) { i, j -> Color(argbMatrix[i][j]) }
}

fun energyMatrixOf(colorMatrix: ColorMatrix): EnergyMatrix {
    val width = colorMatrix.getWidth()
    val height = colorMatrix.getHeight()
    return Matrix(width, height) { i, j -> getEnergyOfPixel(MatrixPoint(j, i), colorMatrix) }
}

fun intensityMatrixOf(energyMatrix: EnergyMatrix): IntensityMatrix {
    val width = energyMatrix.getWidth()
    val height = energyMatrix.getHeight()
    val maxEnergyValue = energyMatrix.maxOf { array -> array.maxOf { it } }
    return Matrix(width, height) { i, j -> intensityOf(energyMatrix[i][j], maxEnergyValue) }
}

fun intensityOf(energy: Double, maxEnergy: Double): Int {
    return (255.0 * energy / maxEnergy).toInt()
}

fun getEnergyOfPixel(point: MatrixPoint, colorMatrix: ColorMatrix): Double {
    val width = colorMatrix.getWidth()
    val height = colorMatrix.getHeight()

    val x = pushIntIntoBounds(point.x, 1, width - 2)
    val y = pushIntIntoBounds(point.y, 1, height - 2)

    val deltaX2 = delta2(colorMatrix[point.y][x - 1], colorMatrix[point.y][x + 1])
    val deltaY2 = delta2(colorMatrix[y - 1][point.x], colorMatrix[y + 1][point.x])

    return sqrt(deltaX2.toDouble() + deltaY2.toDouble())
}

fun delta2(color1: Color, color2: Color): Int {
    val rx = color1.red - color2.red
    val gx = color1.green - color2.green
    val bx = color1.blue - color2.blue

    return sqrInt(rx) + sqrInt(gx) + sqrInt(bx)
}

fun intColorMatrixOf(intensityMatrix: IntensityMatrix): Matrix<Int> {
    val width = intensityMatrix.getWidth()
    val height = intensityMatrix.getHeight()
    return Matrix(width, height) { i, j -> intColorOf(intensityMatrix[i][j]) }
}

fun intArrayOf(matrix: Matrix<Int>): IntArray {
    val width = matrix.getWidth()
    val height = matrix.getHeight()
    return IntArray(width * height) { i ->
        matrix[i / width][i % width]
    }
}

fun intColorOf(intensity: Int): Int {
    return intensity * 256 * 256 + intensity * 256 + intensity
}
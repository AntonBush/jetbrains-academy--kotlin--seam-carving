package seamcarving

fun resizeRGBMatrix(resizedWidth: Int, resizedHeight: Int, rgbMatrix: Matrix<Int>): Matrix<Int> {
    var resizedMatrix = rgbMatrix
    var resizedEnergyMatrix = energyMatrixOf(colorMatrixOf(resizedMatrix))
    for (k in 1..resizedWidth) {
        println("${resizedEnergyMatrix.getHeight()} ${resizedEnergyMatrix.getWidth()}")
        val seam = findSeamToRemove(resizedEnergyMatrix)
        resizedMatrix = removeSeamFromIntColorMatrix(seam, resizedMatrix)
        resizedEnergyMatrix = removeSeamFromIntColorMatrix(seam, resizedEnergyMatrix)
    }

    resizedMatrix = resizedMatrix.transpose()
    resizedEnergyMatrix = resizedEnergyMatrix.transpose()
    for (k in 1..resizedHeight) {
        println("${resizedEnergyMatrix.getHeight()} ${resizedEnergyMatrix.getWidth()}")
        val seam = findSeamToRemove(resizedEnergyMatrix)
        resizedMatrix = removeSeamFromIntColorMatrix(seam, resizedMatrix)
        resizedEnergyMatrix = removeSeamFromIntColorMatrix(seam, resizedEnergyMatrix)
    }

    return resizedMatrix.transpose()
}

fun <E: Number> removeSeamFromIntColorMatrix(seam: Seam, intColorMatrix: Matrix<E>): Matrix<E> {
    return List(intColorMatrix.getHeight()) { i ->
        var offset = 0
        List(intColorMatrix.getWidth() - 1) { j ->
            if (seam.getPoints().contains(MatrixPoint(i, j))) {
                ++offset
            }
            intColorMatrix[i][j]
        }
    }
}
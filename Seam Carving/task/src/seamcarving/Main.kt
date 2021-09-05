package seamcarving

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File

fun extractArgument(id: String, args: Array<String>): String {
    return args[args.indexOf(id) + 1]
}

fun main(args: Array<String>) {
    val inFilename = extractArgument("-in", args)
    val outFilename = extractArgument("-out", args)
    val width = extractArgument("-width", args).toInt()
    val height = extractArgument("-height", args).toInt()
//    val inFilename = "bridge.png"
//    val outFilename = "bridge-seam.png"

    val image = ImageIO.read(File(inFilename))

    val pixels = IntArray(image.width * image.height)
    image.getRGB(0, 0, image.width, image.height, pixels, 0, image.width)
    val oldRGBMatrix = intMatrixOf(image.width, image.height, pixels)
    val newRGBMatrix = resizeRGBMatrix(width, height, oldRGBMatrix)
    val newImage = BufferedImage(image.width - width, image.height - height, BufferedImage.TYPE_INT_ARGB)
    newImage.setRGB(0, 0, newImage.width, newImage.height, intArrayOf(newRGBMatrix), 0, newImage.width)

    ImageIO.write(newImage, "png", File(outFilename))
}

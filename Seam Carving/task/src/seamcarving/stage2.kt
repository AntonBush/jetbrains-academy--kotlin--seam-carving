package seamcarving

import kotlin.math.pow

fun reverseARGB(argb: Int): Int {
    val a = argb / (256.0.pow(3).toInt())
    val r = 255 - argb / (256.0.pow(2).toInt()) % 256
    val g = 255 - argb / 256 % 256
    val b = 255 - argb % 256
    var reversedArgb = a * (256.0.pow(3).toInt())
    reversedArgb += r * (256.0.pow(2).toInt())
    reversedArgb += g * 256
    reversedArgb += b
    return reversedArgb
}

fun reverseARGB(w: Int, h: Int, argbArray: IntArray) {
    for (y in 0 until h) {
        for (x in y * w until (y + 1) * w) {
            argbArray[x] = reverseARGB(argbArray[x])
        }
    }
}
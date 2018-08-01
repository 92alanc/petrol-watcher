package com.braincorp.petrolwatcher.utils

/**
 * Converts an int array to a range
 *
 * @return the range
 */
fun IntArray.toRange(): IntRange {
    this.sort()
    return IntRange(this[0], this[size - 1])
}

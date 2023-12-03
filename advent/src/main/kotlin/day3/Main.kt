package day3

import java.io.File

data class Point(val x: Int, val y: Int) {
    fun neighbors(): Set<Point> =
        setOf(
            Point(x - 1, y - 1),
            Point(x, y - 1),
            Point(x + 1, y - 1),
            Point(x - 1, y),
            Point(x + 1, y),
            Point(x - 1, y + 1),
            Point(x, y + 1),
            Point(x + 1, y + 1)
        )
}

class NumberLocation {
    private val number = mutableListOf<Char>()
    private val locations = mutableSetOf<Point>()

    fun add(c: Char, location: Point) {
        number.add(c)
        locations.addAll(location.neighbors())
    }

    fun isNotEmpty() =
        number.isNotEmpty()

    fun isAdjacentToAny(points: Set<Point>): Boolean =
        locations.intersect(points).isNotEmpty()

    fun isAdjacentTo(point: Point): Boolean =
        point in locations

    fun toInt(): Int =
        number.joinToString("").toInt()
}

fun part1(file: File): Int {
    val _file = file.readLines()
    val length = _file.size
    val width = _file[0].length
    // let's add a border to the array, so when we check neighbors it wont go OOB
    val input = mutableListOf<String>()
    input.add(".".repeat(width))
    _file.forEach {
        input.add(".${it}.")
    }
    input.add(".".repeat(width))

    val numbers = mutableSetOf<NumberLocation>()
    val symbols = mutableSetOf<Point>()
    var workingNumber = NumberLocation()

    input
        .forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c.isDigit()) {
                    workingNumber.add(c, Point(x, y))
                } else {
                    if (workingNumber.isNotEmpty()) {
                        // assumption is no number touches more than one symbol, but using a set anyways
                        numbers.add(workingNumber)
                        // reset working number
                        workingNumber = NumberLocation()
                    }
                    if (c != '.') {
                        symbols.add(Point(x, y))
                    }
                }
            }
        }

    return numbers
        .filter { number -> number.isAdjacentToAny(symbols) }
        .sumOf { it.toInt() }
}

fun part2(file: File): Int {
    val _file = file.readLines()
    val length = _file.size
    val width = _file[0].length
    // let's add a border to the array, so when we check neighbors it wont go OOB
    val input = mutableListOf<String>()
    input.add(".".repeat(width))
    _file.forEach {
        input.add(".${it}.")
    }
    input.add(".".repeat(width))

    val numbers = mutableSetOf<NumberLocation>()
    val symbols = mutableSetOf<Point>()
    var workingNumber = NumberLocation()

    input
        .forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c.isDigit()) {
                    workingNumber.add(c, Point(x, y))
                } else {
                    if (workingNumber.isNotEmpty()) {
                        // assumption is no number touches more than one symbol, but using a set anyways
                        numbers.add(workingNumber)
                        // reset working number
                        workingNumber = NumberLocation()
                    }
                    // in this part, only care for *
                    if (c == '*') {
                        symbols.add(Point(x, y))
                    }
                }
            }
        }

    return symbols
        .sumOf { symbol ->
            val neighbors = numbers.filter { it.isAdjacentTo(symbol) }
            if (neighbors.size == 2) {
                neighbors.map{ it.toInt() }.reduce(Int::times)
            } else 0
        }
}

fun main(args: Array<String>) {
    // get input file
    val file = File("./src/main/kotlin/day3/input.txt").absoluteFile

    println("part1 sum is ${part1(file)}")
    println("part2 res is ${part2(file)}")
}
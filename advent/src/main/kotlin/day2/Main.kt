package day2

import java.io.File
import kotlin.time.times

fun part1(file: File): Int {
    // only 12 red cubes, 13 green cubes, and 14 blue cubes
    val redRe = Regex("([1-9][3-9]|[2-9][0-9]|\\d{3,}) red")
    val greenRe = Regex("([1-9][4-9]|[2-9][0-9]|\\d{3,}) green")
    val blueRe = Regex("([1-9][5-9]|[2-9][0-9]|\\d{3,}) blue")

    var validGameSum = 0
    var gameNum = 1
    file.readLines().forEach { game ->
        if (!redRe.containsMatchIn(game) && !blueRe.containsMatchIn(game) && !greenRe.containsMatchIn(game)) {
//            println("game $gameNum is valid")
            validGameSum += gameNum
        }

        gameNum++
    }

    return validGameSum
}

fun part2(file: File): Int {
    // goal is to calculate fewest of r,g,b in each game then multiply the fewest of each
    var powerSetSum = 0

    val redRe = Regex("\\d{1,}red")
    val greenRe = Regex("\\d{1,}green")
    val blueRe = Regex("\\d{1,}blue")

    file.readLines().forEach { game ->
        val strippedGameString = game.filterNot { it.isWhitespace() }

        val red = redRe.findAll(strippedGameString).map { it.value.replace("red", "").toInt() }.toList().max()
        val green = greenRe.findAll(strippedGameString).map { it.value.replace("green", "").toInt() }.toList().max()
        val blue = blueRe.findAll(strippedGameString).map { it.value.replace("blue", "").toInt() }.toList().max()
        println("fewest in game is r=$red g=$green b=$blue")
        powerSetSum += (red * green * blue)
    }

    return powerSetSum
}

fun main(args: Array<String>) {
    // get input file
    val file = File("./src/main/kotlin/day2/input.txt").absoluteFile

    println("part1 sum is ${part1(file)}")
    println("part2 res is ${part2(file)}")
}
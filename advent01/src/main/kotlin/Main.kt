import java.io.File

fun part1(file: File): Int {
    // sum time!
    var sum: Int = 0 // big enough??

    file.readLines().forEach { input ->
        val length = input.length
        if (length > 0) {
            var firstNum: Int? = null; var lastNum: Int? = null;
            var i = 0; var j = length - 1;
            while (true) {
                // if found both or done searching string, break
                if ((firstNum != null && lastNum != null) || i > j) {
                    break
                }

                // check if 'i' is digit
                if (firstNum == null) {
                    if (input[i].isDigit()) {
                        firstNum = input[i].digitToInt()
                    } else {
                        i++
                    }
                }

                // check if 'j' is digit
                if (lastNum == null) {
                    if (input[j].isDigit()) {
                        lastNum = input[j].digitToInt()
                    } else {
                        j--
                    }
                }
            }
            // calculate the digits, if not present
            sum +=
                (if (lastNum != null && firstNum != null)
                    "$firstNum$lastNum"
                else if (lastNum != null) "$lastNum$lastNum"
                else if (firstNum != null) "$firstNum$firstNum"
                else "0").toInt()
        }
    }

    /* with regex
    var sum2: Int = 0

    file.readLines().forEach {
        var input = it
        val re = Regex("[^0-9]")
        input = re.replace(input, "")

        if (input.isNotBlank()) {
            sum2 += ("${input[0]}${input[input.length-1]}".toInt())
        }
    }
    */

    return sum
}

fun part2(file: File): Int {
    // since letters finish and start each other, we cant blind replace 'one' with 1
    // ex: oneight = 18
    // oneeight -> 1eight -> 11 -> WRONG
    // so....
    val strMap: Map<String, String> = mapOf(
        "one" to "o1e",
        "two" to "t2o",
        "three" to "t3e",
        "four" to "f4r",
        "five" to "f5e",
        "six" to "s6x",
        "seven" to "s7n",
        "eight" to "e8t",
        "nine" to "n9e",
        "zero" to "z0o"
    )

    val inputs: List<String> = file.absoluteFile.readLines().map {
        var newInput = it
        strMap.forEach { (t: String, u: String) ->
            newInput = newInput.replace(t, u)
        }
        newInput
    }

    var sum: Int = 0

    inputs.forEach {
        var input = it
        val re = Regex("[^0-9]")
        input = re.replace(input, "")

        if (input.isNotBlank()) {
            sum += ("${input[0]}${input[input.length-1]}".toInt())
        }
    }

    return sum
}

fun main(args: Array<String>) {

    // get input file
    val file = File("./src/main/kotlin/input.txt").absoluteFile

    println("part 1 sum is ${part1(file)}")
    println("part 2 sum is ${part2(file)}")
}
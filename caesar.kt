import kotlin.system.exitProcess

/**
 * Checks if index is at the end of the string to be enciphered.
 * If not, the current index character is assessed against conditions and then the appropriate character is added to the result
 * The method then recursively calls itself
 *
 */
fun rotate(s: String, number: Int, index: Int, result: String): String {

    val upper:String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val lower:String = "abcdefghijklmnopqrstuvwxyz"
    var newResult: String = result

    if (index == s.length) {
        return result
    } else {

        if (!Character.isLetter(s.get(index))) {
            newResult += s.get(index)
        } else if (Character.isUpperCase(s.get(index))) {
            newResult += upper.get(((upper.indexOf(s.get(index))) + number) % upper.length)    //Gets the character in the alphabet sequence which is equal to character at the current index of String s added to the number. Modulus alphabet length to wrap around z-a.
            //result+=Character.toUpperCase(lower.charAt(((lower.indexOf(s.charAt(index)))+number)%lower.length()));
        } else {
            newResult += lower.get(((lower.indexOf(s.get(index))) + number) % lower.length)
        }

        return rotate(s, number, index + 1, newResult)

    }

}

/**
 *
 * Deciphers a String with the use of scrabble scores. Maps each letter to a score.
 * Loops through every possible letter shift.
 * Finds the sum of scrabble scores for the characters in that String.
 * Lowest score has a high possibility of being the correct word
 */
fun decipher(s: String): String {
    //try creating map with letters mapped to their scrabble score. Use the rotate with numbers 0-25. Test score and record the min score number.

    val scrabble = HashMap<Char, Int>() //Letters mapped to their scrabble scores
    scrabble.put('e', 1)
    scrabble.put('a', 1)
    scrabble.put('i', 1)
    scrabble.put('o', 1)
    scrabble.put('n', 1)
    scrabble.put('r', 1)
    scrabble.put('t', 1)
    scrabble.put('l', 1)
    scrabble.put('s', 1)
    scrabble.put('u', 1)
    scrabble.put('d', 2)
    scrabble.put('g', 2)
    scrabble.put('b', 3)
    scrabble.put('c', 3)
    scrabble.put('m', 3)
    scrabble.put('p', 3)
    scrabble.put('f', 4)
    scrabble.put('h', 4)
    scrabble.put('v', 4)
    scrabble.put('w', 4)
    scrabble.put('y', 4)
    scrabble.put('k', 5)
    scrabble.put('j', 8)
    scrabble.put('x', 8)
    scrabble.put('q', 10)
    scrabble.put('z', 10)

    var lowestScore = 1000 //arbritary number
    var shiftNumber = 0 //The shift number of the lowest scored rotation will be assigned to this variable

    for (i in 1..25) { //Loops through every possible shift number.

        val rotated = rotate(s, i, 0, "") //Creates the rotated version for the shifted number

        var score = 0    //Sets the scrabble score to zero
        for (j in 0 until rotated.length) { //Used to loop through every character in the rotated String

            if (j > 0 && rotated[j] == rotated[j - 1] && (rotated[j] == 'a' || rotated[j] == 'i' || rotated[j] == 'u')) { //These vowels are rarely if ever put next to each other in the English language but are low score and mean the wrong result is returned for small strings.
                score += 20
            }

            if (Character.isLetter(rotated[j])) { //If the character is a letter add its scrabble score
                // score += scrabble[Character.toLowerCase(rotated[j])]
                score+= scrabble.get(Character.toLowerCase(rotated.get(j)))!!
            }

        }

        if (score < lowestScore) { //The lowest scored rotated word should theoretically have the highest chance of being the correct String
            lowestScore = score
            shiftNumber = i
        }
    }


    return rotate(s, shiftNumber, 0, "")
}


/**
 *
 * Encipher function checks that the number is inside the appropriate range
 * Calls rotate with the number to return the enciphered string
 *
 */
fun encipher(s: String, n: Int): String {

    if (n < 0 || n > 25) {
        return "Number parameter must be between 0 and 25"
    }

    return rotate(s, n, 0, "")
}


fun main(args: Array<String>) {

    //For command line arguments
    /*try {

        when(args[0]){
            "encipher"-> println(encipher(args[1],args[2].toInt()))
            "decipher"->println(decipher(args[1]))
            else -> println("Invalid command")

        }
    }catch (Ex: Exception){
        println("Invalid arguments")
    }*/

    do {
        println("Decrypt any string first and then decipher the ciphertext after.")
        println("Enter decipher, encipher or exit")
        val command = readLine()
        when(command){
            "encipher"-> {
                println("Enter a string to encipher")
                val plainText = readLine()
                println("Enter a number from 0 to 25 to rotate by")
                try {
                    println(encipher(plainText!!, readLine()!!.toInt()))
                }catch (Ex: Exception){
                    println("Invalid inputs provided, restarting")
                }

            }


            "decipher" -> {
                println("Enter a string to decipher")
                val cipherText = readLine()
                try {
                    println(decipher(cipherText!!))
                }catch (Ex: Exception){
                    println("Invalid inputs provided, restarting")
                }
            }

            "exit" -> exitProcess(0)

            else -> println("Invalid command")

        }
    }while(true)

}
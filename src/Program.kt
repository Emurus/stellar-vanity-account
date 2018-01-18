import com.google.common.primitives.UnsignedLong
import org.stellar.sdk.KeyPair

// Number at which the program will report the number of tries
val reportNumber = 100000L

fun main(args: Array<String>) {

    //Stores the vanity string
    var vanity = ""

    // Validate input
    while(vanity == ""){
        println("Enter vanity string (I suggest 4 or less characters): ")
        vanity = readLine()!!.toUpperCase()
        if (!vanity.matches("[a-zA-Z0-9]*".toRegex())){
            println("Only use alphanumeric characters (letters and/or numbers)")
            vanity = ""
        }
    }

    // Let the user know the program has started running
    println("Running...")

    // Produce random key pairs until the vanity accountId is found
    var count = UnsignedLong.ONE
    var keyPair = KeyPair.random()
    while (!keyPair.accountId.endsWith(vanity)){
        keyPair = KeyPair.random()
        count += UnsignedLong.ONE
        if(count.mod(UnsignedLong.valueOf(reportNumber)) == UnsignedLong.ZERO){
            println("Num tries: $count")
        }
    }

    // Print result to the screen
    print("""Result:
Found Stellar keypair in $count tries
public key: ${keyPair.accountId}
secret key: ${keyPair.secretSeed.makeString()}""")
}

// Helper function for creating a string from a CharArray
fun CharArray.makeString() = fold(""){res, next -> res+next}
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String?.fromIsoToString(incomingPattern: String, outgoingPattern: String): String? {
    return try {
        this?.let {
            SimpleDateFormat(incomingPattern, Locale.getDefault()).parse(it)?.let {
                SimpleDateFormat(outgoingPattern, Locale.getDefault()).format(it)
            }
        }
    }catch (ex: ParseException){
        null
    }
}
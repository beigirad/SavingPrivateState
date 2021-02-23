package savingprivatestate.sample.util

import android.os.Bundle

fun Bundle?.toFormattedString(): CharSequence {
    if (this == null) return "null"
    return this.keySet().joinToString(separator = "\n") { key ->
        ">> $key = ${this.get(key)}}"
    }
}
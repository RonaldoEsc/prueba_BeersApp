package net.hrec.pruebatecnica.util

class UtilString {
    companion object {
        fun convertDoubleOrIntToString(num: Double?) : String {
            if ( num != null) {
                val convertText: String = if (num.toString().contains(".0")) {
                    num.toInt().toString()
                } else {
                    num.toString()
                }
                return convertText
            }
            return "0"
        }
    }
}
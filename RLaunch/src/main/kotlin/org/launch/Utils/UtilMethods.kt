package org.launch.Utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.text.DateFormat
import java.util.*


fun getTimeFromMillis(millis: Long): String? {
    return DateFormat.getDateTimeInstance().format(Date(millis))
}
fun getLogger(forClass: Class<*>): Logger =
    LoggerFactory.getLogger(forClass)
package com.epacheco.reports.compose_reformat.utils.extensions

import kotlinx.datetime.Instant

fun Instant.parseIsoDate(isoString: String): Instant? {
    return runCatching { Instant.parse(isoString) }.getOrNull()
}
package kr.co.beghsimulator.common.exception

import org.springframework.http.HttpStatus

open class CustomRuntimeException(
    message: String? = null,
    cause: Throwable? = null,
    val status: HttpStatus
) : RuntimeException(message, cause)
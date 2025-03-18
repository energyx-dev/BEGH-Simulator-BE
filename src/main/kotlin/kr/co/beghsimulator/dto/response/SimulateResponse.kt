package kr.co.beghsimulator.dto.response

import java.io.File

data class SimulateResponse(
    val file: String,
) {
    companion object {
        fun from(file: File): SimulateResponse {
            return SimulateResponse(
                file = file.absolutePath
            )
        }
    }
}

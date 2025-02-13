package kr.co.beghsimulator.dto.request

import jakarta.validation.constraints.NotBlank

data class ReadJsonRequest(
    @NotBlank
    val absolutePath: String
)

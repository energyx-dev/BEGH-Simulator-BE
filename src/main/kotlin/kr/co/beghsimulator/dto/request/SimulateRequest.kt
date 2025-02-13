package kr.co.beghsimulator.dto.request

import jakarta.validation.constraints.NotBlank

data class SimulateRequest(
    @NotBlank
    val absolutePath: String
)

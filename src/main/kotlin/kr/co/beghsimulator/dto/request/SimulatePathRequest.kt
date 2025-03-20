package kr.co.beghsimulator.dto.request

import jakarta.validation.constraints.NotBlank

data class SimulatePathRequest(
    @NotBlank
    val absolutePath: String
)

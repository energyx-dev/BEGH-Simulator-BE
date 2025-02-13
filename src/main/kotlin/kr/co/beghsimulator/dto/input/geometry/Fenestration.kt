package kr.co.beghsimulator.dto.input.geometry

import java.math.BigDecimal

data class Fenestration(
    val area: BigDecimal?,
    val construction: String?,
    val id: String?,
    val name: String?,
    val type: String?
)
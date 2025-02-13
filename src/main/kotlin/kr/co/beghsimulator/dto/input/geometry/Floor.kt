package kr.co.beghsimulator.dto.input.geometry

data class Floor(
    val floorHeight: Double?,
    val tag: String?,
    val zones: List<Zone?>?
)
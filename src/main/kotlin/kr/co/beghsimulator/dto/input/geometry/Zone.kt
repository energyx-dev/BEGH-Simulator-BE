package kr.co.beghsimulator.dto.input.geometry

data class Zone(
    val id: String?,
    val name: String?,
    val surfaces: List<Surface>?,
    val type: String?
)
package kr.co.beghsimulator.dto.request.building

data class Zone(
    val id: String?,
    val name: String?,
    val surfaces: List<Surface>?,
    val type: String?
)
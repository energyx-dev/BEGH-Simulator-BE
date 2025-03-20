package kr.co.beghsimulator.dto.request.building

data class Floor(
    val floorHeight: Double?,
    val tag: String?,
    val zones: List<Zone?>?
)
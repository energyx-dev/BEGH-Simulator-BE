package kr.co.beghsimulator.dto.request


data class SimulateRequest(
    val address: String?,
    val name: String?,
    val northAxis: Int?,
    val terrain: String?,
    val weather: String?,
    val building: BuildingRequest
)
package kr.co.beghsimulator.dto.request

import kr.co.beghsimulator.dto.request.building.Floor

data class BuildingRequest(
    val floors: List<Floor?>?,
    val name: String?,
    val northAxis: Int?,
    val numAbovegroundFloor: Int?,
    val numUndergroundFloor: Int?,
    val terrain: String?
)
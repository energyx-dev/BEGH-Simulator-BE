package kr.co.beghsimulator.dto.input

import kr.co.beghsimulator.dto.input.geometry.Floor

data class Geometry(
    val floors: List<Floor?>?,
    val name: String?,
    val northAxis: Int?,
    val numAbovegroundFloor: Int?,
    val numUndergroundFloor: Int?,
    val terrain: String?
)
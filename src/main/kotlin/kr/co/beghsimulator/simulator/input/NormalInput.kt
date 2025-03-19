package kr.co.beghsimulator.simulator.input

import kr.co.beghsimulator.dto.request.BuildingRequest
import kr.co.beghsimulator.simulator.input.building.Floor

data class NormalInput(
    val name: String?,
    val floors: List<Floor?>?
) {
    companion object {
        fun from(buildingRequest: BuildingRequest): NormalInput {
            return NormalInput(
                name = buildingRequest.name,
                floors = buildingRequest.floors
            )
        }
    }
}
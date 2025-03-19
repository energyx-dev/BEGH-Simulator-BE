package kr.co.beghsimulator.simulator.input

import kr.co.beghsimulator.dto.request.BuildingRequest

data class RemodelingInput(
    val name: String?,
    val terrain: String?
) {
    companion object {
        fun from(building: BuildingRequest): RemodelingInput {
            return RemodelingInput(
                name = building.name,
                terrain = building.terrain
            )
        }
    }
}

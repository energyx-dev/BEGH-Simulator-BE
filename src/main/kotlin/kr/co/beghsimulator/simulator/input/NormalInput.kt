package kr.co.beghsimulator.simulator.input

import kr.co.beghsimulator.dto.request.SimulateRequest
import kr.co.beghsimulator.dto.request.building.Floor

data class NormalInput(
    val name: String?,
    val floors: List<Floor?>?
) {
    companion object {
        fun from(request: SimulateRequest): NormalInput {
            return NormalInput(
                name = request.name,
                floors = request.building.floors
            )
        }
    }
}
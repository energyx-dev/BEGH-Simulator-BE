package kr.co.beghsimulator.simulator.input

import kr.co.beghsimulator.dto.request.SimulateRequest
import kr.co.beghsimulator.dto.request.building.Floor
import kr.co.beghsimulator.service.IInput

data class NormalInput(
    val name: String?,
    val floors: List<Floor?>?
) : IInput {
    companion object {
        fun from(request: SimulateRequest): NormalInput {
            return NormalInput(
                name = request.name,
                floors = request.building.floors
            )
        }
    }
}
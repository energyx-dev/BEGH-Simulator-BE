package kr.co.beghsimulator.simulator.input

import kr.co.beghsimulator.dto.request.SimulateRequest
import kr.co.beghsimulator.service.IInput

data class RemodelingInput(
    val name: String?,
    val terrain: String?
) : IInput{
    companion object {
        fun from(request: SimulateRequest): RemodelingInput {
            return RemodelingInput(
                name = request.name,
                terrain = request.terrain
            )
        }
    }
}

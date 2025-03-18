package kr.co.beghsimulator.dto.response

import kr.co.beghsimulator.simulator.output.Cost
import kr.co.beghsimulator.simulator.output.DGBuilding
import kr.co.beghsimulator.simulator.output.GreenRemodeling

data class SimulateResult(
    val result: DGBuilding,
    val path: String,
    val cost: Cost
) {
    companion object {
        fun from(gr: GreenRemodeling, cost: Cost): SimulateResult {
            return SimulateResult(
                result = gr.result,
                path = gr.path,
                cost = cost
            )
        }
    }
}

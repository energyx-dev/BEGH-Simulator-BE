package kr.co.beghsimulator.dto.response

import kr.co.beghsimulator.simulator.output.Cost
import kr.co.beghsimulator.simulator.output.DGBuilding
import kr.co.beghsimulator.simulator.output.GreenRemodeling
import kr.co.beghsimulator.simulator.output.IOutput

data class SimulateResult(
    val result: DGBuilding,
    val path: String,
    val cost: Cost
) {
    companion object {
        fun from(results: List<IOutput>): SimulateResult {

            var gr: GreenRemodeling? = null
            var cost: Cost? = null

            results.forEach { output ->
                when (output) {
                    is GreenRemodeling -> gr = output
                    is Cost -> cost = output
                }
            }

            gr ?: throw IllegalArgumentException("GreenRemodeling 결과가 필요합니다.")
            cost ?: throw IllegalArgumentException("Cost 결과가 필요합니다.")

            return SimulateResult(
                result = gr!!.result,
                path = gr!!.path,
                cost = cost!!
            )
        }
    }
}

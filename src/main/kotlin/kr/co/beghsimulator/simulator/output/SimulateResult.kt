package kr.co.beghsimulator.simulator.output

import kr.co.beghsimulator.service.IOutput

data class SimulateResult(
    val building: BuildingOutput,
    val cost: CostOutput
) {
    companion object {
        fun from(results: List<IOutput>): SimulateResult {
            val building = results.filterIsInstance<BuildingOutput>().singleOrNull()
                ?: throw IllegalArgumentException("EP Launcher 결과가 필요합니다.")

            val cost = results.filterIsInstance<CostOutput>().singleOrNull()
                ?: throw IllegalArgumentException("Cost 결과가 필요합니다.")

            return SimulateResult(
                building = building,
                cost = cost
            )
        }
    }
}
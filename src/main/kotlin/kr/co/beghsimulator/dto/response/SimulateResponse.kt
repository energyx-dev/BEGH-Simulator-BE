package kr.co.beghsimulator.dto.response

import kr.co.beghsimulator.simulator.output.DGBuilding

data class SimulateResponse(
    val results: List<DGBuilding>,
    val resultFiles: List<String>
) {
    companion object {
        fun from(results: List<DGBuilding>, filePaths: List<String>): SimulateResponse {
            return SimulateResponse(
                results = results,
                resultFiles = filePaths
            )
        }
    }
}

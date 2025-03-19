package kr.co.beghsimulator.simulator.output

import kr.co.beghsimulator.service.IOutput

data class BuildingOutput(
    val result: List<Building>,
    val path: List<String>,
) : IOutput {
    companion object {
        fun from(results: List<Building>, paths: List<String>): BuildingOutput {
            return BuildingOutput(
                result = results,
                path = paths
            )
        }
    }
}
package kr.co.beghsimulator.simulator.output

data class GreenRemodeling(
    val result: DGBuilding,
    val path: String,
) {
    companion object {
        fun from(results: List<DGBuilding>, paths: List<String>): GreenRemodeling {
            return GreenRemodeling(
                result = results[0],
                path = paths[0]
            )
        }
    }
}
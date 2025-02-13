package kr.co.beghsimulator.dto.response

import kr.co.beghsimulator.dto.output.DGBuilding
import java.io.File

data class SimulateResponse(
    val result: DGBuilding,
    val resultFile: String,
) {
    companion object {
        fun from(result: DGBuilding, file: File): SimulateResponse {
            return SimulateResponse(
                result = result,
                resultFile = file.absolutePath
            )
        }
    }
}

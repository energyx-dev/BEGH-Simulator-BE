package kr.co.beghsimulator.service

import kr.co.beghsimulator.dto.request.SimulateRequest
import kr.co.beghsimulator.simulator.input.Geometry
import kr.co.beghsimulator.simulator.output.DGBuilding
import kr.co.beghsimulator.dto.response.SimulateResponse
import kr.co.beghsimulator.simulator.Simulator
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.io.File

@Service
class SimulateService(
    private val simulator: Simulator,
    private val fileService: FileService,
) {

    private val log = KotlinLogging.logger { }

    fun simulate(request: SimulateRequest) : SimulateResponse {
        val data: Geometry = fileService.readFile(request.absolutePath, Geometry::class.java)

        val resultFile = simulator.execute(data)
        val resultData = fileService.readFile(resultFile, DGBuilding::class.java)

        return SimulateResponse.from(resultData, resultFile)
    }
}
package kr.co.beghsimulator.service

import kr.co.beghsimulator.dto.request.SimulateRequest
import kr.co.beghsimulator.simulator.input.Geometry
import kr.co.beghsimulator.dto.response.SimulateResponse
import kr.co.beghsimulator.simulator.Simulator
import kr.co.beghsimulator.simulator.output.DGBuilding
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class SimulateService(
    private val simulator: Simulator,
    private val fileService: FileService,
) {
    private val log = KotlinLogging.logger { }

    fun simulate(request: SimulateRequest): SimulateResponse {
        val data: Geometry = fileService.readFile(request.absolutePath, Geometry::class.java)

        val resultFilePaths: List<String> = simulator.execute(data)

        return analyze(resultFilePaths)
    }

    private fun analyze(paths: List<String>): SimulateResponse {
        val results = mutableListOf<DGBuilding>()

        paths.forEach { path ->
            val result: DGBuilding = fileService.readFile(path, DGBuilding::class.java)
            results.add(result)
        }

        return SimulateResponse.from(results, paths)
    }
}
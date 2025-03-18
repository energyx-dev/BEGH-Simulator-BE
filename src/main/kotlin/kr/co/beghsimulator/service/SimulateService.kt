package kr.co.beghsimulator.service

import kr.co.beghsimulator.dto.request.SimulateRequest
import kr.co.beghsimulator.dto.response.SimulateResponse
import kr.co.beghsimulator.simulator.input.Geometry
import kr.co.beghsimulator.dto.response.SimulateResult
import kr.co.beghsimulator.simulator.ISimulator
import kr.co.beghsimulator.simulator.output.IOutput
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class SimulateService(
    private val fileService: FileService,
    private val simulators: List<ISimulator>
) {
    private val log = KotlinLogging.logger { }

    fun simulate(request: SimulateRequest): SimulateResponse {
        val data: Geometry = fileService.readFile(request.absolutePath, Geometry::class.java)

        val results: List<IOutput> = simulators.map { simulator ->
            simulator.execute(data)
        }

        val result = SimulateResult.from(results)
        val resultFile = fileService.writeFile(result)

        return SimulateResponse.from(resultFile)
    }
}
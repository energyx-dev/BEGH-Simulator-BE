package kr.co.beghsimulator.service

import kr.co.beghsimulator.dto.request.SimulateRequest
import kr.co.beghsimulator.dto.response.SimulateResponse
import kr.co.beghsimulator.simulator.input.Geometry
import kr.co.beghsimulator.dto.response.SimulateResult
import kr.co.beghsimulator.simulator.ISimulator
import kr.co.beghsimulator.simulator.output.Cost
import kr.co.beghsimulator.simulator.output.GreenRemodeling
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class SimulateService(
    private val fileService: FileService,
    private val remodelingSimulator: ISimulator<GreenRemodeling>,
    private val costSimulator: ISimulator<Cost>
) {
    private val log = KotlinLogging.logger { }

    fun simulate(request: SimulateRequest): SimulateResponse {
        val data: Geometry = fileService.readFile(request.absolutePath, Geometry::class.java)

        val grResult: GreenRemodeling = remodelingSimulator.execute(data)
        val costResult: Cost = costSimulator.execute(data)

        val result = SimulateResult.from(grResult, costResult)
        val resultFile = fileService.writeFile(result)

        return SimulateResponse.from(resultFile)
    }
}
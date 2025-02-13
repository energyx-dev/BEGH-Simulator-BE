package kr.co.beghsimulator.service

import kr.co.beghsimulator.dto.request.SimulateRequest
import kr.co.beghsimulator.dto.input.Geometry
import kr.co.beghsimulator.dto.output.DGBuilding
import kr.co.beghsimulator.dto.response.SimulateResponse
import kr.co.beghsimulator.repository.SimulateRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class SimulateService(
    private val simulateRepository: SimulateRepository,
    private val fileService: FileService,
) {

    private val log = KotlinLogging.logger { }

    fun simulateJson(request: SimulateRequest) : SimulateResponse {
        val data: Geometry = fileService.readFile(request.absolutePath)

        val result: DGBuilding = simulateRepository.simulate(data)

        val file = fileService.writeFile(result)

        return SimulateResponse.from(result, file)
    }
}
package kr.co.beghsimulator.service

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kr.co.beghsimulator.dto.request.SimulateRequest
import kr.co.beghsimulator.dto.response.SimulateResponse
import kr.co.beghsimulator.dto.request.BuildingRequest
import kr.co.beghsimulator.simulator.output.SimulateResult
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class SimulateService(
    private val fileService: FileService,
    private val simulators: List<ISimulator>
) {
    private val log = KotlinLogging.logger { }

    fun execute(request: SimulateRequest): SimulateResponse = runBlocking {
        val data: BuildingRequest = fileService.readFile(request.absolutePath, BuildingRequest::class.java)

        val simulateResults: List<IOutput> = simulateAll(data)
        val result: SimulateResult = SimulateResult.from(simulateResults)

        val resultFile = fileService.writeFile(result)
        return@runBlocking SimulateResponse.from(resultFile)
    }

    private suspend fun simulateAll(data: BuildingRequest): List<IOutput> {
        return runBlocking {
            simulators.map { simulator ->
                async {
                    simulator.execute(data)
                }
            }
        }.awaitAll()
    }
}
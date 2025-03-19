package kr.co.beghsimulator.service

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
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

    fun simulate(request: SimulateRequest): SimulateResponse = runBlocking {
        val data: Geometry = fileService.readFile(request.absolutePath, Geometry::class.java)

        val results: List<IOutput> = runBlocking {
            simulators.map { simulator ->
                async {
                    simulator.execute(data)
                }
            }
        }.awaitAll()

        val result = SimulateResult.from(results)
        val resultFile = fileService.writeFile(result)

        return@runBlocking SimulateResponse.from(resultFile)
    }
}
package kr.co.beghsimulator.simulator

import kr.co.beghsimulator.dto.request.SimulateRequest
import kr.co.beghsimulator.service.FileService
import kr.co.beghsimulator.service.ISimulator
import kr.co.beghsimulator.service.ProcessService
import kr.co.beghsimulator.simulator.input.NormalInput
import kr.co.beghsimulator.simulator.input.RemodelingInput
import kr.co.beghsimulator.simulator.output.Building
import kr.co.beghsimulator.simulator.output.BuildingOutput
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class RemodelingSimulator(
    val fileService: FileService,
    val processService: ProcessService
) : ISimulator {
    private val log = KotlinLogging.logger { }

    override fun execute(request: SimulateRequest): BuildingOutput {
        val processBuilders: List<ProcessBuilder> = getRequestProcessBuilders(request)

        val results: List<String> = processService.executeAll(processBuilders)

        return analyze(results)
    }

    private fun getRequestProcessBuilders(request: SimulateRequest): List<ProcessBuilder> {
        return processService.getProcessBuilders(
            inputs = listOf(
                NormalInput.from(request),
                RemodelingInput.from(request)
            )
        )
    }

    private fun analyze(paths: List<String>): BuildingOutput {
        val results = mutableListOf<Building>()

        paths.forEach { path ->
            val result: Building = fileService.readFile(path, Building::class.java)
            results.add(result)
        }

        return BuildingOutput.from(results, paths)
    }
}
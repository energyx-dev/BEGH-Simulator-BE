package kr.co.beghsimulator.simulator

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kr.co.beghsimulator.dto.request.SimulateRequest
import kr.co.beghsimulator.service.FileService
import kr.co.beghsimulator.service.ISimulator
import kr.co.beghsimulator.simulator.output.Building
import kr.co.beghsimulator.simulator.output.BuildingOutput
import kr.co.beghsimulator.common.util.PythonUtil
import kr.co.beghsimulator.simulator.Remodeling.RemodelingSimulatorInput
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.io.File

@Component
class RemodelingSimulator(
    private val fileService: FileService,
    private val remodelingSimulatorInput: RemodelingSimulatorInput
) : ISimulator {
    private val log = KotlinLogging.logger { }

    override fun execute(request: SimulateRequest): BuildingOutput {
        val simulatorInputs = remodelingSimulatorInput.getInputs(request)

        val outputs: List<String> = executeSimulator(simulatorInputs, getSimulatorScript())

        return analyze(outputs)
    }

    private fun executeSimulator(inputs: List<String>, script: String): List<String> {
        return runBlocking {
            inputs.map { input ->
                async(Dispatchers.IO) {
                    PythonUtil.execute(script = script, input = input)
                }
            }.awaitAll()
        }
    }

    private fun getSimulatorScript(): String {
        val curDir = System.getProperty("user.dir")
        val scriptDir = "$curDir/python/simulator.py"
        return scriptDir.replace('/', File.separatorChar)
    }

    private fun analyze(outputs: List<String>): BuildingOutput {
        val results = mutableListOf<Building>()

        outputs.forEach { path ->
            val result: Building = fileService.readFile(path, Building::class.java)
            results.add(result)
        }

        return BuildingOutput.from(results, outputs)
    }
}
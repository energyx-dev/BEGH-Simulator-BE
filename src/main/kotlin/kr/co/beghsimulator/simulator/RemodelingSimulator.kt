package kr.co.beghsimulator.simulator

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kr.co.beghsimulator.dto.request.SimulateRequest
import kr.co.beghsimulator.service.FileService
import kr.co.beghsimulator.service.IInput
import kr.co.beghsimulator.service.ISimulator
import kr.co.beghsimulator.simulator.input.NormalInput
import kr.co.beghsimulator.simulator.input.RemodelingInput
import kr.co.beghsimulator.simulator.output.Building
import kr.co.beghsimulator.simulator.output.BuildingOutput
import kr.co.beghsimulator.simulator.util.PythonUtil
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.io.File

@Component
class RemodelingSimulator(
    val fileService: FileService
) : ISimulator {
    private val log = KotlinLogging.logger { }

    override fun execute(request: SimulateRequest): BuildingOutput {
        val inputs: List<IInput> = getInputs(request)

        val inputFilePaths: List<String> = saveInputs(inputs)

        val outputFilePaths: List<String> = executeSimulator(inputFilePaths, getSimulatorScript())

        return analyze(outputFilePaths)
    }

    private fun executeSimulator(inputFilePaths: List<String>, script: String): List<String> {
        return runBlocking {
            inputFilePaths.map { inputFile ->
                async(Dispatchers.IO) {
                    PythonUtil.execute(script = script, input = inputFile)
                }
            }.awaitAll()
        }
    }

    private fun getInputs(request: SimulateRequest): List<IInput> {
        return listOf(
            NormalInput.from(request),
            RemodelingInput.from(request)
        )
    }

    private fun saveInputs(inputs: List<IInput>): List<String> {
        return inputs.map { input -> fileService.writeFile(input).absolutePath }
    }

    private fun getSimulatorScript(): String {
        val curDir = System.getProperty("user.dir")
        val scriptDir = "$curDir/python/simulator.py"
        return scriptDir.replace('/', File.separatorChar)
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
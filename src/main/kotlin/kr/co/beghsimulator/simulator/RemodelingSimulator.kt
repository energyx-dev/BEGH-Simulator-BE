package kr.co.beghsimulator.simulator

import kotlinx.coroutines.*
import kr.co.beghsimulator.service.FileService
import kr.co.beghsimulator.service.ISimulator
import kr.co.beghsimulator.dto.request.BuildingRequest
import kr.co.beghsimulator.simulator.input.NormalInput
import kr.co.beghsimulator.simulator.input.RemodelingInput
import kr.co.beghsimulator.simulator.output.Building
import kr.co.beghsimulator.simulator.output.BuildingOutput
import kr.co.beghsimulator.simulator.util.PythonUtil
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

@Component
class RemodelingSimulator(
    val fileService: FileService
) : ISimulator {
    private val log = KotlinLogging.logger { }

    override fun execute(data: BuildingRequest): BuildingOutput {
        val result = executePython(data)
        return analyze(result)
    }

    private fun analyze(paths: List<String>): BuildingOutput {
        val results = mutableListOf<Building>()

        paths.forEach { path ->
            val result: Building = fileService.readFile(path, Building::class.java)
            results.add(result)
        }

        return BuildingOutput.from(results, paths)
    }

    private fun executePython(buildingRequest: BuildingRequest): List<String> = runBlocking {
        val processBuilders: List<ProcessBuilder> = setProcessBuilders(buildingRequest)

        return@runBlocking processBuilders.map { processBuilder ->
            async(Dispatchers.IO) { executePython(processBuilder) }
        }.awaitAll()
    }

    private fun setProcessBuilders(data: BuildingRequest): List<ProcessBuilder> {
        val python: String = PythonUtil.getPython()
        val script: String = PythonUtil.getScript()

        return listOf(
            setProcessBuilder(
                data = NormalInput.from(data),
                python = python,
                script = script
            ),
            setProcessBuilder(
                data = RemodelingInput.from(data),
                python = python,
                script = script
            )
        )
    }

    private fun <T> setProcessBuilder(data: T, python: String, script: String): ProcessBuilder {
        val tmpFile: File = fileService.writeTmpFile(data)
        log.info { "file : ${tmpFile.absolutePath}" }

        return ProcessBuilder(python, script, tmpFile.absolutePath)
            .redirectErrorStream(true)
    }

    private suspend fun executePython(processBuilder: ProcessBuilder): String {
        var resultFilePath: String? = null

        withContext(Dispatchers.IO) {
            val process = processBuilder.start()

            BufferedReader(InputStreamReader(process.inputStream, "UTF-8")).use { reader ->
                reader.forEachLine { line ->
                    resultFilePath = line
                }
            }

            val exitCode: Int = process.waitFor()
            when (exitCode) {
                0 -> println("Python 실행 완료")
                else -> throw RuntimeException("Python 실행 중 오류 발생 (코드: $exitCode)")
            }
        }

        return resultFilePath ?: throw RuntimeException("Python 실행 결과 오류")
    }
}
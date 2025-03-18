package kr.co.beghsimulator.simulator

import kotlinx.coroutines.*
import kr.co.beghsimulator.service.FileService
import kr.co.beghsimulator.simulator.input.Geometry
import kr.co.beghsimulator.simulator.output.DGBuilding
import kr.co.beghsimulator.simulator.output.GreenRemodeling
import kr.co.beghsimulator.simulator.util.PythonUtil
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

@Component
class RemodelingSimulator(
    val fileService: FileService
) : ISimulator<GreenRemodeling> {
    private val log = KotlinLogging.logger { }

    override fun execute(data: Geometry): GreenRemodeling {
        val result = executePython(data)
        return analyze(result)
    }

    private fun analyze(paths: List<String>): GreenRemodeling {
        val results = mutableListOf<DGBuilding>()

        paths.forEach { path ->
            val result: DGBuilding = fileService.readFile(path, DGBuilding::class.java)
            results.add(result)
        }

        return GreenRemodeling.from(results, paths)
    }

    private fun executePython(geometry: Geometry): List<String> = runBlocking {
        val python: String = PythonUtil.getPython()
        val script: String = PythonUtil.getScript()

        val processBuilders: List<ProcessBuilder> = listOf(
            setProcessBuilder(geometry, python, script),
            setProcessBuilder(geometry, python, script),
            setProcessBuilder(geometry, python, script)
        )

        val deferredResults: List<Deferred<String>> = processBuilders.map { processBuilder ->
            async(Dispatchers.IO) { executePython(processBuilder) }
        }

        deferredResults.awaitAll()
    }

    private fun setProcessBuilder(data: Geometry, pythonDir: String, pythonScript: String): ProcessBuilder {
        val tmpFile: File = fileService.writeTmpFile(data)
        log.info { "file : ${tmpFile.absolutePath}" }

        return ProcessBuilder(pythonDir, pythonScript, tmpFile.absolutePath)
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
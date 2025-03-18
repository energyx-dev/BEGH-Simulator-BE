package kr.co.beghsimulator.simulator

import kotlinx.coroutines.*
import kr.co.beghsimulator.service.FileService
import kr.co.beghsimulator.simulator.enums.OS
import kr.co.beghsimulator.simulator.input.Geometry
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


@Component
class Simulator (
  val fileService: FileService
) {
    private val log = KotlinLogging.logger { }

    fun execute(geometry: Geometry): List<String> = runBlocking {
        val processBuilders: List<ProcessBuilder> = listOf(
            setProcessBuilder(geometry),
            setProcessBuilder(geometry),
            setProcessBuilder(geometry)
        )

        val deferredResults: List<Deferred<String>> = processBuilders.map { processBuilder ->
            async(Dispatchers.IO) { executePython(processBuilder) }
        }

        deferredResults.awaitAll()
    }

    private fun setProcessBuilder(data: Geometry): ProcessBuilder {
        val tmpFile: File = fileService.writeTmpFile(data)
        val pythonDir: String = OS.getPythonDir()
        val pythonScript: String = OS.getPythonScript()

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
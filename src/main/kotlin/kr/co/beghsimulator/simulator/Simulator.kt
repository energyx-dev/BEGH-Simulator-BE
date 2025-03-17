package kr.co.beghsimulator.simulator

import kotlinx.coroutines.*
import kr.co.beghsimulator.simulator.enums.OS
import kr.co.beghsimulator.simulator.input.Geometry
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


@Component
class Simulator {

    private val log = KotlinLogging.logger { }

    fun execute(geometry: Geometry): File {

        try {
            val processBuilder: ProcessBuilder = setProcessBuilder()

            val resultFilePath: String = runBlocking { executePython(processBuilder) }

            return File(resultFilePath)
        } catch (e: Exception) {
            throw RuntimeException("python 실행 오류", e)
        }
    }

    private fun setProcessBuilder(): ProcessBuilder {
        val curDir: String = System.getProperty("user.dir")
        val os = OS.getOS()

        val pythonDir: String = when(os) {
            OS.WIN -> "$curDir\\python\\python3\\python.exe"
            OS.MAC -> "python3"
        }

        val pythonScript: String = when(os) {
            OS.WIN -> "$curDir\\python\\simulator.py"
            OS.MAC -> "$curDir/python/simulator.py"
        }

        log.info { "dir : $pythonDir" }
        log.info { "script : $pythonScript" }

        return ProcessBuilder(pythonDir, pythonScript)
            .redirectErrorStream(true)
    }

    private suspend fun executePython(processBuilder: ProcessBuilder): String {
        var resultFilePath: String? = null

        withContext(Dispatchers.IO) {
            val process = processBuilder.start()

            BufferedReader(InputStreamReader(process.inputStream, "UTF-8")).use { reader ->
                reader.forEachLine { line -> resultFilePath = line }
            }

            val exitCode: Int = process.waitFor()
            when (exitCode) {
                0 -> println("Python 실행 완료")
                else -> throw RuntimeException("Python 실행 중 오류 발생 (코드: $exitCode)")
            }
        }

        return resultFilePath ?: throw RuntimeException("Python 실행 결과가 없음")
    }
}
package kr.co.beghsimulator.service

import kotlinx.coroutines.*
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

@Service
class ProcessService(
    private val fileService: FileService
) {
    val log = KotlinLogging.logger { }

    fun getProcessBuilders(inputs: List<Any>, python: String, script: String): List<ProcessBuilder> {
        return inputs.map { input ->
            setProcessBuilder(input, python, script)
        }
    }

    fun <T> setProcessBuilder(input: T, python: String, script: String,): ProcessBuilder {
        val tmpFile: File = fileService.writeTmpFile(input)
        log.info { "file : ${tmpFile.absolutePath}" }

        return ProcessBuilder(python, script, tmpFile.absolutePath)
            .redirectErrorStream(true)
    }

    fun executeAll(processBuilders: List<ProcessBuilder>): List<String> = runBlocking {
        processBuilders.map { processBuilder ->
            async(Dispatchers.IO) { execute(processBuilder) }
        }.awaitAll()
    }

    private suspend fun execute(processBuilder: ProcessBuilder): String {
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
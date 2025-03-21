package kr.co.beghsimulator.service

import kotlinx.coroutines.*
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader

@Service
class ProcessService(

) {
    val log = KotlinLogging.logger { }

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
                0 -> log.info { "프로세스 실행 완료" }
                else -> throw RuntimeException("프로세스 실행 중 오류 발생 (코드: $exitCode)")
            }
        }

        return resultFilePath ?: throw RuntimeException("프로세스 실행 결과 오류")
    }
}
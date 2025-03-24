package kr.co.beghsimulator.simulator.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import java.io.BufferedReader
import java.io.InputStreamReader

object ProcessUtil {
    private val log = KotlinLogging.logger { }

    suspend fun execute(vararg commend: String): String {
        val processBuilder = ProcessBuilder(*commend)

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
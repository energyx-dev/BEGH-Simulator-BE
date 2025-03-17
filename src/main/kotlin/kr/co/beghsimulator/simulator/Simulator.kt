package kr.co.beghsimulator.simulator

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
            val processBuilder = setProcessBuilder(getOsType())

            val resultFilePath = executePython(processBuilder)

            return File(resultFilePath)
        } catch (e: Exception) {
            throw RuntimeException("python 실행 오류", e)
        }
    }


    fun getOsType(): OS {
        val osName = System.getProperty("os.name").lowercase()
        when {
            osName.contains("win") -> return OS.WIN
            osName.contains("mac") -> return OS.MAC
            else -> throw RuntimeException("win 배포 환경, mac 개발 환경에서만 실행이 가능합니다.")
        }
    }

    fun setProcessBuilder(os: OS): ProcessBuilder {
        val curDir: String = System.getProperty("user.dir")

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

    fun executePython(processBuilder: ProcessBuilder) : String {
        val process = processBuilder.start()

        val reader = BufferedReader(InputStreamReader(process.inputStream, "UTF-8"))
        lateinit var resultFilePath: String

        while (true) {
            val line = reader.readLine() ?: break
            resultFilePath = line
        }

        val exitCode: Int = process.waitFor()
        when (exitCode) {
            0 -> println("Python 실행 완료")
            else -> throw RuntimeException("Python 실행 중 오류 발생 (코드: $exitCode)")
        }

        return resultFilePath
    }
}
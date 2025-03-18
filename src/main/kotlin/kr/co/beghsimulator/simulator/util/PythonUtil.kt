package kr.co.beghsimulator.simulator.util

import java.io.File

object PythonUtil {
    fun getPython(): String {
        val curDir = System.getProperty("user.dir")
        val osName = System.getProperty("os.name").lowercase()

        if (osName.contains("win")) {
            return "$curDir\\python\\python3\\python.exe"
        }

        if (osName.contains("mac")) {
            return "python3"
        }

        throw RuntimeException("win 배포 환경, mac 개발 환경에서만 실행이 가능합니다.")
    }

    fun getScript(): String {
        val curDir = System.getProperty("user.dir")
        val dir = "$curDir/python/simulator.py"
        return dir.replace('/', File.separatorChar)
    }
}
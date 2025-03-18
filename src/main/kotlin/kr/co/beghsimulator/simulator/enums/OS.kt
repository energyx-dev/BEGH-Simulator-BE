package kr.co.beghsimulator.simulator.enums

import java.io.File

enum class OS {
    WIN,
    MAC;

    companion object {
        fun getOS(): OS {
            val osName = System.getProperty("os.name").lowercase()
            return when {
                osName.contains("win") -> WIN
                osName.contains("mac") -> MAC
                else -> throw RuntimeException("win 배포 환경, mac 개발 환경에서만 실행이 가능합니다.")
            }
        }

        fun getPythonDir(): String {
            val curDir = System.getProperty("user.dir")
            return when (getOS()) {
                WIN -> "$curDir\\python\\python3\\python.exe"
                MAC -> "python3"
            }
        }

        fun getPythonScript(): String {
            val curDir = System.getProperty("user.dir")
            val dir = "$curDir/python/simulator.py"
            return dir.replace('/', File.separatorChar)
        }
    }
}
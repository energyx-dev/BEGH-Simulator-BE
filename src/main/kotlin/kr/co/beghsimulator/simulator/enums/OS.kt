package kr.co.beghsimulator.simulator.enums

enum class OS {
    WIN,
    MAC;

    companion object {
        fun getOS(): OS {
            val osName = System.getProperty("os.name").lowercase()
            when {
                osName.contains("win") -> return WIN
                osName.contains("mac") -> return MAC
                else -> throw RuntimeException("win 배포 환경, mac 개발 환경에서만 실행이 가능합니다.")
            }
        }
    }
}
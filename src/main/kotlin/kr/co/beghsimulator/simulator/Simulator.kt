package kr.co.beghsimulator.simulator

import kr.co.beghsimulator.simulator.input.Geometry
import kr.co.beghsimulator.simulator.output.DGBuilding
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class Simulator {

    private val log = KotlinLogging.logger { }

    fun execute(geometry: Geometry) : DGBuilding {
        val result = DGBuilding(
            name = "Test Building",
            northAxis = 0,
            weather = "seoul",
            address = "서울특별시 관악구 관악로 1",
            terrain = "city"
        )

        log.info { "simulate result : ${result}" }
        return result
    }
}
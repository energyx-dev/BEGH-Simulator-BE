package kr.co.beghsimulator.repository

import kr.co.beghsimulator.dto.input.Geometry
import kr.co.beghsimulator.dto.output.DGBuilding
import mu.KotlinLogging
import org.springframework.stereotype.Repository

@Repository
class SimulateRepository {

    private val log = KotlinLogging.logger { }

    fun simulate(geometry: Geometry) : DGBuilding {
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
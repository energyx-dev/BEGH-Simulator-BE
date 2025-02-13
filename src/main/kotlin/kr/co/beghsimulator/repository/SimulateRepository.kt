package kr.co.beghsimulator.repository

import kr.co.beghsimulator.dto.input.Geometry
import mu.KotlinLogging
import org.springframework.stereotype.Repository

@Repository
class SimulateRepository {

    private val log = KotlinLogging.logger { }

    fun simulate(geometry: Geometry) {
        log.info { "simulate complete : ${geometry}" }
    }
}
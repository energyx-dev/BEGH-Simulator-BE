package kr.co.beghsimulator.controller

import jakarta.validation.Valid
import kr.co.beghsimulator.simulator.output.DGBuilding
import kr.co.beghsimulator.dto.request.SimulateRequest
import kr.co.beghsimulator.dto.response.SimulateResponse
import kr.co.beghsimulator.service.SimulateService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/simulate")
class SimulateController(
    private val simulateService: SimulateService
) {
    @PostMapping
    fun simulate(@Valid request: SimulateRequest) : ResponseEntity<SimulateResponse> {
        val result = simulateService.simulate(request)

        return ResponseEntity.ok(result)
    }
}
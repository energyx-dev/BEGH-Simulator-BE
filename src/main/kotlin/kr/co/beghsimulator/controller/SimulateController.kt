package kr.co.beghsimulator.controller

import jakarta.validation.Valid
import kr.co.beghsimulator.dto.request.SimulatePathRequest
import kr.co.beghsimulator.dto.response.SimulateResponse
import kr.co.beghsimulator.service.SimulateService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/simulate")
class SimulateController(
    private val simulateService: SimulateService
) {
    @PostMapping
    fun simulate(@Valid request: SimulatePathRequest) : ResponseEntity<SimulateResponse> {
        val result = simulateService.execute(request)

        return ResponseEntity.ok(result)
    }
}
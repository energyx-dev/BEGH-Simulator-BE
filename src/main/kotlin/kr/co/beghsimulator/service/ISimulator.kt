package kr.co.beghsimulator.service

import kr.co.beghsimulator.dto.request.SimulateRequest

interface ISimulator {
    fun execute(request: SimulateRequest) : IOutput
}
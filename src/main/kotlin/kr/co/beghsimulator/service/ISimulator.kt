package kr.co.beghsimulator.service

import kr.co.beghsimulator.dto.request.BuildingRequest

interface ISimulator {
    fun execute(data: BuildingRequest) : IOutput
}
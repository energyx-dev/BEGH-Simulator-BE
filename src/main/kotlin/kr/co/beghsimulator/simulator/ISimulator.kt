package kr.co.beghsimulator.simulator

import kr.co.beghsimulator.simulator.input.Geometry
import kr.co.beghsimulator.simulator.output.IOutput

interface ISimulator {
    fun execute(data: Geometry) : IOutput
}
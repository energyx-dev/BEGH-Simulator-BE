package kr.co.beghsimulator.simulator

import kr.co.beghsimulator.simulator.input.Geometry

interface ISimulator<T> {
    fun execute(data: Geometry) : T
}
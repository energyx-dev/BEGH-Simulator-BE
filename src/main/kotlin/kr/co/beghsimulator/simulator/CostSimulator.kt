package kr.co.beghsimulator.simulator

import kr.co.beghsimulator.simulator.input.Geometry
import kr.co.beghsimulator.simulator.output.Cost
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class CostSimulator (

) : ISimulator {
    override fun execute(data: Geometry): Cost {
        return Cost.from(BigDecimal.TEN, BigDecimal.ONE)
    }
}
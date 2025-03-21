package kr.co.beghsimulator.simulator

import kr.co.beghsimulator.dto.request.SimulateRequest
import kr.co.beghsimulator.service.ISimulator
import kr.co.beghsimulator.simulator.output.CostOutput
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class CostSimulator (

) : ISimulator {
    override fun execute(request: SimulateRequest): CostOutput {
        return CostOutput.from(BigDecimal.TEN, BigDecimal.ONE)
    }
}
package kr.co.beghsimulator.simulator

import kr.co.beghsimulator.service.ISimulator
import kr.co.beghsimulator.dto.request.BuildingRequest
import kr.co.beghsimulator.simulator.output.CostOutput
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class CostSimulator (

) : ISimulator {
    override fun execute(data: BuildingRequest): CostOutput {
        return CostOutput.from(BigDecimal.TEN, BigDecimal.ONE)
    }
}
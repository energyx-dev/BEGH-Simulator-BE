package kr.co.beghsimulator.simulator.output

import kr.co.beghsimulator.service.IOutput
import java.math.BigDecimal

data class CostOutput(
    val before: BigDecimal,
    val after: BigDecimal,
) : IOutput {
    companion object {
        fun from(before: BigDecimal, after: BigDecimal): CostOutput {
            return CostOutput(
                before = before,
                after = after
            )
        }
    }
}
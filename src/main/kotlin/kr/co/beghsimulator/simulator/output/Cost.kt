package kr.co.beghsimulator.simulator.output

import java.math.BigDecimal

data class Cost(
    val before: BigDecimal,
    val after: BigDecimal,
) : IOutput {
    companion object {
        fun from(before: BigDecimal, after: BigDecimal): Cost {
            return Cost(
                before = before,
                after = after
            )
        }
    }
}
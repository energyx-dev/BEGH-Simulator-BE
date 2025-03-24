package kr.co.beghsimulator.simulator.Remodeling

import kr.co.beghsimulator.dto.request.SimulateRequest
import kr.co.beghsimulator.service.FileService
import kr.co.beghsimulator.service.IInput
import kr.co.beghsimulator.simulator.input.NormalInput
import kr.co.beghsimulator.simulator.input.RemodelingInput
import org.springframework.stereotype.Component

@Component
class RemodelingSimulatorInput(
    private val fileService: FileService
) {
    fun getInputs(request: SimulateRequest): List<String> {
        val inputs: List<IInput> = createInputs(request)

        return saveInputs(inputs)
    }

    private fun createInputs(request: SimulateRequest): List<IInput> {
        return listOf(
            NormalInput.from(request),
            RemodelingInput.from(request)
        )
    }

    private fun saveInputs(inputs: List<IInput>): List<String> {
        return inputs.map { input -> fileService.writeFile(input).absolutePath }
    }
}
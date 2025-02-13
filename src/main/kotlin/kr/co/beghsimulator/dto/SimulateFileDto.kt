package kr.co.beghsimulator.dto

import kr.co.beghsimulator.dto.input.Geometry
import java.io.File

data class SimulateFileDto(
    val file: File,
    val data: Geometry
)

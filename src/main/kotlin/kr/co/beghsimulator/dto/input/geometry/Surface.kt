package kr.co.beghsimulator.dto.input.geometry

data class Surface(
    val adjZone: String?,
    val area: Int?,
    val azim: Int?,
    val construction: String?,
    val fenestrations: List<Fenestration?>?,
    val id: String?,
    val name: String?,
    val type: String?,
    val underground: Boolean?
)
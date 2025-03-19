package kr.co.beghsimulator.simulator.input.building

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
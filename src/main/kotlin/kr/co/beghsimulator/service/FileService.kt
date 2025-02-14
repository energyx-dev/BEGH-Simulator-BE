package kr.co.beghsimulator.service

import com.fasterxml.jackson.databind.ObjectMapper
import kr.co.beghsimulator.simulator.input.Geometry
import kr.co.beghsimulator.simulator.output.DGBuilding
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.io.File

@Service
class FileService(
    private val objectMapper: ObjectMapper,
) {

    private val log = KotlinLogging.logger { }

    fun <T> readFile(absolutePath: String, type: Class<T>) : T {
        val data: T = objectMapper.readValue(File(absolutePath), type)
        log.info { "file read : $data" }
        return data
    }

    fun <T> writeFile(result: T) : File {
        val currentDir = System.getProperty("user.dir")
        val fileName = "output_${System.currentTimeMillis()}.json"

        val jsonFile = File(currentDir, fileName)

        if (!jsonFile.exists()) {
            jsonFile.createNewFile()
        }

        jsonFile.writeText(objectMapper.writeValueAsString(result))

        return jsonFile
    }
}
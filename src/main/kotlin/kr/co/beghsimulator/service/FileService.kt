package kr.co.beghsimulator.service

import com.fasterxml.jackson.databind.ObjectMapper
import kr.co.beghsimulator.dto.input.Geometry
import kr.co.beghsimulator.dto.output.DGBuilding
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.io.File

@Service
class FileService(
    private val objectMapper: ObjectMapper,
) {

    private val log = KotlinLogging.logger { }

    fun readFile(absolutePath: String) : Geometry {
        val data: Geometry = objectMapper.readValue(File(absolutePath), Geometry::class.java)
        log.info { "file read : $data" }
        return data
    }

    fun writeFile(result: DGBuilding) : File {
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
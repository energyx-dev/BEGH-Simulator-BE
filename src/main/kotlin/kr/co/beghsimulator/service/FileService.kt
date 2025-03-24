package kr.co.beghsimulator.service

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.io.File
import java.util.*

@Service
class FileService(
    private val objectMapper: ObjectMapper,
) {
    private val log = KotlinLogging.logger { }

    fun <T> readFile(absolutePath: String, type: Class<T>) : T {
        val data: T = objectMapper.readValue(File(absolutePath), type)
        log.info { "read file by path : $data" }
        return data
    }

    fun <T> writeFile(result: T) : File {
        val currentDir = System.getProperty("user.dir")
        val fileName = "output_${UUID.randomUUID()}.json"

        val jsonFile = File(currentDir, fileName)
        jsonFile.writeText(objectMapper.writeValueAsString(result))

        return jsonFile
    }
}
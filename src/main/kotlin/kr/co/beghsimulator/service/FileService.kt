package kr.co.beghsimulator.service

import com.fasterxml.jackson.databind.ObjectMapper
import kr.co.beghsimulator.dto.SimulateFileDto
import kr.co.beghsimulator.dto.request.ReadJsonRequest
import kr.co.beghsimulator.dto.input.Geometry
import kr.co.beghsimulator.repository.SimulateRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Service
class FileService(
    private val simulateRepository: SimulateRepository,
    private val objectMapper: ObjectMapper,
) {

    private val log = KotlinLogging.logger { }

    fun simulateFile(request: MultipartFile) {
        val result: SimulateFileDto = readGeometryFile(request)
        simulateRepository.simulate(result.data)
        writeFile(result.data)
    }

    fun simulateJson(request: ReadJsonRequest) {
        val data: Geometry = readGeometryJson(request)
        simulateRepository.simulate(data)
        writeFile(request.absolutePath, data)
    }

    fun readGeometryJson(request: ReadJsonRequest) : Geometry {
        val data: Geometry = objectMapper.readValue(File(request.absolutePath), Geometry::class.java)
        log.info { data }
        return data
    }

    fun readGeometryFile(multipartFile: MultipartFile) : SimulateFileDto {
        val originalFilename: String = multipartFile.originalFilename
            ?: throw IllegalArgumentException("입력 파일명 확인이 필요합니다")

        val file = File(System.getProperty("java.io.tmpdir"), "My$originalFilename")

        multipartFile.transferTo(file)

        val data: Geometry = objectMapper.readValue(file, Geometry::class.java)
        log.info { data }

        return SimulateFileDto(data = data, file = file)
    }

    private fun writeFile(absolutePath: String, data: Geometry) {
        val originFile = File(absolutePath)
        val parentDir: File = originFile.parentFile
            ?: throw IllegalArgumentException("입력된 파일의 부모 디렉토리를 찾을 수 없습니다.")

        val baseName = originFile.nameWithoutExtension
        val jsonFileName = "${baseName}_output.json"

        val jsonFile = File(parentDir, jsonFileName)

        if (!jsonFile.exists()) {
            jsonFile.createNewFile()
        }
        jsonFile.writeText(objectMapper.writeValueAsString(data))
    }

    private fun writeFile(data: Geometry) {
        val currentDir = System.getProperty("user.dir")
        val jsonFileName = "geometry_output.json"

        val jsonFile = File(currentDir, jsonFileName)

        if (!jsonFile.exists()) {
            jsonFile.createNewFile()
        }
        jsonFile.writeText(objectMapper.writeValueAsString(data))
    }
}
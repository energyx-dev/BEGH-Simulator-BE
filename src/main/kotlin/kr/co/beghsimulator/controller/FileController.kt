package kr.co.beghsimulator.controller

import jakarta.validation.Valid
import kr.co.beghsimulator.dto.request.ReadJsonRequest
import kr.co.beghsimulator.service.FileService
import org.jetbrains.annotations.NotNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping
class FileController(
    private val fileService: FileService
) {

    @PostMapping("/json/geometry")
    private fun readGeometryJson(@Valid readJsonRequest: ReadJsonRequest) : ResponseEntity<Void> {
        fileService.simulateJson(readJsonRequest)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/file/geometry", consumes = ["multipart/form-data"])
    private fun readGeometryFile(@RequestPart @NotNull file: MultipartFile) : ResponseEntity<Void> {
        fileService.simulateFile(file)
        return ResponseEntity.ok().build()
    }
}
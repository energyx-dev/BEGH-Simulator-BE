package kr.co.beghsimulator.common.exception

import com.fasterxml.jackson.databind.JsonMappingException
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.io.FileNotFoundException

@RestControllerAdvice
class ExceptionAdvice {
    private val log = KotlinLogging.logger { }

    @ExceptionHandler(FileNotFoundException::class)
    protected fun handleFileNotFoundException(exception: FileNotFoundException): ResponseEntity<String> {
        log.info { "파일이 존재하지 않음 ${exception.message}" }
        return ResponseEntity.badRequest().body("파일이 존재하지 않습니다.")
    }

    @ExceptionHandler(JsonMappingException::class)
    protected fun handleJsonMappingException(exception: JsonMappingException): ResponseEntity<String> {
        log.info { "JSON 파싱 오류 ${exception.message}" }
        return ResponseEntity.badRequest().body("JSON 형식에 맞지 않는 파일입니다.")
    }

    @ExceptionHandler(IllegalArgumentException::class)
    protected fun handleIllegalArgumentException(exception: IllegalArgumentException): ResponseEntity<String> {
        log.info { "입력 파일명 오류 ${exception.message}" }
        return ResponseEntity.badRequest().body(exception.message)
    }
}
package org.spring.base.window.interceptor

import org.slf4j.Logger
import org.spring.base.common.model.dto.ApiCommonResponse
import org.spring.base.common.types.api.ApiStatusCode
import org.spring.base.common.util.logger.getLogger
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.util.NestedServletException
import java.lang.IllegalStateException

/**
 * API Flow 마지막 가장 범용적인 예외(모든 예외의 부모)
 * Exception, Error, Throwable을 핸들링하는 클래스입니다.
 */
@ControllerAdvice
@RestController
@Order(Ordered.LOWEST_PRECEDENCE)
class UniversalExceptionAdvisor : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    fun anyExceptionAdvisor(ex: Exception, req: WebRequest): ResponseEntity<ApiCommonResponse> {

        return ResponseEntity.ok(
            ApiCommonResponse(
                statusCode = ApiStatusCode.EXCEPTION.code,
                message = "예기치 못한 오류가 발생했습니다.",
            )
        )
    }

    /**
     * Error의 경우, NestedServletException으로 Wrapping된 Error를 검사
     *
     * @see <a href="https://github.com/spring-projects/spring-framework/issues/15732">관련 Github Issue</>
     *
     * @param ex 발생한 에러 예외 클래스
     * @param req 요청 정보
     *
     * @return 사용자에게 전달할 에러 메시지
     */
    @ExceptionHandler(NestedServletException::class)
    fun anyErrorAdvisor(ex: NestedServletException, req: WebRequest): ResponseEntity<ApiCommonResponse> {
        // Check NestedServletException is caused by thrown Error
        if (ex.cause != null && ((ex.cause) is Error)) {
            val error = ex.cause as Error

            return ResponseEntity.ok(
                ApiCommonResponse(
                    statusCode = ApiStatusCode.EXCEPTION.code,
                    message = "시스템 소프트웨어에 치명적인 에러가 발생했습니다: ${error.javaClass.simpleName}",
                )
            )
        } else {
            return ResponseEntity.ok(
                ApiCommonResponse(
                    statusCode = ApiStatusCode.EXCEPTION.code,
                    message = "예기치 못한 오류가 발생했습니다.",
                )
            )
        }
    }

    /**
     * Error, Exception에서도 걸러지지 못한 예외를 처리합니다.
     *
     * Spring 레벨에서 IllegalStateException으로 Wrapping된 Throwable을 검사
     *
     * @param ex 발생한 예외
     * @param req 요청 정보
     * @return 사용자에게 전달할 메시지
     */
    @ExceptionHandler(IllegalStateException::class)
    fun anyThrowableAdvisor(ex: Throwable, req: WebRequest): ResponseEntity<ApiCommonResponse> {
        // Check IllegalStateException is caused by thrown Throwable (not Exception or Error)
        if (ex.cause != null && ex.cause !is Exception && ex.cause !is Error) {
            val throwable = ex.cause as Throwable

            return ResponseEntity.ok(
                ApiCommonResponse(
                    statusCode = ApiStatusCode.EXCEPTION.code,
                    message = "시스템 소프트웨어에 치명적인 에러가 발생했습니다[!!UNCATCHED_ERROR!!]: ${throwable.javaClass.simpleName}",
                )
            )
        } else {
            return ResponseEntity.ok(
                ApiCommonResponse(
                    statusCode = ApiStatusCode.EXCEPTION.code,
                    message = "예기치 못한 오류가 발생했습니다.",
                )
            )
        }
    }

    companion object {
        val logger: Logger = getLogger()
    }
}

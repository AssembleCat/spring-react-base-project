package org.spring.base.controller.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * React 어플리케이션 웹 서버를 위한 컨트롤러
 *
 * @constructor Create empty React application controller
 */
@Controller
class ReactRedirectController {

    // match all request except /api/*, and static resources
    @GetMapping(
        value = [
            "/",
            "/{x:[\\w\\-]+}",
            "/{x:^(?!api$).*$}/**/{y:[\\w\\-]+}",
        ]
    )
    fun toIndex(): String {
        return "forward:/index.html"
    }
}

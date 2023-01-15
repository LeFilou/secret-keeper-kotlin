package org.melsif.secretkeeperkotlin.api

import io.swagger.v3.oas.annotations.Parameter
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class DummyController(val dummyService: DummyService) {

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/dummy"],
        produces = ["application/json", "application/xml"]
    )
    fun get(
        @Parameter(name = "dummyParameter") @Valid @RequestParam(
            value = "dummyParameter",
            required = true
        ) url: String
    ): String {
        return dummyService.dummyFunction()
    }
}

@Service
class DummyService {
    fun dummyFunction(): String {
        return "Dummy Function"
    }
}
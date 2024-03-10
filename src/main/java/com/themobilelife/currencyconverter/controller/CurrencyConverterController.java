package com.themobilelife.currencyconverter.controller;

import com.themobilelife.currencyconverter.model.Convert;
import com.themobilelife.currencyconverter.service.impl.CurrencyConverterServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

@RestController
@RequestMapping("/convert")
public class CurrencyConverterController {

    private final CurrencyConverterServiceImpl currencyConverterService;

    public CurrencyConverterController(CurrencyConverterServiceImpl currencyConverterService) {
        this.currencyConverterService = currencyConverterService;
    }

    @PostMapping
    @Tag(name = "POST", description = "POST method of Convert APIs")
    @Operation(summary = "Convert a value",
            description = "Convert a currency to another. The response is an amount as a Big Decimal.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Convert.class)) }),
            @ApiResponse(responseCode = "404", description = "Convert not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)})
    protected BigDecimal convert(@Valid @RequestBody Convert convert) throws Exception {
        return currencyConverterService.convert(convert);
    }

}
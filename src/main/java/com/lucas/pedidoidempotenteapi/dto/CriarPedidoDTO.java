package com.lucas.pedidoidempotenteapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CriarPedidoDTO(
        @NotNull @DecimalMin("0.01")
        BigDecimal valor
) {}

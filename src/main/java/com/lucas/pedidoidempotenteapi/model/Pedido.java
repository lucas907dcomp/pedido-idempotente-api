package com.lucas.pedidoidempotenteapi.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "idempotency_key", unique = true)
    private String idempotencyKey;
}

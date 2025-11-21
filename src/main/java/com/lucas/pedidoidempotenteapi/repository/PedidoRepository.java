package com.lucas.pedidoidempotenteapi.repository;

import com.lucas.pedidoidempotenteapi.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    Optional<Pedido> findByIdempotencyKey(String idempotencyKey);
}

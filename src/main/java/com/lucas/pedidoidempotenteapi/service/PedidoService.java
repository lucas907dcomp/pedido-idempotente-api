package com.lucas.pedidoidempotenteapi.service;

import com.lucas.pedidoidempotenteapi.dto.CriarPedidoDTO;
import com.lucas.pedidoidempotenteapi.exception.IdempotencyKeyMissingException;
import com.lucas.pedidoidempotenteapi.model.Pedido;
import com.lucas.pedidoidempotenteapi.model.StatusPedido;
import com.lucas.pedidoidempotenteapi.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public Pedido criarPedido(CriarPedidoDTO dto, String idempotencyKey) {

        // 1️⃣ Valida se o header existe
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new IdempotencyKeyMissingException();
        }

        log.info("Recebendo requisição com Idempotency-Key: {}", idempotencyKey);

        // 2️⃣ Verifica se essa chave já foi utilizada antes
        Optional<Pedido> pedidoExistente = pedidoRepository.findByIdempotencyKey(idempotencyKey);

        if (pedidoExistente.isPresent()) {
            log.info("Idempotency-Key {} já utilizada. Retornando pedido existente.", idempotencyKey);
            return pedidoExistente.get();
        }

        // 3️⃣ Se não existir, cria um novo pedido
        Pedido novoPedido = Pedido.builder()
                .valor(dto.valor())
                .status(StatusPedido.CREATED)
                .dataCriacao(LocalDateTime.now())
                .idempotencyKey(idempotencyKey)
                .build();

        // 4️⃣ Salva o novo pedido
        novoPedido = pedidoRepository.save(novoPedido);

        log.info("Pedido criado com sucesso para Idempotency-Key {}", idempotencyKey);

        return novoPedido;
    }

    public Pedido buscarPorId(java.util.UUID id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }
}

package com.lucas.pedidoidempotenteapi.controller;

import com.lucas.pedidoidempotenteapi.dto.CriarPedidoDTO;
import com.lucas.pedidoidempotenteapi.model.Pedido;
import com.lucas.pedidoidempotenteapi.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(
            @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey,
            @RequestBody @Valid CriarPedidoDTO dto
    ) {
        Pedido pedido = pedidoService.criarPedido(dto, idempotencyKey);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedido(@PathVariable UUID id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(pedido);
    }
}

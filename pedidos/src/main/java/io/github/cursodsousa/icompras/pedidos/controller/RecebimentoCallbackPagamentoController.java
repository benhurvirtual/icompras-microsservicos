package io.github.cursodsousa.icompras.pedidos.controller;

import io.github.cursodsousa.icompras.pedidos.controller.dto.AdicaoNovoPagamentoDTO;
import io.github.cursodsousa.icompras.pedidos.controller.dto.RecebimentoCallbackPagamentoDTO;
import io.github.cursodsousa.icompras.pedidos.model.ErroResposta;
import io.github.cursodsousa.icompras.pedidos.model.exception.ItemNaoEncontradoException;
import io.github.cursodsousa.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos/callback-pagamentos")
@RequiredArgsConstructor
public class RecebimentoCallbackPagamentoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Object> atualizarStatusPagamento(
            @RequestBody RecebimentoCallbackPagamentoDTO body,
            @RequestHeader(required = true, name = "apiKey") String apiKey
            ){

        pedidoService.atualizarStatusPagamento(
                body.codigo(),
                body.chavePagamento(),
                body.status(),
                body.observacoes()
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("pagamentos")
    public ResponseEntity<Object> adiconarNovoPagamento(
            @RequestBody AdicaoNovoPagamentoDTO dto){
        try {
            pedidoService.adicionarNovoPagamento(dto.codigoPedido(), dto.dados(), dto.tipoPagamento());
            return ResponseEntity.noContent().build();
        }catch (ItemNaoEncontradoException e){
            var erro = new ErroResposta(
                    "Item não encontrado", "codigoPedido", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }
}

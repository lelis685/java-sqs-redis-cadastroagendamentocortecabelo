package br.com.cadastro.corte.entrypoint.controller;

import br.com.cadastro.corte.dataprovider.repository.data.ValorCadastro;
import br.com.cadastro.corte.domain.service.CadastroCorteService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/cadastros/cortes")
@RequiredArgsConstructor
public class CadastroCorteController {

    private final CadastroCorteService service;

    @SneakyThrows
    @GetMapping("/{tipoCorte}/clientes/{cpf}")
    public ValorCadastro encontrarCadastroPorChave(
            @PathVariable String tipoCorte,
            @PathVariable String cpf,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data){

       return service.findByKey(tipoCorte, cpf, data);
    }

}

package br.com.cadastro.corte.dataprovider.repository;

import br.com.cadastro.corte.dataprovider.repository.data.ValorCadastro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CadastroCorteRepository {

    private final RedisTemplate<String, String> template;
    private final ObjectMapper mapper;

    public void save(String chave, ValorCadastro valorCadastro) throws JsonProcessingException {
        String valor = mapper.writeValueAsString(valorCadastro);
        template.opsForValue().set(chave, valor);
    }


    public ValorCadastro findByKey(String chave) throws JsonProcessingException {
        String valor = template.opsForValue().get(chave);
        return mapper.readValue(valor, ValorCadastro.class);
    }

}

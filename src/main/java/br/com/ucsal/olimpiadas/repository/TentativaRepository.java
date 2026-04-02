package br.com.ucsal.olimpiadas.repository;

import java.util.ArrayList;
import java.util.List;
import br.com.ucsal.olimpiadas.Tentativa;

public class TentativaRepository {
    private List<Tentativa> tentativas = new ArrayList<>();
    private long proximoId = 1;

    public void salvar(Tentativa t) {
        t.setId(proximoId++);
        tentativas.add(t);
    }

    public List<Tentativa> listarTodas() {
        return tentativas;
    }
}
package br.com.ucsal.olimpiadas.repository;

import java.util.ArrayList;
import java.util.List;
import br.com.ucsal.olimpiadas.Prova;

public class ProvaRepository {
    private List<Prova> provas = new ArrayList<>();
    private long proximoId = 1;

    public void salvar(Prova prova) {
        prova.setId(proximoId++);
        provas.add(prova);
    }

    public List<Prova> listarTodas() {
        return new ArrayList<>(provas);
    }
}
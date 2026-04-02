package br.com.ucsal.olimpiadas.repository;

import java.util.ArrayList;
import java.util.List;
import br.com.ucsal.olimpiadas.Questao;

public class QuestaoRepository {
    private List<Questao> questoes = new ArrayList<>();
    private long proximoId = 1;

    public void salvar(Questao q) {
        q.setId(proximoId++);
        questoes.add(q);
    }

    public List<Questao> listarPorProva(long provaId) {
        return questoes.stream().filter(q -> q.getProvaId() == provaId).toList();
    }
}
package br.com.ucsal.olimpiadas.repository;

import java.util.ArrayList;
import java.util.List;
import br.com.ucsal.olimpiadas.Participante; // Importamos o modelo

public class ParticipanteRepository {
    
   
    private List<Participante> participantes = new ArrayList<>();
    private long proximoId = 1;

 
    public void salvar(Participante p) {
        p.setId(proximoId++);  
        participantes.add(p);
    }

 
    public List<Participante> listarTodos() {
        return participantes;
    }
}
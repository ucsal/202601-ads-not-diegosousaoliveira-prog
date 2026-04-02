package br.com.ucsal.olimpiadas;

import java.util.List;

public interface Repository<T> {
    void salvar(T entidade);
    List<T> listarTodos();
}
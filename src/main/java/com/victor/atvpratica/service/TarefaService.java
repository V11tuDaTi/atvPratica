package com.victor.atvpratica.service;

import com.victor.atvpratica.model.Tarefa;
import com.victor.atvpratica.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository TarefaRepository;

    public List<Tarefa> ListaTarefa() {
        return TarefaRepository.findAll();
    }

        public Tarefa ListaId(long id) {
        return TarefaRepository.findById(id).orElse(null);
    }

    public Tarefa salvar(Tarefa tarefa) {
        return TarefaRepository.save(tarefa);
    }

    public void deletarById(Long id) {
        TarefaRepository.deleteById(id);
    }

    public Tarefa selectTarefaById(long id){
        Optional<Tarefa> oc = TarefaRepository.findById(id);
        if(oc.isPresent()){
            return oc.get();
        }else{
            throw new RuntimeException("Orcamento nao encotrado.");
        }
    }


    public Tarefa mudarTarefa(long id){
        Tarefa tr = selectTarefaById(id);
        tr.setStatus("Aprovado");
        return TarefaRepository.save(tr);
    }

    public void deletarTarefa(long id){
        TarefaRepository.deleteById(id);
    }

    public Tarefa moverTarefa(Long id) {
        Tarefa tarefa = selectTarefaById(id);
        switch (tarefa.getStatus()) {
            case "A Fazer":
                tarefa.setStatus("Em Progresso");
                break;
            case "Em Progresso":
                tarefa.setStatus("Concluído");
                break;
            case "Concluído":
                throw new IllegalStateException("A tarefa já está concluída.");
            default:
                throw new IllegalStateException("Status inválido.");
        }
        return TarefaRepository.save(tarefa);
    }

    public Tarefa editarTarefa(Long id, Tarefa novaTarefa) {
        Tarefa tarefaExistente = selectTarefaById(id);
        tarefaExistente.setTitulo(novaTarefa.getTitulo());
        tarefaExistente.setDescricao(novaTarefa.getDescricao());
        tarefaExistente.setPrioridade(novaTarefa.getPrioridade());
        tarefaExistente.setDataLimite(novaTarefa.getDataLimite());
        return TarefaRepository.save(tarefaExistente);
    }

    public List<Tarefa> filtrarTarefas(String prioridade, String dataLimite) {
        return TarefaRepository.findAll().stream()
                .filter(t -> prioridade == null || t.getPrioridade().equalsIgnoreCase(prioridade))
                .filter(t -> dataLimite == null || t.getDataLimite().toString().equals(dataLimite))
                .toList();
    }

    public List<Tarefa> tarefasAtrasadas() {
        return TarefaRepository.findAll().stream()
                .filter(t -> !"Concluído".equalsIgnoreCase(t.getStatus()))
                .filter(t -> t.getDataLimite() != null && t.getDataLimite().before(java.sql.Date.valueOf(LocalDate.now())))
                .toList();
    }

}

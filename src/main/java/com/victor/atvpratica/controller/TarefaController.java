package com.victor.atvpratica.controller;

import com.victor.atvpratica.model.Tarefa;
import com.victor.atvpratica.service.TarefaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    public List<Tarefa> getAllProducts() {
        return tarefaService.ListaTarefa();
    }

    @PostMapping
    public Tarefa createProduct(@RequestBody Tarefa tarefa) {
        return tarefaService.salvar(tarefa);
    }

    @PutMapping("/{id}/move")
    public Tarefa moveTarefa(@PathVariable Long id) {
        return tarefaService.moverTarefa(id);
    }

    @PutMapping("/{id}")
    public Tarefa editarTarefa(@PathVariable Long id, @RequestBody Tarefa novaTarefa) {
        return tarefaService.editarTarefa(id, novaTarefa);
    }

    @GetMapping("/filtrar")
    public List<Tarefa> filtrarTarefas(@RequestParam(required = false) String prioridade,
                                       @RequestParam(required = false) String dataLimite) {
        return tarefaService.filtrarTarefas(prioridade, dataLimite);
    }

    @GetMapping("/atrasadas")
    public List<Tarefa> tarefasAtrasadas() {
        return tarefaService.tarefasAtrasadas();
    }

    @DeleteMapping("/deletar/{id}")
    public void deletarTarefa(@PathVariable Long id) {
        tarefaService.deletarTarefa(id);
    }

}

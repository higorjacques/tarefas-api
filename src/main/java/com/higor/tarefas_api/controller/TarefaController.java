package com.higor.tarefas_api.controller;

import com.higor.tarefas_api.model.Tarefa;
import com.higor.tarefas_api.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;


    @PostMapping
    public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }


    @GetMapping
    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarTarefa(@PathVariable Long id) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        return tarefa.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa novaTarefa) {
        return tarefaRepository.findById(id).map(tarefa -> {
            tarefa.setNome(novaTarefa.getNome());
            tarefa.setDataEntrega(novaTarefa.getDataEntrega());
            tarefa.setResponsavel(novaTarefa.getResponsavel());
            tarefaRepository.save(tarefa);
            return ResponseEntity.ok(tarefa);
        }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        return tarefaRepository.findById(id).map(tarefa -> {
            tarefaRepository.deleteById(id);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}


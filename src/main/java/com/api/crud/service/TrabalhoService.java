package com.api.crud.service;

import com.api.crud.dto.TrabalhoDTO;
import com.api.crud.model.Trabalho;
import com.api.crud.repository.TrabalhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrabalhoService {

    @Autowired
    private TrabalhoRepository trabalhoRepository;

    public List<TrabalhoDTO> listarTodos() {
        return trabalhoRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public TrabalhoDTO buscarPorId(Long id) {
        Trabalho trabalho = trabalhoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trabalho não encontrado"));
        return converterParaDTO(trabalho);
    }

    public TrabalhoDTO cadastrar(TrabalhoDTO trabalhoDTO) {
        Trabalho trabalho = converterParaEntidade(trabalhoDTO);
        trabalho = trabalhoRepository.save(trabalho);
        return converterParaDTO(trabalho);
    }

    public TrabalhoDTO atualizar(Long id, TrabalhoDTO trabalhoDTO) {
        if (!trabalhoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trabalho não encontrado");
        }
        
        trabalhoDTO.setId(id);
        Trabalho trabalho = converterParaEntidade(trabalhoDTO);
        trabalho = trabalhoRepository.save(trabalho);
        return converterParaDTO(trabalho);
    }

    public void excluir(Long id) {
        if (!trabalhoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trabalho não encontrado");
        }
        trabalhoRepository.deleteById(id);
    }

    // Métodos de conversão
    private TrabalhoDTO converterParaDTO(Trabalho trabalho) {
        TrabalhoDTO dto = new TrabalhoDTO();
        dto.setId(trabalho.getId());
        dto.setNome(trabalho.getNome());
        dto.setEndereco(trabalho.getEndereco());
        return dto;
    }

    private Trabalho converterParaEntidade(TrabalhoDTO dto) {
        Trabalho trabalho = new Trabalho();
        trabalho.setId(dto.getId());
        trabalho.setNome(dto.getNome());
        trabalho.setEndereco(dto.getEndereco());
        trabalho.setPessoas(new ArrayList<>());
        return trabalho;
    }
} 
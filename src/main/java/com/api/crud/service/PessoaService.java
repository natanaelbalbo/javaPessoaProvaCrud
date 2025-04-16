package com.api.crud.service;

import com.api.crud.dto.PessoaDTO;
import com.api.crud.model.Pessoa;
import com.api.crud.model.Trabalho;
import com.api.crud.repository.PessoaRepository;
import com.api.crud.repository.TrabalhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TrabalhoRepository trabalhoRepository;

    public List<PessoaDTO> listarTodas() {
        return pessoaRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public PessoaDTO buscarPorId(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));
        return converterParaDTO(pessoa);
    }

    public PessoaDTO cadastrar(PessoaDTO pessoaDTO) {
        if (pessoaRepository.existsByCpf(pessoaDTO.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF já cadastrado");
        }

        Pessoa pessoa = converterParaEntidade(pessoaDTO);
        pessoa = pessoaRepository.save(pessoa);
        return converterParaDTO(pessoa);
    }

    public PessoaDTO atualizar(Long id, PessoaDTO pessoaDTO) {
        Pessoa pessoaExistente = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));

        // Verifica se o CPF já existe e não pertence a esta pessoa
        if (!pessoaExistente.getCpf().equals(pessoaDTO.getCpf()) && 
            pessoaRepository.existsByCpf(pessoaDTO.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF já cadastrado para outra pessoa");
        }

        pessoaDTO.setId(id);
        Pessoa pessoa = converterParaEntidade(pessoaDTO);
        pessoa = pessoaRepository.save(pessoa);
        return converterParaDTO(pessoa);
    }

    public void excluir(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada");
        }
        pessoaRepository.deleteById(id);
    }

    // Métodos de conversão
    private PessoaDTO converterParaDTO(Pessoa pessoa) {
        PessoaDTO dto = new PessoaDTO();
        dto.setId(pessoa.getId());
        dto.setCpf(pessoa.getCpf());
        dto.setIdade(pessoa.getIdade());
        if (pessoa.getTrabalho() != null) {
            dto.setTrabalhoId(pessoa.getTrabalho().getId());
        }
        return dto;
    }

    private Pessoa converterParaEntidade(PessoaDTO dto) {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(dto.getId());
        pessoa.setCpf(dto.getCpf());
        pessoa.setIdade(dto.getIdade());
        
        if (dto.getTrabalhoId() != null) {
            Trabalho trabalho = trabalhoRepository.findById(dto.getTrabalhoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trabalho não encontrado"));
            pessoa.setTrabalho(trabalho);
        }
        
        return pessoa;
    }
} 
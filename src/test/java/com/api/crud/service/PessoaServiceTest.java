package com.api.crud.service;

import com.api.crud.dto.PessoaDTO;
import com.api.crud.model.Pessoa;
import com.api.crud.model.Trabalho;
import com.api.crud.repository.PessoaRepository;
import com.api.crud.repository.TrabalhoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private TrabalhoRepository trabalhoRepository;

    @InjectMocks
    private PessoaService pessoaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarTodasPessoas() {
        // Arrange
        Trabalho trabalho = new Trabalho(1L, "Desenvolvedor", "Rua A", null);
        Pessoa pessoa1 = new Pessoa(1L, "12345678901", 25, trabalho);
        Pessoa pessoa2 = new Pessoa(2L, "09876543210", 30, trabalho);
        List<Pessoa> pessoas = Arrays.asList(pessoa1, pessoa2);

        when(pessoaRepository.findAll()).thenReturn(pessoas);

        // Act
        List<PessoaDTO> result = pessoaService.listarTodas();

        // Assert
        assertEquals(2, result.size());
        assertEquals("12345678901", result.get(0).getCpf());
        assertEquals("09876543210", result.get(1).getCpf());
        verify(pessoaRepository, times(1)).findAll();
    }

    @Test
    void deveBuscarPessoaPorId() {
        // Arrange
        Trabalho trabalho = new Trabalho(1L, "Desenvolvedor", "Rua A", null);
        Pessoa pessoa = new Pessoa(1L, "12345678901", 25, trabalho);

        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));

        // Act
        PessoaDTO result = pessoaService.buscarPorId(1L);

        // Assert
        assertNotNull(result);
        assertEquals("12345678901", result.getCpf());
        assertEquals(25, result.getIdade());
        assertEquals(1L, result.getTrabalhoId());
    }

    @Test
    void deveLancarExcecaoQuandoPessoaNaoEncontrada() {
        // Arrange
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            pessoaService.buscarPorId(1L);
        });
    }

    @Test
    void deveCadastrarPessoa() {
        // Arrange
        PessoaDTO pessoaDTO = new PessoaDTO(null, "12345678901", 25, 1L);
        Trabalho trabalho = new Trabalho(1L, "Desenvolvedor", "Rua A", null);
        Pessoa pessoaSalva = new Pessoa(1L, "12345678901", 25, trabalho);

        when(pessoaRepository.existsByCpf("12345678901")).thenReturn(false);
        when(trabalhoRepository.findById(1L)).thenReturn(Optional.of(trabalho));
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoaSalva);

        // Act
        PessoaDTO result = pessoaService.cadastrar(pessoaDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("12345678901", result.getCpf());
        assertEquals(25, result.getIdade());
        assertEquals(1L, result.getTrabalhoId());
    }

    @Test
    void deveLancarExcecaoQuandoCpfJaCadastrado() {
        // Arrange
        PessoaDTO pessoaDTO = new PessoaDTO(null, "12345678901", 25, 1L);

        when(pessoaRepository.existsByCpf("12345678901")).thenReturn(true);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            pessoaService.cadastrar(pessoaDTO);
        });
    }

    @Test
    void deveAtualizarPessoa() {
        // Arrange
        Long id = 1L;
        PessoaDTO pessoaDTO = new PessoaDTO(null, "12345678901", 30, 1L);
        Trabalho trabalho = new Trabalho(1L, "Desenvolvedor", "Rua A", null);
        Pessoa pessoaExistente = new Pessoa(id, "12345678901", 25, trabalho);
        Pessoa pessoaAtualizada = new Pessoa(id, "12345678901", 30, trabalho);

        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoaExistente));
        when(trabalhoRepository.findById(1L)).thenReturn(Optional.of(trabalho));
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoaAtualizada);

        // Act
        PessoaDTO result = pessoaService.atualizar(id, pessoaDTO);

        // Assert
        assertEquals(id, result.getId());
        assertEquals("12345678901", result.getCpf());
        assertEquals(30, result.getIdade());
        assertEquals(1L, result.getTrabalhoId());
    }

    @Test
    void deveExcluirPessoa() {
        // Arrange
        Long id = 1L;
        when(pessoaRepository.existsById(id)).thenReturn(true);
        doNothing().when(pessoaRepository).deleteById(id);

        // Act
        pessoaService.excluir(id);

        // Assert
        verify(pessoaRepository, times(1)).deleteById(id);
    }

    @Test
    void deveLancarExcecaoAoExcluirPessoaInexistente() {
        // Arrange
        Long id = 1L;
        when(pessoaRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            pessoaService.excluir(id);
        });
    }
} 
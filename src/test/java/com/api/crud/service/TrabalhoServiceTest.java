package com.api.crud.service;

import com.api.crud.dto.TrabalhoDTO;
import com.api.crud.model.Trabalho;
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
import static org.mockito.Mockito.*;

class TrabalhoServiceTest {

    @Mock
    private TrabalhoRepository trabalhoRepository;

    @InjectMocks
    private TrabalhoService trabalhoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarTodosTrabalhos() {
        // Arrange
        Trabalho trabalho1 = new Trabalho(1L, "Desenvolvedor", "Rua A", null);
        Trabalho trabalho2 = new Trabalho(2L, "Analista", "Rua B", null);
        List<Trabalho> trabalhos = Arrays.asList(trabalho1, trabalho2);

        when(trabalhoRepository.findAll()).thenReturn(trabalhos);

        // Act
        List<TrabalhoDTO> result = trabalhoService.listarTodos();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Desenvolvedor", result.get(0).getNome());
        assertEquals("Analista", result.get(1).getNome());
        verify(trabalhoRepository, times(1)).findAll();
    }

    @Test
    void deveBuscarTrabalhoPorId() {
        // Arrange
        Trabalho trabalho = new Trabalho(1L, "Desenvolvedor", "Rua A", null);

        when(trabalhoRepository.findById(1L)).thenReturn(Optional.of(trabalho));

        // Act
        TrabalhoDTO result = trabalhoService.buscarPorId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Desenvolvedor", result.getNome());
        assertEquals("Rua A", result.getEndereco());
    }

    @Test
    void deveLancarExcecaoQuandoTrabalhoNaoEncontrado() {
        // Arrange
        when(trabalhoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            trabalhoService.buscarPorId(1L);
        });
    }

    @Test
    void deveCadastrarTrabalho() {
        // Arrange
        TrabalhoDTO trabalhoDTO = new TrabalhoDTO(null, "Desenvolvedor", "Rua A");
        Trabalho trabalhoSalvo = new Trabalho(1L, "Desenvolvedor", "Rua A", null);

        when(trabalhoRepository.save(any(Trabalho.class))).thenReturn(trabalhoSalvo);

        // Act
        TrabalhoDTO result = trabalhoService.cadastrar(trabalhoDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Desenvolvedor", result.getNome());
        assertEquals("Rua A", result.getEndereco());
    }

    @Test
    void deveAtualizarTrabalho() {
        // Arrange
        Long id = 1L;
        TrabalhoDTO trabalhoDTO = new TrabalhoDTO(null, "Desenvolvedor Sênior", "Rua C");
        Trabalho trabalhoAtualizado = new Trabalho(id, "Desenvolvedor Sênior", "Rua C", null);

        when(trabalhoRepository.existsById(id)).thenReturn(true);
        when(trabalhoRepository.save(any(Trabalho.class))).thenReturn(trabalhoAtualizado);

        // Act
        TrabalhoDTO result = trabalhoService.atualizar(id, trabalhoDTO);

        // Assert
        assertEquals(id, result.getId());
        assertEquals("Desenvolvedor Sênior", result.getNome());
        assertEquals("Rua C", result.getEndereco());
    }

    @Test
    void deveLancarExcecaoAoAtualizarTrabalhoInexistente() {
        // Arrange
        Long id = 1L;
        TrabalhoDTO trabalhoDTO = new TrabalhoDTO(null, "Desenvolvedor", "Rua A");

        when(trabalhoRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            trabalhoService.atualizar(id, trabalhoDTO);
        });
    }

    @Test
    void deveExcluirTrabalho() {
        // Arrange
        Long id = 1L;
        when(trabalhoRepository.existsById(id)).thenReturn(true);
        doNothing().when(trabalhoRepository).deleteById(id);

        // Act
        trabalhoService.excluir(id);

        // Assert
        verify(trabalhoRepository, times(1)).deleteById(id);
    }

    @Test
    void deveLancarExcecaoAoExcluirTrabalhoInexistente() {
        // Arrange
        Long id = 1L;
        when(trabalhoRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            trabalhoService.excluir(id);
        });
    }
} 
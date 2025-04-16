package com.api.crud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrabalhoDTO {
    
    private Long id;
    
    @NotBlank(message = "Nome não pode estar em branco")
    private String nome;
    
    @NotBlank(message = "Endereço não pode estar em branco")
    private String endereco;
} 
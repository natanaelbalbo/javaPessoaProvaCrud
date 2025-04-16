package com.api.crud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO {
    
    private Long id;
    
    @NotBlank(message = "CPF não pode estar em branco")
    private String cpf;
    
    @NotNull(message = "Idade não pode ser nula")
    @Positive(message = "Idade deve ser um número positivo")
    private Integer idade;
    
    private Long trabalhoId;
} 
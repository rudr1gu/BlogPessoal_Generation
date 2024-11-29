package com.generation.blogpessoal.model;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_postagens")
public class Postagem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotBlank
    @Size(min = 5, max = 100)
    private String titulo;
    
    @NotBlank
    @Size(min = 5, max = 100)
    private String texto;

    @UpdateTimestamp
    private String data;

    @ManyToOne
    @JsonIgnoreProperties("postagem")
    private Tema tema;
  
}

package com.generation.blogpessoal.model;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_postagens")
public class Postagem {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    @NotBlank
    @Size(min = 5, max = 100)
    private String titulo;

    @Getter
    @Setter
    @NotBlank
    @Size(min = 5, max = 100)
    private String texto;

    @Getter
    @Setter
    @UpdateTimestamp
    private String data;
  
}

package com.generation.blogpessoal.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// import com.generation.blogpessoal.BlogpessoalApplication;
import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @BeforeAll
    void start() {
        Usuario usuario = new Usuario(0L, "João", "joao@root.com", "12345678", "url");
        usuarioRepository.deleteAll();
        usuarioService.cadastrarUsuario(usuario);
    }

    @Test
    @DisplayName("✔ Cadastrar Usuário!")
    public void deveCriarUsuario() {
        Usuario usuario = new Usuario(0L, "Maria", "maria@mail.com", "12345678", "url");

        HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuario);

        ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao,
                Usuario.class);
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    }

    @Test
    @DisplayName("✔ Não deve duplicar Usuário!")
    public void naoDeveCadastrarUsuario() {
        Usuario usuario = new Usuario(0L, "João", "joao@root.com", "12345678", "url");

        HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuario);

        ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao,
                Usuario.class);
        assertEquals(HttpStatus.BAD_REQUEST , resposta.getStatusCode());
    }
    
}
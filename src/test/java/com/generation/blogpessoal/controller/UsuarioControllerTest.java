package com.generation.blogpessoal.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

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
        Usuario usuario = new Usuario(0L, "João", "root@root.com", "rootroot", "url");
        usuarioRepository.deleteAll();
        usuarioService.cadastrarUsuario(usuario);
    }

    @Test
    @DisplayName("✔ Cadastrar Usuário!")
    public void deveCriarUsuario() {
        Usuario usuario = new Usuario(0L, "Maria Silva", "maria85@mail.com", "12345678", "url");

        HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuario);

        ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao,
                Usuario.class);
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    }

    @Test
    @DisplayName("✔ Não deve duplicar Usuário!")
    public void naoDeveCadastrarUsuario() {
        Usuario usuario = new Usuario(0L, "João", "joao@root.com", "12345678", "url");

        usuarioService.cadastrarUsuario(usuario);

        HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuario);

        ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao,
                Usuario.class);
        assertEquals(HttpStatus.BAD_REQUEST , resposta.getStatusCode());
    }
    
    @Test
    @DisplayName("✔ Atualizar Usuário!")
    public void deveAtualizarUsuario() {
        Usuario usuario = new Usuario(0L, "João", "joao32423@root.com", "12345678", "url");

        Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(usuario);

        Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(), "João da Silva", "joao444@root.com", "12345678", "url2");

        HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuarioUpdate);

        ResponseEntity<Usuario> resposta = testRestTemplate.withBasicAuth("root@root.com", "rootroot").exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, Usuario.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());        
    }


    @Test
    @DisplayName("✔ Listar todos os Usuários!")
    public void deveMostrarTodosUsuarios() {
        Usuario usuario1 = new Usuario(0L, "Maria", "maria@mail.com", "12345678", "url");
        Usuario usuario2 = new Usuario(0L, "Maria2", "maria2@mail.com", "12345678", "url");
        Usuario usuario3 = new Usuario(0L, "Maria3", "maria3@mail.com", "12345678", "url");

        usuarioService.cadastrarUsuario(usuario1);
        usuarioService.cadastrarUsuario(usuario2);
        usuarioService.cadastrarUsuario(usuario3);

        ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("root@root.com", "rootroot").exchange("/usuarios/all", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

}
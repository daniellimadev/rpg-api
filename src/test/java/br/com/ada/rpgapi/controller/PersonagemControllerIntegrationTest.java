package br.com.ada.rpgapi.controller;

import br.com.ada.rpgapi.model.Personagem;
import br.com.ada.rpgapi.repository.PersonagemRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonagemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonagemRepository personagemRepository;

    @AfterEach
    void teardown() {
        personagemRepository.deleteAll();
    }

    @Test
    void cadastraPersonagemTest() throws Exception {
        mockMvc.perform(post("/personagem")
                .contentType("application/json")
                .content("{\n" +
                        "\"nome\": \"Harry\"\n" +
                        "\n" +
                        "}"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorNomeTest1() throws Exception {
        personagemRepository.save(Personagem.builder().nome("Harry").build());
        mockMvc.perform(get("/personagem?nome=Harry"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(notNullValue()));
        Assertions.assertEquals(1, personagemRepository.count());
    }


    @Test
    void buscarPorNomeTest2() throws Exception {
        personagemRepository.save(Personagem.builder().nome("Harry").build());
        MvcResult result = mockMvc.perform(get("/personagem?nome=Harry"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(notNullValue()))
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals(1, personagemRepository.count());

    }
}

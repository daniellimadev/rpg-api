package br.com.ada.rpgapi.service;

import br.com.ada.rpgapi.exception.RpgException;
import br.com.ada.rpgapi.model.Personagem;
import br.com.ada.rpgapi.repository.PersonagemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PersonagemServiceTest {


    private PersonagemService personagemService;

    @Mock
    private PersonagemRepository personagemRepository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        personagemService = new PersonagemService(personagemRepository);
    }

    @Test
    void cadastrandoPersonagemValidorTest(){
        Personagem personagem = Personagem.builder()
                .nome("Harry")
                .build();

        Mockito.when(personagemRepository.findByNome(personagem.getNome())).thenReturn(null);
        Mockito.when(personagemRepository.save(personagem)).thenReturn(personagem);

        Personagem result = personagemService.cadastrar(personagem);

        Assertions.assertEquals(personagem, result);

        Mockito.verify(personagemRepository, Mockito.times(1)).findByNome(personagem.getNome());
        Mockito.verify(personagemRepository, Mockito.times(1)).save(personagem);
    }


    @Test
    void cadastrandoPersonagemValidorExitenteTest() {
        Personagem personagem = Personagem.builder()
                .nome("Harry")
                .build();

        Mockito.when(personagemRepository.findByNome(personagem.getNome())).thenReturn(personagem);

        Assertions.assertThrows(RpgException.class, () -> personagemService.cadastrar(personagem));

        Mockito.verify(personagemRepository, Mockito.times(1)).findByNome(personagem.getNome());
        Mockito.verify(personagemRepository, Mockito.never()).save(personagem);


    }

    @Test
    void cadastrandoPersonagemNomeInvalidorTest() {
        Personagem personagem = Personagem.builder()
                .nome("@Harry")
                .build();

        Assertions.assertThrows(RpgException.class, () -> personagemService.cadastrar(personagem));

    }

    @Test
    void buscarPorNomeEncontradaTest() {
        Personagem personagem = Personagem.builder()
                .nome("@Harry")
                .build();

        Mockito.when(personagemRepository.findByNome(personagem.getNome())).thenReturn(personagem);

        Personagem result = personagemService.buscarPorNome(personagem.getNome());

        Assertions.assertEquals(personagem, result);
        Mockito.verify(personagemRepository, Mockito.times(1)).findByNome(personagem.getNome());
    }

    @Test
    void buscarPorNomeNaoEncontradaTest() {
       String nome = "";

        Mockito.when(personagemRepository.findByNome(nome)).thenReturn(null);

        Assertions.assertThrows(RpgException.class, () -> personagemService.buscarPorNome(nome));
        Mockito.verify(personagemRepository, Mockito.times(1)).findByNome(nome);
    }
}

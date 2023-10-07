package br.com.ada.rpgapi.service;

import br.com.ada.rpgapi.exception.RpgException;
import br.com.ada.rpgapi.model.Personagem;
import br.com.ada.rpgapi.repository.PersonagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonagemService {

    private final PersonagemRepository personagemRepository;

    public Personagem cadastrar(Personagem personagem) {
        if (existePersonagem(personagem.getNome())) {
            throw new RpgException("Já existe um personagem com esse nome cadastrado");
        }

        if (!nomeValido(personagem.getNome())) {
            throw new RpgException("Nome do personagem é inválido");
        }

        return personagemRepository.save(personagem);

    }

    public Personagem buscarPorNome(String nome) {
        Personagem personagem = personagemRepository.findByNome(nome);

        if (personagem == null) {
            throw new RpgException("Personagem não encontrado");
        }

        return personagem;

    }


    private boolean existePersonagem(String nome) {
        return personagemRepository.findByNome(nome) != null;
    }

    private boolean nomeValido(String nome) {
        String regularExpression = "^[A-Za-z][A-Za-z0-9_]{3,29}$";
        return nome.matches(regularExpression);
    }
}

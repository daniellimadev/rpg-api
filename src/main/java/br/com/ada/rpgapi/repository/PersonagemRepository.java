package br.com.ada.rpgapi.repository;

import br.com.ada.rpgapi.model.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonagemRepository extends JpaRepository<Personagem, Long> {

    Personagem findByNome(String nome);
}

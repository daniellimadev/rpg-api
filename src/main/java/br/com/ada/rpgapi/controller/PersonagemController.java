package br.com.ada.rpgapi.controller;

import br.com.ada.rpgapi.model.Personagem;
import br.com.ada.rpgapi.service.PersonagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personagem")
@RequiredArgsConstructor
public class PersonagemController {

    private final PersonagemService personagemService;

    @PostMapping
    public ResponseEntity<Personagem> cadastrar(@RequestBody Personagem personagem) {
        return ResponseEntity.ok(personagemService.cadastrar(personagem));
    }

    @GetMapping
    public ResponseEntity<Personagem> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(personagemService.buscarPorNome(nome));
    }

}

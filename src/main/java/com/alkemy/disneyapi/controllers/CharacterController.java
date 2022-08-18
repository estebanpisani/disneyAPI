package com.alkemy.disneyapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alkemy.disneyapi.dto.CharacterBasicDTO;
import com.alkemy.disneyapi.dto.CharacterDTO;
import com.alkemy.disneyapi.mapper.CharacterMapper;
import com.alkemy.disneyapi.services.CharacterService;

@RestController
@RequestMapping("/characters")
public class CharacterController {
	
	@Autowired
	private CharacterService characterServ;
	@Autowired
	CharacterMapper characterMapper;

	@GetMapping("/all")
	public ResponseEntity<List<CharacterBasicDTO>> getAllCharacters(){
		return ResponseEntity.ok().body(characterServ.getAllCharacters());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CharacterDTO> getCharacterDetails(@PathVariable String id) throws Exception {
		return ResponseEntity.ok().body(characterServ.getCharacterDetails(id));
	}

	@GetMapping
	public ResponseEntity<List<CharacterDTO>> getByFilters(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer age,
			@RequestParam(required = false) Double weight,
			@RequestParam(required = false) List<String> movies,
			@RequestParam(required = false, defaultValue = "asc") String order
	){
		List<CharacterDTO> characters = characterServ.getByFilters(name, age, weight, movies, order);
		return ResponseEntity.ok(characters);
	}

	@PostMapping
	public ResponseEntity<CharacterDTO> save(@RequestBody CharacterDTO dto) throws Exception{
		CharacterDTO savedCharacter = characterServ.saveCharacter(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCharacter);	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CharacterDTO> update(@PathVariable String id, @RequestBody CharacterDTO dto) throws Exception{
		CharacterDTO result = characterServ.updateCharacter(id, dto);
		return ResponseEntity.ok().body(result);	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) throws Exception{
		characterServ.deleteCharacter(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();	
	}

}
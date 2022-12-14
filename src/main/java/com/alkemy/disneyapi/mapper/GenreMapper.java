package com.alkemy.disneyapi.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alkemy.disneyapi.dto.GenreDTO;
import com.alkemy.disneyapi.entities.Genre;

@Component
public class GenreMapper {
	
	public Genre genreDTO2Entity(GenreDTO dto) {
		Genre genre = new Genre();
		genre.setName(dto.getName());;
		genre.setImage(dto.getImage());
		return genre;
	}
	
	public GenreDTO genreEntity2DTO(Genre genre) {
		GenreDTO dto = new GenreDTO();
		dto.setId(genre.getId());
		dto.setName(genre.getName());
		dto.setImage(genre.getImage());
		return dto;
	}
	
	public List<GenreDTO> genreEntityList2DTOList(List<Genre> genres){
		List<GenreDTO> dtos = new ArrayList<>();
		for (Genre genre : genres) {
			dtos.add(this.genreEntity2DTO(genre));
		}
		return dtos;
	}
	
	public List<Genre> DTOList2genreEntityList(List<GenreDTO> dtos){
		List<Genre> genres = new ArrayList<>();
		for (GenreDTO dto : dtos) {
			genres.add(genreDTO2Entity(dto));
		}
		return genres;
	}
	
}

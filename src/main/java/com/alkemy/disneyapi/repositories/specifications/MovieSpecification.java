package com.alkemy.disneyapi.repositories.specifications;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import com.alkemy.disneyapi.entities.Genre;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.alkemy.disneyapi.dto.MovieFiltersDTO;
import com.alkemy.disneyapi.entities.Movie;

@Component
public class MovieSpecification {
	
	public Specification<Movie> getByFilters(MovieFiltersDTO filtersDTO){
		return (root, query, criteriaBuilder) ->{
			
			List<Predicate> predicates = new ArrayList<>();
			
			if(filtersDTO.getTitle() != null && !filtersDTO.getTitle().isEmpty()) {
				predicates.add(
						criteriaBuilder.like(
								criteriaBuilder.lower(root.get("title")),
								"%" + filtersDTO.getTitle().toLowerCase()+"%")
				);
			}	
			
			//TODO Agregar filtro de Genre
			if(filtersDTO.getGenre() != null){
				Join<Genre, Movie> join = root.join("genre", JoinType.INNER);
				Expression<String> genre = join.get("id");
				predicates.add(genre.in(filtersDTO.getGenre()));
			}
			//Remove duplicates
			query.distinct(true);

			//TODO OrderByDate
			if(filtersDTO.getOrder().equalsIgnoreCase("DESC")){
				query.orderBy(criteriaBuilder.desc(root.get("title")));
			}
			else{
				query.orderBy(criteriaBuilder.asc(root.get("title")));
			}
			
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
		
	}
}

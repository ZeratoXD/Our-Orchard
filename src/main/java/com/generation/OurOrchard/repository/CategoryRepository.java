package com.generation.OurOrchard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.OurOrchard.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	public List<Category> findAllByNameContainingIgnoreCase(@Param("name") String name);

}

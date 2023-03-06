package com.projetkfet.backend.data.stock;

import com.projetkfet.backend.model.stock.SubCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubCategoryRepository extends CrudRepository<SubCategory, Integer> {

    Optional<SubCategory> findById(UUID id);
}
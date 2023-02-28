package com.example.homebankingAaronSolo.repositories;

import com.example.homebankingAaronSolo.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.Entity;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card,Long> {
}

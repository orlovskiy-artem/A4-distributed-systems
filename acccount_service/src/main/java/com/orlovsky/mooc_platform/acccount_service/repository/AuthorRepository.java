package com.orlovsky.mooc_platform.acccount_service.repository;

import com.orlovsky.mooc_platform.acccount_service.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
}

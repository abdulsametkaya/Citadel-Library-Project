package com.library.citadel_library.repository;

import com.library.citadel_library.domain.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
    @Query(value = "SELECT p  FROM Author p")
    Page<Author> findAllPublishersWithPage(Pageable pageable);
}

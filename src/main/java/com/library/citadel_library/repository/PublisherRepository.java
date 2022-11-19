package com.library.citadel_library.repository;


import com.library.citadel_library.domain.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher,Long> {
    @Query(value = "SELECT p  FROM Publisher p")
    Page<Publisher> findAllPublishersWithPage(Pageable pageable);

}

package com.shchurov.webquizzes.repository;


import com.shchurov.webquizzes.model.Completed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedRepository extends PagingAndSortingRepository<Completed, Long> {

    @Query(value = "SELECT c FROM Completed c WHERE c.user.email = :email")
    Page<Completed> findAll(@Param("email") String userId, Pageable pageable);
}

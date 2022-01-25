package com.shchurov.webquizzes.repository;


import com.shchurov.webquizzes.model.Quiz;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long> {
    List<Quiz> findAll();
}

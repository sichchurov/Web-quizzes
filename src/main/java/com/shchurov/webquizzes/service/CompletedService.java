package com.shchurov.webquizzes.service;

import com.shchurov.webquizzes.model.Completed;
import com.shchurov.webquizzes.repository.CompletedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CompletedService {
    private final CompletedRepository completedRepository;

    @Autowired
    public CompletedService(CompletedRepository completedRepository) {
        this.completedRepository = completedRepository;
    }

    public void saveCompleted(Completed completed) {
        completedRepository.save(completed);
    }

    public Page<Completed> findAllCompleted(String userId, int page, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
        return completedRepository.findAll(userId, paging);
    }

}

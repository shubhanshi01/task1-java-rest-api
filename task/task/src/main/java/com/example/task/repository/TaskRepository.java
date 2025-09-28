package com.example.task.repository;

import com.example.task.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

    @Query("{'name': {$regex: ?0, $options: 'i'}}")
    List<Task> findByNameContainingIgnoreCase(String name);

    boolean existsById(String id);
}
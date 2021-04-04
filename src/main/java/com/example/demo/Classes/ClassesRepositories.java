package com.example.demo.Classes;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassesRepositories extends CrudRepository<Classes, Long> {

    List<Classes> getAllByOwnerId(Long id);
}

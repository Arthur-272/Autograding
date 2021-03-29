package com.example.demo.TestCases;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCasesRepository extends CrudRepository<TestCases, Long> {
}

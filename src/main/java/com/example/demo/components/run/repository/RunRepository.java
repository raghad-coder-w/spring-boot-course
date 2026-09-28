package com.example.demo.components.run.repository;

import com.example.demo.components.run.entity.Run;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface RunRepository extends ListCrudRepository<Run,Integer> {

    List<Run> findAllByLocation(String location);
}

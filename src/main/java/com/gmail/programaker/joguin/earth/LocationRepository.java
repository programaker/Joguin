package com.gmail.programaker.joguin.earth;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository {
    List<Location> findAll();
}

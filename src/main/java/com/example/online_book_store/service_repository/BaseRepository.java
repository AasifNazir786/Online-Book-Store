package com.example.online_book_store.service_repository;

import java.util.List;

public interface BaseRepository<T> {
    T create(T dto);

    List<T> getAll();

    T getById(int id);

    T update(int id, T dto);

    void delete(int id);
}

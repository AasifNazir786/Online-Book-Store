package com.example.online_book_store.Generics;

import java.util.List;

public interface BaseRepository<T> {
    T create(T dto) throws Exception;

    List<T> getAll();

    T getById(int id);

    T updateById(int id, T dto);

    void delete(int id);
}

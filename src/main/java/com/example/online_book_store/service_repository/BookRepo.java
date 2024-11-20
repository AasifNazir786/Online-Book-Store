package com.example.online_book_store.service_repository;

import java.util.List;

import com.example.online_book_store.dto.BookDTO;

public interface BookRepo extends BaseRepository<BookDTO>{

    BookDTO createBookDTO(BookDTO bookDTO);

    List<BookDTO> getAllBookDTOs();

    BookDTO getBookDTOById(int id);

    BookDTO updateBookDTO(int id, BookDTO bookDTO);

    void deleteBookDTO(int id);
}

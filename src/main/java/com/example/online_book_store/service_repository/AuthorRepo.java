package com.example.online_book_store.service_repository;

import java.util.List;

import com.example.online_book_store.dto.AuthorDTO;

public interface AuthorRepo {

    AuthorDTO createAuthorDTO(AuthorDTO authorDTO);

    List<AuthorDTO> getAllAuthorDTOs();

    AuthorDTO getAuthorDTOById(int id);

    AuthorDTO updateAuthorDTO(int id, AuthorDTO authorDTO);
    
    void deleteAuthorDTO(int id);
}

package com.example.online_book_store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.online_book_store.dto.ReviewDTO;
import com.example.online_book_store.model.Review;

@Mapper(componentModel="spring", uses={BookMapper.class})
public abstract class ReviewMapper {
    
    @Mapping(target="user", ignore=true)
    public abstract Review toEntity(ReviewDTO dto);

    @Mapping(target="user", ignore=true)
    public abstract ReviewDTO toDTO(Review review);


}

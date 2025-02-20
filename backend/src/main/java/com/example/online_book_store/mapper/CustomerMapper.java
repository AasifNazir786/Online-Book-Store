// package com.example.online_book_store.mapper;

// import org.mapstruct.Mapper;
// import org.mapstruct.Mapping;

// import com.example.online_book_store.dto.CustomerDTO;
// import com.example.online_book_store.model.Customer;

// @Mapper(componentModel="spring", uses = {OrderMapper.class})
// public interface CustomerMapper {

//     @Mapping(target = "orders", ignore = true)
//     CustomerDTO toDTO(Customer customer);

//     @Mapping(target="orders", ignore = true)
//     Customer toEntity(CustomerDTO customerDTO);
// }

// package com.example.online_book_store.mapper;

// import org.mapstruct.Mapper;
// import org.mapstruct.Mapping;

// import com.example.online_book_store.dto.OrderDTO;
// import com.example.online_book_store.model.BookOrder;

// @Mapper(componentModel="spring")
// public interface OrderMapper {

//     @Mapping(target="customerId", ignore = true)
//     @Mapping(target="bookIDs", ignore = true)//expression="java(order.getBooks().stream().map(book -> book.getBookId()).toList())")
//     OrderDTO toDTO(BookOrder order);
    
//     @Mapping(target = "customer", ignore = true) // set it explicitly/manually
//     @Mapping(target = "books", ignore = true) // set it explicitly/manually
//     BookOrder toEntity(OrderDTO orderDTO);
    
// }

// //@Mapper(componentModel = "spring", uses = {BookMapper.class})
// //public interface OrderMapper {
// //
// //    // Mapping from Entity to DTO
// //    @Mapping(source = "customer.customerId", target = "customerId")
// //    @Mapping(source = "books", target = "bookIDs", qualifiedByName = "mapBooksToBookIDs")
// //    OrderDTO toDTO(BookOrder order);
// //
// //    // Mapping from DTO to Entity
// //    @Mapping(source = "customerId", target = "customer", qualifiedByName = "mapCustomerIdToCustomer")
// //    @Mapping(source = "bookIDs", target = "books", qualifiedByName = "mapBookIDsToBooks")
// //    BookOrder toEntity(OrderDTO orderDTO);
// //
// //    // Helper Method: Convert List<Book> to List<Integer>
// //    @Named("mapBooksToBookIDs")
// //    default List<Integer> mapBooksToBookIDs(List<Book> books) {
// //        if (books == null) return null;
// //        return books.stream().map(Book::getBookId).collect(Collectors.toList());
// //    }
// //
// //    // Helper Method: Convert List<Integer> to List<Book>
// //    @Named("mapBookIDsToBooks")
// //    default List<Book> mapBookIDsToBooks(List<Integer> bookIDs) {
// //        if (bookIDs == null) return null;
// //        return bookIDs.stream()
// //                .map(bookId -> bookRepository.findById(bookId)
// //                        .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId)))
// //                .collect(Collectors.toList());
// //    }
// //
// //    // Helper Method: Convert CustomerId to Customer
// //    @Named("mapCustomerIdToCustomer")
// //    default Customer mapCustomerIdToCustomer(Integer customerId) {
// //        if (customerId == null) return null;
// //        return customerRepository.findById(customerId)
// //                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + customerId));
// //    }
// //}


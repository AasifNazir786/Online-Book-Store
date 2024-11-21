package com.example.online_book_store.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.online_book_store.dto.OrderDTO;
import com.example.online_book_store.service_repository.OrderRepo;


@Service
public class BookOrderService implements OrderRepo{

    // @Autowired
    // private BookOrderRepository bookOrderRepository;

    @Override
    public OrderDTO create(OrderDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public List<OrderDTO> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public OrderDTO getById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public OrderDTO updateById(int id, OrderDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateById'");
    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    
}
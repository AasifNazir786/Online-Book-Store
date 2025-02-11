// package com.example.online_book_store.controller;


// import com.example.online_book_store.dto.OrderDTO;
// import com.example.online_book_store.service.BookOrderService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;


// @RestController
// @RequestMapping("/api/orders")
// public class OrderController {

//     @Autowired
//     private BookOrderService orderService;

//     @GetMapping
//     public ResponseEntity<List<OrderDTO>> getAllOrders(){
//         List<OrderDTO> orderList = orderService.getAll();

//         if(orderList != null)
//             return new ResponseEntity<>(orderList, HttpStatus.OK);

//         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<OrderDTO> getById(@PathVariable int id){

//         OrderDTO orderDTO = orderService.getById(id);

//         if(orderDTO != null)
//             return new ResponseEntity<>(orderDTO, HttpStatus.OK);

//         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//     }

//     @PostMapping
//     public ResponseEntity<OrderDTO> saveOrder(@RequestBody OrderDTO orderDTO){

//         OrderDTO orderDTO1 = orderService.create(orderDTO);

//         if(orderDTO1 != null)
//             return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);

//         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//     }

//     @PutMapping("/update/{id}")
//     public ResponseEntity<OrderDTO> updateOrder(@PathVariable int id, @RequestBody OrderDTO orderDTO){

//         OrderDTO orderDTO1 = orderService.updateById(id, orderDTO);

//         return new ResponseEntity<>(orderDTO1, HttpStatus.OK);
//     }

//     @DeleteMapping("{id}")
//     public ResponseEntity<Void> deleteOrder(@PathVariable int id){

//         orderService.delete(id);

//         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//     }

// }
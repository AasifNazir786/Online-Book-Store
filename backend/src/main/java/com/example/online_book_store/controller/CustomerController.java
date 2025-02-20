// package com.example.online_book_store.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.online_book_store.dto.CustomerDTO;
// import com.example.online_book_store.service.CustomerService;

// @RestController
// @RequestMapping("/api/customers")
// public class CustomerController {
    
//     @Autowired
//     private CustomerService customerService;

//     @GetMapping("/all")
//     public ResponseEntity<List<CustomerDTO>> getAllCustomers(){

//         List<CustomerDTO> listDTO = customerService.getAll();

//         if(listDTO == null || listDTO.isEmpty()){

//             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//         }
//         return new ResponseEntity<>(listDTO, HttpStatus.OK);
//     }

//     @PostMapping
//     public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) throws Exception{

//         CustomerDTO customerDTO1 = customerService.create(customerDTO);

//         if(customerDTO1 == null || customerDTO1.getCustomerEmail().isEmpty()){

//             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//         }
//         return new ResponseEntity<>(customerDTO1, HttpStatus.CREATED);
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable int id){

//         CustomerDTO customerDTO = customerService.getById(id);

//         if(customerDTO == null){

//             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//         }
//         return new ResponseEntity<>(customerDTO, HttpStatus.OK);
//     }

//     @PutMapping("/update/{id}")
//     public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable int id, @RequestBody CustomerDTO customerDTO){

//         CustomerDTO customerDTO1 = customerService.updateById(id, customerDTO);

//         if(customerDTO1 == null){
            
//             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//         }
//         return new ResponseEntity<>(customerDTO1, HttpStatus.OK);
//     }

//     @DeleteMapping("/delete/{id}")
//     public ResponseEntity<Void> deleteCustomer(@PathVariable int id){

//         customerService.delete(id);

//         return ResponseEntity.noContent().build();
//     }

// }
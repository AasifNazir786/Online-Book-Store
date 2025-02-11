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

// import com.example.online_book_store.dto.AuthorDTO;
// import com.example.online_book_store.service.AuthorService;



// @RestController
// @RequestMapping("/api/authors")
// public class AuthorController {
    
//     @Autowired
//     private AuthorService authorService;


//     @PostMapping("/create")
//     public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO){

//         if(authorDTO == null){

//             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//         }
//         AuthorDTO author = authorService.create(authorDTO);
        
//         return new ResponseEntity<>(author, HttpStatus.CREATED);
//     }

//     @GetMapping("/all")
//     public ResponseEntity<List<AuthorDTO>> getAllAuthors(){

//         List<AuthorDTO> authors = authorService.getAll();

//         return new ResponseEntity<>(authors, HttpStatus.OK);
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable int id){

//         AuthorDTO author = authorService.getById(id);

//         return new ResponseEntity<>(author, HttpStatus.OK);
//     }
    

//     @PutMapping("/update/{id}")
//     public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable int id, @RequestBody AuthorDTO author){

//         AuthorDTO authorDTO = authorService.updateById(id, author);

//         return new ResponseEntity<>(authorDTO, HttpStatus.OK);
//     }

//     @DeleteMapping("/delete/{id}")
//     public void deleteAuthor(@PathVariable int id){

//         authorService.delete(id);
//     }
// }

package com.blog1.controller;
//7th step
import com.blog1.payload.PostDto;
import com.blog1.service.impl.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;
    public PostController(PostService postService) {

        this.postService = postService;

    }
    //http://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping //create a new resource
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){ //1.json data copied to postDto with the help of RequestBody
        // and RequestBody will take this postDto and give it to PostServiceImpl

        if(result.hasErrors()){
         return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PostDto dto = postService.createPost(postDto); //7.dto comes back

        return new ResponseEntity<>(dto, HttpStatus.CREATED); //8. returns dto in Response of postman
    }

    //http://localhost:8080/api/posts?pageNo=1&pageSize=10&sortBy=id&sortDir=desc
    @GetMapping //Get all Data
    public List<PostDto> listAllPosts( //1.postman calls controller
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        List<PostDto> postDtos = postService.listAllPosts(pageNo, pageSize, sortBy, sortDir); //2.controller calls service layer
        return postDtos; //7.return dto back to postman
    }

    //http://localhost:8080/api/posts/1
    @GetMapping("/{id}") // Get data by Id
    public ResponseEntity<PostDto> getPostbyId(@PathVariable("id") long id){ //1.postman calls controller

        PostDto dto = postService.getPostById(id); //2.controller calls service layer

        return new ResponseEntity<>(dto, HttpStatus.OK); //5.returns dto to postman if id exists otherwise message:Post not found with id
    }

    //http://localhost:8080/api/posts/1
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}") //update data by Id
    public ResponseEntity<PostDto> updatePost( //1.json data copied to postDto with the help of RequestBody
            @RequestBody PostDto postDto,     // and RequestBody will take this postDto and give it to PostServiceImpl
            @PathVariable("id") long id
    ){
        PostDto dto = postService.updatePost(id, postDto); //10.gets dto from PostServiceImpl and stores in dto

        return new ResponseEntity<>(dto, HttpStatus.OK); //11.updated dto returns to postman
    }

    @PreAuthorize("hasRole('ADMIN')")
    //http://localhost:8080/api/posts/1
    @DeleteMapping("{id}") // delete data by Id
    public ResponseEntity<String> deletePostByid( @PathVariable("id") long id){ //1.postman calls controller

        postService.deletePostById(id); //2.calls ServiceImpl

        return new ResponseEntity<>("Post is deleted", HttpStatus.OK); //5.if id exists, message: Post is deleted
                                                                              //otherwise message: Post not found with id
        }
    }
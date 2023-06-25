package com.blog1.service.impl;
//6th step
import com.blog1.entity.Post;
import com.blog1.exception.ResourceNotFoundException;
import com.blog1.payload.PostDto;
import com.blog1.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

@Service
public class PostServiceImpl implements PostService{

    private PostRepository postRepository;

    private ModelMapper modelMapper;
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {

        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) { //2.takes postDto from PostController and converts into Entity
        // Dto coverts to Entity
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post newPost = postRepository.save(post); //3.saves the entity in PostRepository and gets save in database
        //4.stores all the saved entity into newPost

        //Entity to Dto
        PostDto dto = new PostDto(); //5. converts the data back to dto
        dto.setId(newPost.getId());
        dto.setTitle(newPost.getTitle());
        dto.setContent(newPost.getContent());
        dto.setDescription(newPost.getDescription());

        return dto; //6.returns the data in the form of dto back to controller
    }

    @Override
    public List<PostDto> listAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> listOfPosts = postRepository.findAll(pageable); //3.finds all the data and stored in listOfPosts

        List<Post> posts = listOfPosts.getContent();//4. Gets all the data from listOfPosts and stored in posts(entity)

        List<PostDto> postDtos = posts.stream().map(post->mapToDto(post)).collect(Collectors.toList()); //5.convert entity into dto
        return postDtos; //6.return dto back to controller
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id: " + id) // 3.check the post with id exists or not
        );
       return mapToDto(post); //4.return dto back to controller if id exists
    }
    @Override
    public PostDto updatePost(long id, PostDto postDto) { //2.takes postDto from PostController
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id: " + id)//3.check the post with id exists or not
        );
        Post newPost = mapToEntity(postDto); //4.Dto converts to Entity by using method mapToEntity and stored in newPost
        newPost.setId(id); //5.set the id

        Post updatedPost = postRepository.save(newPost); //6.saves the entity in PostRepository and gets save in database
             //7.stores all the saved Entity into updatedPost
        PostDto dto = mapToDto(updatedPost); //8.Entity converts into dto by using mapToDto method and stored in dto
        return dto; //9.dto returns to controller
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow( // 3.check the data exists or not
                () -> new ResourceNotFoundException("Post not found with id: " + id)
        );
        postRepository.deleteById(id); //4.if data exists, deleted from database
    }


    PostDto mapToDto(Post post){ //converts Entity to Dto
        PostDto dto = modelMapper.map(post, PostDto.class);
//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setContent(post.getContent());
//        dto.setDescription(post.getDescription());
        return dto;
    }

    Post mapToEntity(PostDto postDto){ //converts Dto to Entity
        Post post = modelMapper.map(postDto, Post.class);
//        Post post = new Post();
//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle());
//        post.setContent(postDto.getContent());
//        post.setDescription(postDto.getDescription());
        return post;
    }
}


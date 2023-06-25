package com.blog1.service.impl;
//5th step
import com.blog1.payload.PostDto;
import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    List<PostDto> listAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(long id, PostDto postDto);

    void deletePostById(long id);
}

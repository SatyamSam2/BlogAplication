package com.blog1.service.impl;

import com.blog1.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto); //create comment

    List<CommentDto> getCommentByPostId(long PostId); //Get data by PostId

    CommentDto getCommentById(Long postId, Long commentId); //Get data by comment Id

    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto); //update comment

    void deleteComment(Long postId, Long commentId); //delete comment
}

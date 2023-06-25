package com.blog1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY) //Many=Comment & One=Post //FetchType.LAZY = load only necessary data from the database
    @JoinColumn(name = "post_id") //same column between two tables(Post & Comment)
    private Post post;// No List of Post because it is one



}

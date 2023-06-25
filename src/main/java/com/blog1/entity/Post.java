package com.blog1.entity;
// 1st step
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Data //getters & setters for variables
@AllArgsConstructor // constructors with declared variable name arguments
@NoArgsConstructor // generate no args or default constructors
@Entity
@Table(
        name = "posts", //table name in database
        uniqueConstraints = @UniqueConstraint(columnNames = {"title"})
)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false) // column name
    private String title;

    @Column(name = "description", nullable = false) //column name
    private String description;

    @Column(name = "content", nullable = false) //column name
    private String content;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post") //One=Post & Many=Comment //cascade=changes in Post also affects comments
    private List<Comment> comments; //List of Comment because it is many

}

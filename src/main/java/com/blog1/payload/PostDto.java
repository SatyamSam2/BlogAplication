package com.blog1.payload;
//4th step
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {

    private long id;

    @NotEmpty
    @Size(min = 2, message = "Title should be more than 2 characters")
    private String title;

    private String description;

    private String content;
}

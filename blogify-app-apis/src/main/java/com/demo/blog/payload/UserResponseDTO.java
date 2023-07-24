package com.demo.blog.payload;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserResponseDTO {

    private int id;

    private String name;

    private String email;

    private String about;

    private Set<CommentsDTO> comments = new HashSet<>();

    // Other fields and methods as needed
}

package com.demo.blog.reposistories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.blog.entity.Comments;

public interface CommentsRepo extends JpaRepository<Comments,Integer> {

}

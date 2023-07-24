package com.demo.blog.reposistories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.blog.entity.Role;

public interface RoleRepo extends JpaRepository<Role,Integer>{

}

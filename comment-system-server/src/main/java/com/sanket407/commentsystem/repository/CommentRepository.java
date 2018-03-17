package com.sanket407.commentsystem.repository;

import com.sanket407.commentsystem.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>
{

    @Query("select s from Comment s where s.parentId is null")
    List<Comment> findAllRootComments();

}

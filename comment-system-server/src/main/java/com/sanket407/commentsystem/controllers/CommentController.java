package com.sanket407.commentsystem.controllers;

import com.sanket407.commentsystem.entity.Comment;
import com.sanket407.commentsystem.entity.User;
import com.sanket407.commentsystem.repository.CommentRepository;
import com.sanket407.commentsystem.repository.UserRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comments")
public class CommentController
{
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private UserRepository userRepository;
       
    @GetMapping()
    public List<Comment> getAllComments()
    {
        return commentRepository.findAll();
    }
    
    @GetMapping("root")
    public List<Comment> getRootComments()
    {
        return commentRepository.findAllRootComments();
    }
    
    @GetMapping("{id}")
    public Comment getCommentbyId(@PathVariable Long id)
    {
        return commentRepository.getOne(id);
    }
    
    @PostMapping("user/{username}")
    public void addRootComment(@PathVariable String username,
                               @RequestBody String content,
                               HttpServletResponse response) throws IOException
    {   
        Comment comment = new Comment(content);
        
        Optional<User> optUser = userRepository.findById(username);
        
        if(optUser.isPresent())
        {
            User user = optUser.get();
            comment.setUser(user);
            user.getComments().add(comment);
            commentRepository.save(comment);
            userRepository.save(user);
        }
        else
        {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Invalid user");
            return;
        }
    }
    
    @PostMapping("user/{username}/parentId/{parentId}")
    public void addReplyComment(@PathVariable String username,
                                @PathVariable Long parentId,
                                @RequestBody String content,
                                HttpServletResponse response) throws IOException
    {   
        Comment comment = new Comment(content);
        
        Optional<User> optUser = userRepository.findById(username);
        
        if(optUser.isPresent())
        {
            User user = optUser.get();
            
            Optional<Comment> optParentComment = commentRepository.findById(parentId);
            if(optParentComment.isPresent())
            {
                Comment parentComment = optParentComment.get();           
                comment.setParent(parentComment);
                parentComment.getChildren().add(comment);
            }
            else
            {
                response.sendError(HttpStatus.BAD_REQUEST.value(), "Invalid parent comment");
                return;
            }

            comment.setUser(user);
            user.getComments().add(comment);
            commentRepository.save(comment);
            userRepository.save(user);
        }
        else
        {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Invalid user");
            return;
        }
    }
    
    @DeleteMapping("{id}")
    public void deleteComment(@PathVariable Long id)
    {
        Comment comment = commentRepository.getOne(id);
        
        Comment parentComment = comment.getParent();
        
        if(parentComment == null)
        {   
            User user = comment.getUser();
            user.getComments().remove(comment);

            comment.getChildren().clear();
            commentRepository.save(comment);
        }    
        else
        {   
            User user = comment.getUser();
            user.getComments().remove(comment);
            parentComment.getChildren().remove(comment);

            for(Comment child : comment.getChildren())
              {
                  child.setParent(null);
              }  
            commentRepository.save(parentComment);
        }
    }
}

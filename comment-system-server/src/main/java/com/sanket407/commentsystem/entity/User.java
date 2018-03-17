package com.sanket407.commentsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "inspection"})
public class User
{
    @Id
    @NotBlank
    @Column(name = "username")
    private String username;
    
    @NotBlank
    @Column(name = "password")
    private String password;
       
    @JsonManagedReference(value="user-comment")
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<Comment>();
    
    public User() 
    {
        
    }
    
    public User(String username, String password, Set<Comment> comments)
    {
        this.username = username;
        this.password = password;
        this.comments = comments;
    }
    
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Set<Comment> getComments()
    {
        return comments;
    }

    public void setComments(Set<Comment> comments)
    {
        this.comments = comments;
    }

    @Override
    public String toString()
    {
        return "User [username=" + username + ", password=" + password + ", comments=" + comments + "]";
    }
      
}  
    
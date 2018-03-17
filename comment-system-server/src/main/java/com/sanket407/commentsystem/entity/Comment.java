package com.sanket407.commentsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sanket407.commentsystem.serializers.CommentSerializer;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comments")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "inspection"})
@JsonSerialize(using = CommentSerializer.class)
public class Comment
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    @NotBlank
    @Column(name = "content")
    private String content;
    
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "created_on")
    private Date createdOn;
    
    @Column(name = "parent_id", insertable = false, updatable = false)
    private Long parentId;

    @Column(name = "username", insertable = false, updatable = false)
    private String username;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    @JsonBackReference(value="comment-comment")
    private Comment parent;

    @JsonManagedReference(value="comment-comment")
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Comment> children = new HashSet<Comment>();
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    @JsonBackReference(value="user-comment")
    private User user;
    
    
    
    public Comment()
    {
        
    }
    
    public Comment(String content)
    {
        this.content = content;        
    }
    
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Date getTime()
    {
        return createdOn;
    }

    public void setTime(Date time)
    {
        this.createdOn = time;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Comment getParent()
    {
        return parent;
    }

    public void setParent(Comment parent)
    {
        this.parent = parent;
    }

    public Set<Comment> getChildren()
    {
        return children;
    }

    public void setChildren(Set<Comment> children)
    {
        this.children = children;
    }
    
    
    
    public void setCreatedOn(Date createdOn)
    {
        this.createdOn = createdOn;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
    
    public User getUser()
    {
        return this.user;
    }

    @Override
    public String toString()
    {
        return "Comment [id=" + id + ", content=" + content + ", createdOn=" + createdOn + ", parent=" + (parent == null ? null : parent.getId()) + ", children=" + children + "]";
    }
}

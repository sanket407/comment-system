package com.sanket407.commentsystem.serializers;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.sanket407.commentsystem.entity.Comment;
import com.sanket407.commentsystem.entity.User;
import java.io.IOException;

public class CommentSerializer extends StdSerializer<Comment>
{
    public CommentSerializer(Class<Comment> t) {
        super(t);
    }
    public CommentSerializer() {
        this(null);
    }
    @Override
    public void serialize(Comment comment, JsonGenerator jg, SerializerProvider sp)
            throws IOException, JsonGenerationException {
        
        jg.writeStartObject();
        jg.writeNumberField("id", comment.getId());
        jg.writeStringField("content", comment.getContent());
        jg.writeStringField("time", comment.getTime().toString());
        jg.writeObjectField("replies", comment.getChildren());
       
        User user = comment.getUser();
        System.out.println("comment " + comment.getContent() + "user " + user);
        jg.writeStringField("username", user.getUsername());
        jg.writeEndObject();
    }
}

package kayleh.wizard.community.mapper;

import kayleh.wizard.community.model.Comment;
import org.springframework.stereotype.Component;

@Component
public interface CommentExtMapper {
    int inCommentCount(Comment comment);
}
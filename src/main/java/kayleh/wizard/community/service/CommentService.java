package kayleh.wizard.community.service;

import kayleh.wizard.community.enums.CommentTypeEnum;
import kayleh.wizard.community.exception.CustomizeErrorCode;
import kayleh.wizard.community.exception.CustomizeException;
import kayleh.wizard.community.mapper.CommentMapper;
import kayleh.wizard.community.mapper.QuestionExtMapper;
import kayleh.wizard.community.mapper.QuestionMapper;
import kayleh.wizard.community.model.Comment;
import kayleh.wizard.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Wizard
 * @Date: 2020/5/25 18:04
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbcomment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbcomment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        } else {
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            //回复问题
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            //评论数加1
            question.setCommentCount(1);
            questionExtMapper.inCommentCount(question);
        }
    }
}

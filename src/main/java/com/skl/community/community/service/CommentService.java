package com.skl.community.community.service;

import com.skl.community.community.enums.CommentTypeEnum;
import com.skl.community.community.exception.CommunityErrorCode;
import com.skl.community.community.exception.CommunityException;
import com.skl.community.community.mapper.CommentMapper;
import com.skl.community.community.mapper.QuestionExtMapper;
import com.skl.community.community.mapper.QuestionMapper;
import com.skl.community.community.model.Comment;
import com.skl.community.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

  @Autowired
  private CommentMapper commentMapper;

  @Autowired
  private QuestionMapper questionMapper;

  @Autowired
  private QuestionExtMapper questionExtMapper;

  public void insert(Comment comment) {
    if(comment.getParentId()==null||comment.getParentId()==0){
      throw new CommunityException(CommunityErrorCode.TARGET_PARAM_NOT_FIND);
    }

    if(comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){
      throw new CommunityException(CommunityErrorCode.TYPE_PARAM_WRONG);
    }

    if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
      // 回复评论
      Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
      if(dbComment==null){
        throw new CommunityException(CommunityErrorCode.COMMENT_NOT_FOUND);
      }
      commentMapper.insert(comment);
    }else {
      // 回复问题
      Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
      if(question==null){
        throw new CommunityException(CommunityErrorCode.QUESTION_NOT_FOUND);
      }
      commentMapper.insert(comment);
      question.setCommentCount(1);
      questionExtMapper.incCommentCount(question);
    }
  }
}

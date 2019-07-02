package com.skl.community.community.service;

import com.skl.community.community.dto.CommentDTO;
import com.skl.community.community.enums.CommentTypeEnum;
import com.skl.community.community.exception.CommunityErrorCode;
import com.skl.community.community.exception.CommunityException;
import com.skl.community.community.mapper.*;
import com.skl.community.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

  @Autowired
  private CommentMapper commentMapper;

  @Autowired
  private QuestionMapper questionMapper;

  @Autowired
  private QuestionExtMapper questionExtMapper;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private CommentExtMapper commentExtMapper;

  @Transactional
  public void insert(Comment comment) {
    if (comment.getParentId() == null || comment.getParentId() == 0) {
      throw new CommunityException(CommunityErrorCode.TARGET_PARAM_NOT_FIND);
    }

    if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
      throw new CommunityException(CommunityErrorCode.TYPE_PARAM_WRONG);
    }

    if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
      // 回复评论
      Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
      if (dbComment == null) {
        throw new CommunityException(CommunityErrorCode.COMMENT_NOT_FOUND);
      }
      commentMapper.insert(comment);

      // 增加评论数
      Comment parentComment = new Comment();
      parentComment.setId(comment.getParentId());
      parentComment.setCommentCount(1);
      commentExtMapper.incCommentCount(parentComment);
    } else {
      // 回复问题
      Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
      if (question == null) {
        throw new CommunityException(CommunityErrorCode.QUESTION_NOT_FOUND);
      }
      commentMapper.insert(comment);
      question.setCommentCount(1);
      questionExtMapper.incCommentCount(question);
    }
  }

  public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
    CommentExample commentExample = new CommentExample();
    commentExample.createCriteria()
        .andParentIdEqualTo(id)
        .andTypeEqualTo(type.getType());
    //按gmt_create 排序
    commentExample.setOrderByClause("gmt_create desc");
    List<Comment> commentList = commentMapper.selectByExample(commentExample);

    if (commentList.size() == 0) {
      return new ArrayList<>();
    }
    // 拿到所有的评论者,去重
    Set<Long> commentators = commentList.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());

    List<Long> userIds = new ArrayList<>();
    userIds.addAll(commentators);
    // 获取评论人并转换为map
    UserExample userExample = new UserExample();
    userExample.createCriteria().andIdIn(userIds);
    List<User> users = userMapper.selectByExample(userExample);
    Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

    // 转换comment为commentDTO
    List<CommentDTO> commentDTOS = commentList.stream().map(comment -> {
      CommentDTO commentDTO = new CommentDTO();
      BeanUtils.copyProperties(comment, commentDTO);
      commentDTO.setUser(userMap.get(comment.getCommentator()));
      return commentDTO;
    }).collect(Collectors.toList());

    return commentDTOS;
  }
}

package com.skl.community.community.service;


import com.skl.community.community.dto.PaginationDTO;
import com.skl.community.community.dto.QuestionDTO;
import com.skl.community.community.exception.CommunityErrorCode;
import com.skl.community.community.exception.CommunityException;
import com.skl.community.community.mapper.QuestionExtMapper;
import com.skl.community.community.mapper.QuestionMapper;
import com.skl.community.community.mapper.UserMapper;
import com.skl.community.community.model.Question;
import com.skl.community.community.model.QuestionExample;
import com.skl.community.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private QuestionMapper questionMapper;

  @Autowired
  private QuestionExtMapper questionExtMapper;
  
  public PaginationDTO list(Integer page, Integer size) {
    PaginationDTO paginationDTO = new PaginationDTO();

    Integer totalPage;
    Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());

    if(totalCount%size==0){
      totalPage = totalCount/size;
    }else {
      totalPage = totalCount/size+1;
    }

    if(page<1){
      page=1;
    }

    if(page>totalPage){
      page=totalPage;
    }

    paginationDTO.setPagination(totalPage,page);

    Integer offset  = size * (page-1);
    List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
    List<QuestionDTO> questionDTOList = new ArrayList<>();




    for (Question question:questions){
      User user = userMapper.selectByPrimaryKey(question.getCreator());
      QuestionDTO questionDTO = new QuestionDTO();
      BeanUtils.copyProperties(question,questionDTO);
      questionDTO.setUser(user);
      questionDTOList.add(questionDTO);
    }
    paginationDTO.setQuestions(questionDTOList);




    return paginationDTO;

  }

  public PaginationDTO list(Long userId, Integer page, Integer size) {
    PaginationDTO paginationDTO = new PaginationDTO();
    Integer totalPage;
    QuestionExample questionExample = new QuestionExample();
    questionExample.createCriteria().andCreatorEqualTo(userId);
    Integer totalCount = (int)questionMapper.countByExample(questionExample);

    if(totalCount%size==0){
      totalPage = totalCount/size;
    }else {
      totalPage = totalCount/size+1;
    }

    if(page<1){
      page=1;
    }

    if(page>totalPage){
      page=totalPage;
    }

    paginationDTO.setPagination(totalPage,page);


    Integer offset  = size * (page-1);
    QuestionExample example = new QuestionExample();
    example.createCriteria().andCreatorEqualTo(userId);
    List<Question> questions = questionMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));



    List<QuestionDTO> questionDTOList = new ArrayList<>();




    for (Question question:questions){
      User user = userMapper.selectByPrimaryKey(question.getCreator());
      QuestionDTO questionDTO = new QuestionDTO();
      BeanUtils.copyProperties(question,questionDTO);
      questionDTO.setUser(user);
      questionDTOList.add(questionDTO);
    }
    paginationDTO.setQuestions(questionDTOList);
    return paginationDTO;
  }

  public QuestionDTO getById(Long id) {
    Question question = questionMapper.selectByPrimaryKey(id);
    if (question==null){
      throw new CommunityException(CommunityErrorCode.QUESTION_NOT_FOUND);
    }


    QuestionDTO questionDTO = new QuestionDTO();
    BeanUtils.copyProperties(question,questionDTO);
    User user = userMapper.selectByPrimaryKey(question.getCreator());
    questionDTO.setUser(user);
    return questionDTO;
  }

  public void createOrUpdate(Question question) {
    if (question.getId() == null) {
      // 创建
      question.setGmtCreate(System.currentTimeMillis());
      question.setGmtModified(question.getGmtCreate());
      question.setViewCount(0);
      question.setLikeCount(0);
      question.setCommentCount(0);
      questionMapper.insert(question);
    } else {
      // 更新
      Question updateQuestion = new Question();
      updateQuestion.setGmtModified(System.currentTimeMillis());
      updateQuestion.setTitle(question.getTitle());
      updateQuestion.setDescription(question.getDescription());
      updateQuestion.setTag(question.getTag());
      QuestionExample example = new QuestionExample();
      example.createCriteria().andIdEqualTo(question.getId());
      int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
      // 如果更新的时候，别人已经删了这条问题
      // 更新success就是1
      if(updated !=1){
        throw new CommunityException(CommunityErrorCode.QUESTION_NOT_FOUND);
      }
    }
  }

  public void incView(Long id) {
    Question question = new Question();
    question.setId(id);
    question.setViewCount(1);
    questionExtMapper.incView(question);
  }
}

package com.skl.community.community.service;


import com.skl.community.community.dto.QuestionDTO;
import com.skl.community.community.mapper.QuestionMapper;
import com.skl.community.community.mapper.UserMapper;
import com.skl.community.community.model.Question;
import com.skl.community.community.model.User;
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
  
  public List<QuestionDTO> list() {
    List<Question> questions = questionMapper.list();
    List<QuestionDTO> questionDTOList = new ArrayList<>();
    for (Question question:questions){
      User user = userMapper.findById(question.getCreator());
      QuestionDTO questionDTO = new QuestionDTO();
      BeanUtils.copyProperties(question,questionDTO);
      questionDTO.setUser(user);
      questionDTOList.add(questionDTO);
    }
    return questionDTOList;

  }
}

package com.skl.community.community.schedule;

import com.skl.community.community.chche.HotTagCache;
import com.skl.community.community.mapper.QuestionMapper;
import com.skl.community.community.model.Question;
import com.skl.community.community.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class HotTagTasks {


  @Autowired
  private QuestionMapper questionMapper;

  @Autowired
  private HotTagCache hotTagCache;

  @Scheduled(fixedRate = 1000 * 60)// 3 hour
  public void hotTagSchedule() {
    int offset = 0;
    int limit = 20;
    log.info("hotTagSchedule start {}", new Date());
    List<Question> list = new ArrayList<>();

    Map<String, Integer> priorities = new HashMap<>();
    while (offset == 0 || list.size() == limit) {
      list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
      for (Question question : list) {
        String[] tags = StringUtils.split(question.getTag(), ",");
        for (String tag : tags) {
          Integer priority = priorities.get(tag);
          if (priority != null) {
            //热度标签算法： priority=5* 问题数+问题的回复数
            priorities.put(tag, priority + 5 + question.getCommentCount());
          } else {
            priorities.put(tag, 5 + question.getCommentCount());
          }
        }
      }
      offset += limit;
    }
    hotTagCache.updateTags(priorities);
    log.info("hotTagSchedule stop {}", new Date());
  }
}

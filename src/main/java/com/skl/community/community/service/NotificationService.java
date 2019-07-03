package com.skl.community.community.service;


import com.skl.community.community.dto.NotificationDTO;
import com.skl.community.community.dto.PaginationDTO;
import com.skl.community.community.enums.NotificationStatusEnum;
import com.skl.community.community.enums.NotificationTypeEnum;
import com.skl.community.community.exception.CommunityErrorCode;
import com.skl.community.community.exception.CommunityException;
import com.skl.community.community.mapper.NotificationMapper;
import com.skl.community.community.mapper.UserMapper;
import com.skl.community.community.model.Notification;
import com.skl.community.community.model.NotificationExample;
import com.skl.community.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {

  @Autowired
  private NotificationMapper notificationMapper;

  @Autowired
  private UserMapper userMapper;

  public PaginationDTO list(Long userId, Integer page, Integer size) {
    PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
    Integer totalPage;
    NotificationExample notificationExample = new NotificationExample();
    notificationExample.createCriteria().andReceiverEqualTo(userId);
    notificationExample.setOrderByClause("gmt_create desc");
    Integer totalCount = (int) notificationMapper.countByExample(notificationExample);

    if (totalCount % size == 0) {
      totalPage = totalCount / size;
    } else {
      totalPage = totalCount / size + 1;
    }

    if (page < 1) {
      page = 1;
    }

    if (page > totalPage) {
      page = totalPage;
    }

    paginationDTO.setPagination(totalPage, page);


    Integer offset = size * (page - 1);
    NotificationExample example = new NotificationExample();
    example.createCriteria().andReceiverEqualTo(userId);
    example.setOrderByClause("gmt_create desc");
    List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
    if (notifications.size() == 0) {
      return paginationDTO;
    }

//    Set<Long> disUserIds = notifications.stream().map(notify -> notify.getReceiver()).collect(Collectors.toSet());
//    List<Long> userIds = new ArrayList<>(disUserIds);
//    UserExample userExample = new UserExample();
//    userExample.createCriteria().andIdIn(userIds);
//    List<User> users = userMapper.selectByExample(userExample);
//
//    Map<Long, User> userMap = users.stream().collect(Collectors.toMap(u -> u.getId(), u -> u));


    List<NotificationDTO> notificationDTOS = new ArrayList<>();
    for (Notification notification : notifications) {
      NotificationDTO notificationDTO = new NotificationDTO();
      BeanUtils.copyProperties(notification, notificationDTO);
      notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
      notificationDTOS.add(notificationDTO);
    }
    paginationDTO.setData(notificationDTOS);
    return paginationDTO;
  }

  public Long unreadCount(Long id) {
    NotificationExample notificationExample = new NotificationExample();
    notificationExample.createCriteria().andReceiverEqualTo(id)
        .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
    ;
    return notificationMapper.countByExample(notificationExample);
  }

  public NotificationDTO read(Long id, User user) {
    Notification notification = notificationMapper.selectByPrimaryKey(id);
    if (notification == null) {
      throw new CommunityException(CommunityErrorCode.NOTIFICATION_NOT_FOUND);
    }
    // 接受着不是你自己的话。。。。
    if (!Objects.equals(notification.getReceiver(), user.getId())) {
      throw new CommunityException(CommunityErrorCode.READ_NOTIFICATION_FAIL);
    }

    notification.setStatus(NotificationStatusEnum.READ.getStatus());
    notificationMapper.updateByPrimaryKey(notification);

    NotificationDTO notificationDTO = new NotificationDTO();
    BeanUtils.copyProperties(notification, notificationDTO);
    notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
    return notificationDTO;
  }
}

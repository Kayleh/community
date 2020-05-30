package kayleh.wizard.community.service;

import kayleh.wizard.community.dto.NotificationDTO;
import kayleh.wizard.community.dto.PaginationDTO;
import kayleh.wizard.community.enums.NotificationStatusEnum;
import kayleh.wizard.community.enums.NotificationTypeEnum;
import kayleh.wizard.community.exception.CustomizeErrorCode;
import kayleh.wizard.community.exception.CustomizeException;
import kayleh.wizard.community.mapper.NotificationMapper;
import kayleh.wizard.community.mapper.UserMapper;
import kayleh.wizard.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: Wizard
 * @Date: 2020/5/30 14:21
 */

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;


    public PaginationDTO list(Long userId, Integer page, Integer size) {


        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();

        Integer totalPage;

        //所有的数量
        NotificationExample notificationExample = new NotificationExample();
        ;
        notificationExample.createCriteria().andReceiverEqualTo(userId);
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


        // size*(page-1)
        Integer offset = size * (page - 1);

        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId);
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        if (notifications.size() == 0) {
            return paginationDTO;
        }

//        Set<Long> disUserIds = notifications.stream().map(notify -> notify.getNotifier()).collect(Collectors.toSet());
//        ArrayList<Long> userIds = new ArrayList<>(disUserIds);
//        UserExample example = new UserExample();
//        example.createCriteria().andIdIn(userIds);
//        List<User> users = userMapper.selectByExample(example);
//
//        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(u -> u.getId(), u -> u));

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

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId).andStatusEqualTo(NotificationStatusEnum.UN_READ.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}

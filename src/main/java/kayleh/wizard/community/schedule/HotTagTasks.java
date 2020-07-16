package kayleh.wizard.community.schedule;

import kayleh.wizard.community.cache.HotTagCache;
import kayleh.wizard.community.mapper.QuestionMapper;
import kayleh.wizard.community.model.Question;
import kayleh.wizard.community.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author: Wizard
 * @Date: 2020/6/26 16:46
 */
@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 1000*60*5)
//    @Scheduled(fixedRate = 1000 * 60 * 60 * 3)
//    @Scheduled(cron = "0 0 1 * * *")  // 每天凌晨一点更新
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
                        priorities.put(tag, priority + 5 + question.getCommentCount() + question.getViewCount());
                    } else {
                        priorities.put(tag, 5 + question.getCommentCount() + question.getViewCount());
                    }
                }
//                log.info("list question : {}", question.getId());
            }
            offset += limit;
        }
//        hotTagCache.setTags(priorities);

//        hotTagCache.getTags().forEach(

//        priorities.forEach(
//
//                (k, v) -> {
//                    System.out.print(k);
//                    System.out.print(":");
//                    System.out.print(v);
//                    System.out.println();
//                }
//        );
        hotTagCache.updatesTags(priorities);


        log.info("hotTagSchedule stop {}", new Date());
    }
}

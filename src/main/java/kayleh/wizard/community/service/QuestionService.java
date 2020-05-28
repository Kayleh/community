package kayleh.wizard.community.service;

import kayleh.wizard.community.dto.PaginationDTO;
import kayleh.wizard.community.dto.QuestionDTO;
import kayleh.wizard.community.exception.CustomizeErrorCode;
import kayleh.wizard.community.exception.CustomizeException;
import kayleh.wizard.community.mapper.QuestionExtMapper;
import kayleh.wizard.community.mapper.QuestionMapper;
import kayleh.wizard.community.mapper.UserMapper;
import kayleh.wizard.community.model.Question;
import kayleh.wizard.community.model.QuestionExample;
import kayleh.wizard.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/5 11:32
 */
@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;


    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;

        //所有的数量
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());


        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }


        paginationDTO.setPagination(totalCount,page,size);

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        // size*(page-1)
        Integer offset = size * (page - 1);

        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,size));


        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {



            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
//        return questionDTOList;
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;

        //所有的数量
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(example);


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

        paginationDTO.setPagination(totalPage,page);


        // size*(page-1)
        Integer offset = size * (page - 1);

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));

//        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
//        return questionDTOList;
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);

        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);


        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId()==null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            //用insertSelective
            questionMapper.insertSelective(question);
        }else {
            //更新
//            question.setGmtModified(System.currentTimeMillis());

            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int update = questionMapper.updateByExampleSelective(updateQuestion, example);
            if (update!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void inView(Long id) {


        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.inView(question);
    }
}
//
//@Service
//public class QuestionService {
//
//    @Autowired
//    private QuestionMapper questionMapper;
//
//    @Autowired
//    private QuestionExtMapper questionExtMapper;
//
//    @Autowired
//    private UserMapper userMapper;
//
//    public PaginationDTO list(String search, String tag, String sort, Integer page, Integer size) {
//
//        if (StringUtils.isNotBlank(search)) {
//            String[] tags = StringUtils.split(search, " ");
//            search = Arrays
//                    .stream(tags)
//                    .filter(StringUtils::isNotBlank)
//                    .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
//                    .filter(StringUtils::isNotBlank)
//                    .collect(Collectors.joining("|"));
//        }
//
//        PaginationDTO paginationDTO = new PaginationDTO();
//
//        Integer totalPage;
//
//        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
//        questionQueryDTO.setSearch(search);
//        if (StringUtils.isNotBlank(tag)) {
//            tag = tag.replace("+", "").replace("*", "").replace("?", "");
//            questionQueryDTO.setTag(tag);
//        }
//
//        for (SortEnum sortEnum : SortEnum.values()) {
//            if (sortEnum.name().toLowerCase().equals(sort)) {
//                questionQueryDTO.setSort(sort);
//
//                if (sortEnum == SortEnum.HOT7) {
//                    questionQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 7);
//                }
//                if (sortEnum == SortEnum.HOT30) {
//                    questionQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30);
//                }
//                break;
//            }
//        }
//
//        Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);
//
//        if (totalCount % size == 0) {
//            totalPage = totalCount / size;
//        } else {
//            totalPage = totalCount / size + 1;
//        }
//
//        if (page < 1) {
//            page = 1;
//        }
//        if (page > totalPage) {
//            page = totalPage;
//        }
//
//        paginationDTO.setPagination(totalPage, page);
//        Integer offset = page < 1 ? 0 : size * (page - 1);
//        questionQueryDTO.setSize(size);
//        questionQueryDTO.setPage(offset);
//        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
//        List<QuestionDTO> questionDTOList = new ArrayList<>();
//
//        for (Question question : questions) {
//            User user = userMapper.selectByPrimaryKey(question.getCreator());
//            QuestionDTO questionDTO = new QuestionDTO();
//            BeanUtils.copyProperties(question, questionDTO);
//            questionDTO.setUser(user);
//            questionDTOList.add(questionDTO);
//        }
//
//        paginationDTO.setData(questionDTOList);
//        return paginationDTO;
//    }
//
//    public PaginationDTO list(Long userId, Integer page, Integer size) {
//        PaginationDTO paginationDTO = new PaginationDTO();
//
//        Integer totalPage;
//
//        QuestionExample questionExample = new QuestionExample();
//        questionExample.createCriteria()
//                .andCreatorEqualTo(userId);
//        Integer totalCount = (int) questionMapper.countByExample(questionExample);
//
//        if (totalCount % size == 0) {
//            totalPage = totalCount / size;
//        } else {
//            totalPage = totalCount / size + 1;
//        }
//
//        if (page < 1) {
//            page = 1;
//        }
//        if (page > totalPage) {
//            page = totalPage;
//        }
//
//        paginationDTO.setPagination(totalPage, page);
//
//        //size*(page-1)
//        Integer offset = size * (page - 1);
//        QuestionExample example = new QuestionExample();
//        example.createCriteria()
//                .andCreatorEqualTo(userId);
//        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
//        List<QuestionDTO> questionDTOList = new ArrayList<>();
//
//        for (Question question : questions) {
//            User user = userMapper.selectByPrimaryKey(question.getCreator());
//            QuestionDTO questionDTO = new QuestionDTO();
//            BeanUtils.copyProperties(question, questionDTO);
//            questionDTO.setUser(user);
//            questionDTOList.add(questionDTO);
//        }
//
//        paginationDTO.setData(questionDTOList);
//        return paginationDTO;
//    }
//
//    public QuestionDTO getById(Long id) {
//        Question question = questionMapper.selectByPrimaryKey(id);
//        if (question == null) {
//            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
//        }
//        QuestionDTO questionDTO = new QuestionDTO();
//        BeanUtils.copyProperties(question, questionDTO);
//        User user = userMapper.selectByPrimaryKey(question.getCreator());
//        questionDTO.setUser(user);
//        return questionDTO;
//    }
//
//    public void createOrUpdate(Question question) {
//        if (question.getId() == null) {
//            // 创建
//            question.setGmtCreate(System.currentTimeMillis());
//            question.setGmtModified(question.getGmtCreate());
//            question.setViewCount(0);
//            question.setLikeCount(0);
//            question.setCommentCount(0);
//            questionMapper.insert(question);
//        } else {
//            // 更新
//
//            Question dbQuestion = questionMapper.selectByPrimaryKey(question.getId());
//            if (dbQuestion == null) {
//                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
//            }
//
//            if (dbQuestion.getCreator().longValue() != question.getCreator().longValue()) {
//                throw new CustomizeException(CustomizeErrorCode.INVALID_OPERATION);
//            }
//
//            Question updateQuestion = new Question();
//            updateQuestion.setGmtModified(System.currentTimeMillis());
//            updateQuestion.setTitle(question.getTitle());
//            updateQuestion.setDescription(question.getDescription());
//            updateQuestion.setTag(question.getTag());
//            QuestionExample example = new QuestionExample();
//            example.createCriteria()
//                    .andIdEqualTo(question.getId());
//            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
//            if (updated != 1) {
//                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
//            }
//        }
//    }
//
//    public void incView(Long id) {
//        Question question = new Question();
//        question.setId(id);
//        question.setViewCount(1);
//        questionExtMapper.incView(question);
//    }
//
//    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
//        if (StringUtils.isBlank(queryDTO.getTag())) {
//            return new ArrayList<>();
//        }
//        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
//        String regexpTag = Arrays
//                .stream(tags)
//                .filter(StringUtils::isNotBlank)
//                .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
//                .filter(StringUtils::isNotBlank)
//                .collect(Collectors.joining("|"));
//        Question question = new Question();
//        question.setId(queryDTO.getId());
//        question.setTag(regexpTag);
//
//        List<Question> questions = questionExtMapper.selectRelated(question);
//        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
//            QuestionDTO questionDTO = new QuestionDTO();
//            BeanUtils.copyProperties(q, questionDTO);
//            return questionDTO;
//        }).collect(Collectors.toList());
//        return questionDTOS;
//    }
//}

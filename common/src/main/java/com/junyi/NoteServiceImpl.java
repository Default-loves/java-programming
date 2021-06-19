package com.junyi;

import com.junyi.entity.CommonResponse;
import com.junyi.entity.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @time: 2021/1/14 10:06
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Service("noteService")
public class NoteServiceImpl implements NoteService {

    @Resource
    private SearchService searchService;


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public CommonResponse<Person> save(Person note) {
        // 一系列 DB 操作

        try {
            searchService.sync(note);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResponse.success(note);
    }

}
package com.junyi;

import com.junyi.entity.Person;

/**
 * @time: 2021/1/14 10:06
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public interface NoteService {

    public CommonResponse<Person> save(Person note); }


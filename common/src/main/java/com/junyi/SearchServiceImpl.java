package com.junyi;

import com.junyi.entity.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @time: 2021/1/14 10:09
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Service("searchService")
public class SearchServiceImpl implements SearchService {

        @Transactional(rollbackFor = Throwable.class)
        @Override
        public void sync(Person note) {
            // 一系列 DB 操作

            throw new RuntimeException("同步异常! [XXX]");
        }

}

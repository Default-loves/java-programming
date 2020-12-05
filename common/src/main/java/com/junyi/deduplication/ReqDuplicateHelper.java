package com.junyi.deduplication;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * @time: 2020/12/3 11:31
 * @version: 1.0
 * @author: junyi Xu
 * @description: 将 JSON 剔除掉指定的 Key 后，计算 MD5
 */
@Slf4j
public class ReqDuplicateHelper {
    /**
     *
     * @param reqJSON 请求的参数，这里通常是JSON
     * @param excludeKeys 请求参数里面要去除哪些字段再求摘要
     * @return 去除参数的MD5摘要
     */
    public String dedupParamMD5(final String reqJSON, String... excludeKeys) {
        String decreptParam = reqJSON;

        TreeMap paramTreeMap = JSON.parseObject(decreptParam, TreeMap.class);
        if (excludeKeys!=null) {
            List<String> dedupExcludeKeys = Arrays.asList(excludeKeys);
            if (!dedupExcludeKeys.isEmpty()) {
                for (String dedupExcludeKey : dedupExcludeKeys) {
                    paramTreeMap.remove(dedupExcludeKey);
                }
            }
        }

        String paramTreeMapJSON = JSON.toJSONString(paramTreeMap);
        String md5deDupParam = jdkMD5(paramTreeMapJSON);
        log.debug("md5deDupParam = {}, excludeKeys = {} {}", md5deDupParam, Arrays.deepToString(excludeKeys), paramTreeMapJSON);
        return md5deDupParam;
    }

    /** 使用 MD5 算法获得摘要 */
    private static String jdkMD5(String src) {
        String res = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] mdBytes = messageDigest.digest(src.getBytes());
            res = DatatypeConverter.printHexBinary(mdBytes);
        } catch (Exception e) {
            log.error("",e);
        }
        return res;
    }

    @Test
    public void testReqDuplicateHelper() {
        //两个请求一样，但是请求时间差一秒
        String req = "{\n" +
                "\"requestTime\" :\"20190101120001\",\n" +
                "\"requestValue\" :\"1000\",\n" +
                "\"requestKey\" :\"key\"\n" +
                "}";

        String req2 = "{\n" +
                "\"requestTime\" :\"20190101120002\",\n" +
                "\"requestValue\" :\"1000\",\n" +
                "\"requestKey\" :\"key\"\n" +
                "}";

        //全参数比对，所以两个参数MD5不同
        ReqDuplicateHelper helper = new ReqDuplicateHelper();
        String dedupMD5 = helper.dedupParamMD5(req);
        String dedupMD52 = helper.dedupParamMD5(req2);
        System.out.println("req1MD5 = "+ dedupMD5+" , req2MD5="+dedupMD52);

        //去除时间参数比对，MD5相同
        String dedupMD53 = helper.dedupParamMD5(req,"requestTime");
        String dedupMD54 = helper.dedupParamMD5(req2,"requestTime");
        System.out.println("req1MD5 = "+ dedupMD53+" , req2MD5="+dedupMD54);
    }
}

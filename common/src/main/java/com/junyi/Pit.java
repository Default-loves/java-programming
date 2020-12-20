package com.junyi;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @time: 2020/11/19 15:28
 * @version: 1.0
 * @author: junyi Xu
 * @description: 一些容易错误的
 */
@Slf4j
public class Pit {
    public static void main(String[] args) throws NullException {
        Pit pit = new Pit();
        pit.listIsNullFix();
    }

    /** NullPointerException,  */
    public void listIsNull() {
        ArrayList<Integer> list = null;
        for (Integer t: list) {
            log.info(t + "");
        }
    }
    public void listIsNullFix() throws NullException {
        ArrayList<Integer> list = null;
        list = Optional.ofNullable(list).orElseThrow(NullException::new);
        for (Integer t: list) {
            log.info(t + "");
        }
    }
    private class NullException extends Throwable {

        public NullException() {}

        public NullException(String msg) {super(msg);}

        @Override
        public String getMessage() {
            String msg = "list is empty\t" + super.getMessage();
            return msg;
        }
    }
}

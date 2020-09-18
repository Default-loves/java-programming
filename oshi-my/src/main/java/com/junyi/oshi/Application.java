package com.junyi.oshi;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

/**
 * @time: 2020/9/18 9:35
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class Application {

    public static void main(String[] args) {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor cpu = hal.getProcessor();
        GlobalMemory memory = hal.getMemory();
        System.out.println(cpu);
        System.out.println(memory);
        System.out.println(hal.getGraphicsCards());
        System.out.println(hal.getSensors());
    }
}

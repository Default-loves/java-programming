package com.junyi.pattern;

import lombok.Data;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Copy On Write 模式：该模式的思想是写入的时候，复制原始数据，修改的是复制的数据，待修改完毕后再将复制数据覆盖原始数据
 * 适用场景：读多写少的场景
 *
 * 例子：接口的路由表，读多写少，当服务实例上线/下线时在对应的接口中添加/删除相应的路由信息
 * @time: 2021/5/8 14:18
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class CopyOnWriteMy {

    class RouterTable {
        private ConcurrentHashMap<String, CopyOnWriteArraySet<Router>> table = new ConcurrentHashMap<>();


        public Set<Router> get(String iface) {
            return table.get(iface);
        }
        public void add(Router router) {
            Set<Router> routers = table.computeIfAbsent(router.iface, r -> new CopyOnWriteArraySet<>());
            routers.add(router);
        }

        public void remove(Router router) {
            CopyOnWriteArraySet<Router> routers = table.get(router.getIface());
            routers.remove(router);
        }
    }


    @Data
    class Router{
        private String ip;      // ip
        private String port;    // 端口
        private String iface;   // 接口

        public Router() {
        }

        public Router(String ip, String port, String iface) {
            this.ip = ip;
            this.port = port;
            this.iface = iface;
        }
    }
}

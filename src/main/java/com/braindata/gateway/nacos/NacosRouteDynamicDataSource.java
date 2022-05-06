package com.braindata.gateway.nacos;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/2/10 12:13 下午
 */

//@Component
@Slf4j
public class NacosRouteDynamicDataSource implements ApplicationRunner {
    @Autowired
    private NacosConfigManager configManager;

    private ConfigService configService;
    @PostConstruct
    void init () {
        configService = configManager.getConfigService();
    }
    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info(args.toString());
        //监听nacos配置变化
        dynamicRouteByNacosListener("gateway-dynamic-route-rule.json", "gateway-dynamic-route-rule");
        //心跳 顺便 初始化
        while (true) {
            String configInfo = configService.getConfig("gateway-dynamic-route-rule.json", "gateway-dynamic-route-rule", 4000);
            log.info("configInfo = {}", configInfo);
            Thread.sleep(2000);
        }
    }

    /**
     * 监听nacos的配置
     * @param dataId
     * @param group
     */
    public void dynamicRouteByNacosListener (String dataId, String group){
        try {
            configService.addListener(dataId, group, new Listener()  {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("进行网关更新:\n\r{}",configInfo);
                }
                @Override
                public Executor getExecutor() {
                    log.info("getExecutor\n\r");
                    return null;
                }
            });
        } catch (NacosException e) {
            log.error("从nacos接收动态路由配置出错!!!",e);
        }
    }
}

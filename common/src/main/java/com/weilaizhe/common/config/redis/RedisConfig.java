package com.weilaizhe.common.config.redis;

import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @dateTime 2019-03-26 22:39
 * @author: dameizi
 * @description: Redis配置
 */
@Configuration
public class RedisConfig {

    //超时时间
    @Value("${cluster-servers-config.timeout}")
    private int timeout;
    //密码
    @Value("${cluster-servers-config.password}")
    private String password;
    //从连接池大小
    @Value("${cluster-servers-config.slave-connection-pool-size}")
    private int slaveConnectionPoolSize;
    //ping连接间隔
    @Value("${cluster-servers-config.ping-connection-interval-size}")
    private int pingConnectionIntervalSize;
    //主连接池大小
    @Value("${cluster-servers-config.master-connection-pool-size}")
    private int masterConnectionPoolSize;
    //主连接池大小
    @Value("${cluster-servers-config.connection-minimum-idle-size}")
    private int connectionMinimumIdleSize;
    //连接失败尝试次数
    @Value("${cluster-servers-config.failed-attempts}")
    private int failedAttempts;
    //命令失败重试次数
    @Value("${cluster-servers-config.retry-attempts}")
    private int retryAttempts;
    //哨兵模式数组
    @Value("${cluster-servers-config.cluster-addresses}")
    private String[] clusterAddresses;

    @Bean(value = "redissonClient")
    public RedissonClient getRedis(){
        Config config = new Config();
        // 单机版redisson
        /*config.useSingleServer()
                .setAddress("redis://61.216.31.153:6379")
                .setPassword("weilaipay789");*/
        // 哨兵模式自动装配redisson
        ClusterServersConfig clusterServersConfig = config.useClusterServers()
                .addNodeAddress(this.clusterAddresses)
                .setPingConnectionInterval(this.pingConnectionIntervalSize)
                .setFailedSlaveReconnectionInterval(this.failedAttempts)
                .setRetryAttempts(this.retryAttempts)
                .setTimeout(this.timeout)
                .setMasterConnectionMinimumIdleSize(this.connectionMinimumIdleSize)
                .setMasterConnectionPoolSize(this.masterConnectionPoolSize)
                .setSlaveConnectionPoolSize(this.slaveConnectionPoolSize);
        if (StringUtils.isNotBlank(this.password)) {
            clusterServersConfig.setPassword(this.password);
        }
        //设置集群状态扫描时间
        clusterServersConfig.setScanInterval(60000);
        return Redisson.create(config);
    }

}

package com.example;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.example.job.MyDataflowJob;
import com.example.job.MySimpleJob;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
//        new JobScheduler(zkCenter(), configuration()).init();
        new JobScheduler(zkCenter(), configurationDataflow()).init();
    }

    public static CoordinatorRegistryCenter zkCenter(){
        var zc = new ZookeeperConfiguration("127.0.0.1:2181",
                "java-simple-job");
        var crc = new ZookeeperRegistryCenter(zc);
        //注册中心初始化
        crc.init();
        return crc;
    }

    /**
     * simple-job配置
     * @return
     */
    public static LiteJobConfiguration configuration() {
        //job核心配置
        var jcc = JobCoreConfiguration
                .newBuilder("mySimpleJob","0/1 * * * * ?",2)
                .build();
        //job类型配置
        var jtc = new SimpleJobConfiguration(jcc, MySimpleJob.class.getCanonicalName());
        //job根的配置（LiteJobConfiguration）
        var ljc = LiteJobConfiguration
                .newBuilder(jtc)
                .overwrite(true)
                .build();

        return ljc;
    }

    /**
     * Dataflow-job配置
     * @return
     */
    public static LiteJobConfiguration configurationDataflow() {
        //job核心配置
        var jcc = JobCoreConfiguration
                .newBuilder("myDataflowJob","0/10 * * * * ?",2)
                .build();
        //job类型配置
        var jtc = new DataflowJobConfiguration(jcc, MyDataflowJob.class.getCanonicalName(), true);
        //job根的配置（LiteJobConfiguration）
        var ljc = LiteJobConfiguration
                .newBuilder(jtc)
                .overwrite(true)
                .build();

        return ljc;
    }
}
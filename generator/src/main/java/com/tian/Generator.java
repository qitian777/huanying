package com.tian;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @ClassName: Generator
 * @Description: MyBatis-Plus代码生成器
 * @Author QiGuang
 * @Date 2022/6/13
 * @Version 1.0
 */
public class Generator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/huanying?characterEncoding=utf-8&userSSL=false", "root", "qi123")
                .globalConfig(builder -> {
                    builder.author("QiGuang") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://mybatis_plus"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.tian") // 设置父包名
                            //.moduleName("mybatisplus") // 设置父包模块名
                            .entity("pojo")//设置entity包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D://mybatis_plus"));// 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("user", "message", "items", "friend", "dev_log", "comment", "user_role", "user_authority", "user_coll") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_") // 设置过滤表前缀
                            .entityBuilder().enableLombok(); //开启 lombok 模型
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker 引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}

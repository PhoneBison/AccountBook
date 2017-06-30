package com.stu.it.accountbook.db;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by Touch on 2017/5/9.
 */

public class CustomGenerator {
    public static void main(String[] args) throws Exception {
        // 第一个参数为数据库版本
        //第二个参数为数据库的包名
        Schema schema = new Schema(1, "com.stu.it.accountbook.db");
        // 创建表,参数为表名
        Entity tag = schema.addEntity("Tag");
        // 为表添加字段
        tag.addIdProperty().autoincrement();
        tag.addIntProperty("type");// Int类型字段：消费的类型
        tag.addIntProperty("tag");// Int类型字段：标签图片ID
        tag.addStringProperty("name");// String类型字段：标签名
        tag.addStringProperty("tag_remark");// String类型字段：标签

        // 创建表,参数为表名
        Entity personal = schema.addEntity("Info");
        // 为表添加字段
        personal.addIdProperty().autoincrement();//主键
        personal.addIntProperty("type");// Int类型字段：消费的类型
        personal.addStringProperty("date");//消费日期
        personal.addDoubleProperty("money");//Double类型字段：消费金额
        personal.addStringProperty("remark");// String类型字段：消费备注
        personal.addIntProperty("tagId");// tag表主键

        // 生成数据库相关类
        //第二个参数指定生成文件的本次存储路径,AndroidStudio工程指定到当前工程的java路径
        try{
            new DaoGenerator().generateAll(schema, "./app/src/main/java");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

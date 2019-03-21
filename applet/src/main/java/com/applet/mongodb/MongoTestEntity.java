package com.applet.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "customer111")//将一个类声明为一个mongodb中的文档，如果@Document（collection=“xx”）,则指定对应xx文档
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MongoTestEntity {

    //将一个字段声明为mongodb文档中的ObjectId
    @Id
    String id;

    //将一个字段声明为文档中的索引，如果@Indexed（unique=true），则声明为唯一索引
    @Indexed(unique = true)
    String mobile;

    String firstName;

    String lastName;

    //声明当前字段对应文档中的哪一个列
    @Field("jsonText")
    String json;

    //映射忽略的字段，不会保存到文档中去
    @Transient
    String test;


}
package com.applet.entity.mongodb;
 
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
 
//@Document(collection="mongodbTestModel") //collection:集合名 类似于关系型数据里面的 tableName
@Data
@ToString
@Document(collection="mongodbTestModel")
public class MongodbTestModel {
 
	@Id
	private String mid;
	private String name;
	private String age;
	
}

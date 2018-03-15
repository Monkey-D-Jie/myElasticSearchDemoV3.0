package com.jf.mydemo.es.myelasticsearch.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/13 0013
 * @time: 10:28
 * To change this template use File | Settings | File and Templates.
 */
@Document(indexName = "myesdemo",type = "post",indexStoreType = "string",shards = 6,replicas=2,refreshInterval="-1")
public class Post {

    public static final String indexName = "myesdemo";

    @Id
    private String id;
    @Field(type = FieldType.Integer)
    private int userId;
    @Field(type = FieldType.Integer)
    private int weight;
//    @Field(type = FieldType.String)
//    spring-data-commons 2.x后成了text，下同
    @Field(type = FieldType.text)
    private String title;
//    @Field(type = FieldType.String)
    @Field(type = FieldType.text)
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", userId='" + userId + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }
}

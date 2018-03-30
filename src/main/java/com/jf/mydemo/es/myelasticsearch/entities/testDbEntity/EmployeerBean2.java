package com.jf.mydemo.es.myelasticsearch.entities.testDbEntity;

import com.jf.mydemo.es.myelasticsearch.utils.DateFormatKit;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/21 0021
 * @time: 14:13
 * To change this template use File | Settings | File and Templates.
 */
@Document(indexName = "mytestdbdemo",type = "EmployeerBean",indexStoreType = "string",shards = 6,replicas=2,refreshInterval="-1")
public class EmployeerBean2 {

    public static final String indexName = "mytestdbdemo";

    @Id
    private Integer empNo;
    /**
     * 这里的 format针对的是，用这个属性告诉es用该格式来存入date类型的数据
     * 如果是希望从里面取出来的日期类数据按着想要的格式呈现出来的，在定义之初就得这么干。
     * 我用的是logStash同步，直接就把MySQL中的数据库同步过去的。然后在es中用默认的UTC(世界协调时间来展示)
     */
    @Field(type = FieldType.Date,format = DateFormat.date_hour_minute_second)
    private Date birthDate;
    @Field(type = FieldType.text)
    private String firstName;
    @Field(type = FieldType.text)
    private String  lastName;
    /*M:男性，F:女性*/
    @Field(type = FieldType.text)
    private String gender;
    @Field(type = FieldType.Date)
    private Date hireDate;

    private String formatBirthDate;
    private String formatHireDate;

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getFormatBirthDate() {
        return DateFormatKit.convert(DateFormatKit.DATE_FORMAT_ONE,this.getBirthDate());
//        return this.formatBirthDate;
    }

    public void setFormatBirthDate(String formatBirthDate) {
        this.formatBirthDate = formatBirthDate;
    }

    public String getFormatHireDate() {
        return DateFormatKit.convert(DateFormatKit.DATE_FORMAT_ONE,this.getHireDate());
    }

    public void setFormatHireDate(String formatHireDate) {
        this.formatHireDate = formatHireDate;
    }

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    /*public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }*/

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }



    @Override
    public String toString() {
        return "EmployeerBean{" +
                "empNo=" + empNo +
                ", birthDate=" + birthDate +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", hireDate=" + hireDate +
                '}';
    }
}

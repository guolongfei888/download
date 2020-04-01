package com.panshi.springmvc.controller;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName User
 * @Description
 * @Author guolongfei
 * @Date 2019/12/11  14:31
 * @Version
 */
public class Items implements Serializable {


    private static final long serialVersionUID = -5221846202107157965L;
    private Integer id;

    private MultipartFile pic; //上传文件会自动绑定到该属性

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MultipartFile getPic() {
        return pic;
    }

    public void setPic(MultipartFile pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "Items{" +
                "id=" + id +
                ", pic=" + pic +
                '}';
    }
}

package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Article;

@Mapper
public interface ArticleMapper {

    @Insert("insert into article(id, title, content, cover_img, state, category_id, create_user, create_time, update_time) " +
    "values(#{title}, #{content}, #{coverImg}, #{state}, #{categoryId}, #{createUser}, #{createTime}, #{updateTime} )")
    void add(Article article);
}








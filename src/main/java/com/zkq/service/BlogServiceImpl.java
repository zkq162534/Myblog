package com.zkq.service;

import com.zkq.mapper.BlogMapper;
import com.zkq.domain.Blog;
import com.zkq.domain.BlogCustom;
import com.zkq.domain.Page;
import com.zkq.utils.LocalDateAndSqlDateChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
/**
 * @author zkq15
 * */
@Service
public class BlogServiceImpl implements  BlogServic {
   @Autowired
    BlogMapper blogMapper;
    @Override
    public List<Blog> getBlogByPage(Page blogPage) {
        int start= (blogPage.getCurrentPage()-1)*blogPage.getPageNumber();
        blogPage.setStart(start);
        //得到分页查询的list
        List<Blog> bloglist=blogMapper.getBlogByPage(blogPage);
         for(Blog blog:bloglist){
             blog.setTime(blog.getWritetime().format(DateTimeFormatter.ISO_LOCAL_DATE));
         }
        return bloglist;
    }

    @Override
    public void setBlogTotalRows(Page blogPage) {
        int count=blogMapper.getBlogTotalRows();
        //设置总行数
        blogPage.setTotalRows(count);
    }

    @Override
    public List<Blog> getBlogWithKeyWord(Page page) {
        int start= (page.getCurrentPage()-1)*page.getPageNumber();
        page.setStart(start);
         List<Blog> blogs=blogMapper.getBlogWithKeyWord(page);
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-mm-dd");
        for(Blog blog:blogs){
            blog.setTime(blog.getWritetime().format(formatter));
        }
        return blogs;
    }

    @Override
    public void setBlogTotalWithKeyWord(Page page) {
        int count=blogMapper.getBlogTotalWithKeyWord(page);
        //设置总行数
        page.setTotalRows(count);
        System.out.println(count);
    }

    @Override
    public boolean deleteBlog(BlogCustom blogCustom) {
        return blogMapper.deleteBlog(blogCustom)==1?true:false;
    }

    @Override
    public boolean insertBlog(BlogCustom blogCustom) {
        LocalDate now=LocalDate.now();
        blogCustom.setWritetime(now);
        return blogMapper.insertBlog(blogCustom)==1?true:false;
    }

    @Override
    public BlogCustom getBlogById(BlogCustom blogCustom) {
        return blogMapper.getBlogById(blogCustom);
    }

    @Override
    public boolean updateBlog(BlogCustom blogCustom) {
        return blogMapper.updateBlog(blogCustom)==1?true:false;
    }

}

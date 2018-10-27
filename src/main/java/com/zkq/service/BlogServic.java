package com.zkq.service;

import com.zkq.domain.Blog;
import com.zkq.domain.Page;
import com.zkq.domain.BlogCustom;

import java.util.List;

public interface BlogServic {
    public List<Blog> getBlogByPage(Page blogPage);
    public void setBlogTotalRows(Page blogPage);
    public List<Blog> getBlogWithKeyWord(Page page);
    public void setBlogTotalWithKeyWord(Page page);
    public boolean deleteBlog(BlogCustom BlogCustom);
    public boolean insertBlog(BlogCustom blogCustom);
}

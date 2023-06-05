package com.oynaap.controllers;

import com.oynaap.models.BlogPost;
import com.oynaap.models.Category;
import com.oynaap.service.CategoryService;
import com.oynaap.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;

import static com.oynaap.repository.Queries.SQL_SELECT_CATEGORYID;

@Controller
@RequestMapping
public class BlogPostController {
    @Autowired
    private ProductService blogpostSvc;


    @Autowired
    private CategoryService categorySvc;

    @Autowired
    private JdbcTemplate template;



    @GetMapping(path="/blogpost")
    public ModelAndView Blogpost() throws SQLException{
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("blogpost");
        List<BlogPost> blogposts = blogpostSvc.getAllBlogPostImages();
        mvc.addObject("blogpost",blogposts);
        List<Category> categories = categorySvc.getAllCategories();
        mvc.addObject("categories",categories);

        return mvc;

    }

    @GetMapping(path="/blog-search")
    public ModelAndView Search(
            @RequestParam String author, @RequestParam String category) throws SQLException{
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("blogpost");

        if(category.equals("All")){
            System.out.println(">>>>>>  category1: " +category);
            List<BlogPost> blogposts = blogpostSvc.searchBlogPostsByName(author);
            mvc.addObject("blogposts",blogposts);
        }else{
            System.out.println(">>>>>> category2: " +category);
            SqlRowSet rs = template.queryForRowSet(SQL_SELECT_CATEGORYID, category);
            rs.next();
            Integer category_id=rs.getInt("category_id");
            List<BlogPost> blogposts = blogpostSvc.searchBlogPostsByNameAndCategory(author,category_id);
            mvc.addObject("blogposts",blogposts);
            System.out.println(">>>>>> category2: " +category);
        }

        List<Category> categories = categorySvc.getAllCategories();
        mvc.addObject("categories",categories);

        return mvc;
    }

    @GetMapping(path="/blogpost/blogpost/{blog_id}")
    public ModelAndView BlogPost(@PathVariable(name="blog_id") Integer blog_id) throws SQLException{
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("blogpostblogpost");
        BlogPost blogpost = blogpostSvc.getOneBlogPostImage(blog_id);
        mvc.addObject("blogpost",blogpost);
        System.out.println(">>>>>> blog_id: " +blogpost.getBlog_id());

        return mvc;

    }


}

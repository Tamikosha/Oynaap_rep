package com.oynaap.repository;

import com.oynaap.models.BlogPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Repository
public class BlogPostRepository implements SQLCOMMAND {

    @Autowired
    private JdbcTemplate template;

    public boolean insertBlogpost(BlogPost blogpost){

        int count = template.update(SQL_INSERT_BLOGPOST,
                blogpost.getImage(),
                blogpost.getAuthor(),
                blogpost.getDescription(),
                blogpost.getTitle(),
                blogpost.getCategory_id());

        return count == 1;
    }
    public Integer selectCategoryID(String category){
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_CATEGORYID,category);
        rs.next();
        Integer categoryID = rs.getInt("category_id");
        return categoryID;
    }

    public String selectBlogPostCategory(Integer id){
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_BLOGPOST_CATEGORY,id);
        rs.next();
        String categoryName = rs.getString("category_name");
        return categoryName;
    }

    public List<BlogPost> selectAllBlogposts() throws SQLException{
        List <BlogPost> blogposts = new LinkedList<>();
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_ALL_BLOGPOST);
        while(rs.next()){
            BlogPost blogpost = BlogPost.convert(rs);
            String categoryName = selectBlogPostCategory(blogpost.getCategory_id());
            blogpost.setCategory(categoryName);
            blogposts.add(blogpost);
        }

        return blogposts;
    }

    public boolean deleteBlogPost(Integer blog_id){
        int count = template.update(SQL_DELETE_BLOGPOST,
                blog_id);

        return count == 1;
    }

    public BlogPost selectBlogPost(Integer blog_id) throws SQLException{
        BlogPost blogpost = new BlogPost();
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_BLOGPOST,blog_id);
        rs.next();
        blogpost = BlogPost.convert(rs);
        return blogpost;

    }
    public boolean updateBlogPost(BlogPost blogpost){

        int count = template.update(SQL_UPDATE_BLOGPOST,
                blogpost.getTitle(),
                blogpost.getDescription(),
                blogpost.getAuthor(),
                blogpost.getCategory_id(),
                blogpost.getImage(),
                blogpost.getBlog_id());

        return count == 1;
    }

    public List <BlogPost> selectBlogPostImage() throws SQLException{
        List <BlogPost> blogposts = new LinkedList<>();
        return template.query(
                SQL_SELECT_ALL_IMAGESS,
                (ResultSet rs)->{
                    while(rs.next()){
                        final BlogPost blogpost = BlogPost.populateImage(rs);
                        blogposts.add(blogpost);}
                    return blogposts;
                }

        );

    }

    public BlogPost selectOneBlogPostImage(Integer blog_id) throws SQLException{

        return template.query(
                SQL_SELECT_AN_IMAGEE,
                (ResultSet rs)->{
                    rs.next();
                    final BlogPost blogpost = BlogPost.populateImage(rs);

                    return blogpost;
                },blog_id

        );

    }

    public List<BlogPost> searchBlogPostsByNameAndCategory(String author,Integer category_id) throws SQLException{
        List <BlogPost> blogposts = new LinkedList<>();
        return template.query(
                SQL_SEARCH_BLOGPOST_BY_NAME_AND_CATEGORY,
                (ResultSet rs)->{
                    while(rs.next()){
                        final BlogPost blogpost = BlogPost.populateImage(rs);
                        String categoryName = selectBlogPostCategory(blogpost.getCategory_id());
                        blogpost.setCategory(categoryName);
                        blogposts.add(blogpost);}
                    return blogposts;
                },author,category_id

        );
    }

    public List<BlogPost> searchBlogPostsByName(String author) throws SQLException{
        List <BlogPost> blogposts = new LinkedList<>();
        return template.query(
                SQL_SEARCH_BLOGPOST_BY_NAME,
                (ResultSet rs)->{
                    while(rs.next()){
                        final BlogPost blogpost = BlogPost.populateImage(rs);
                        String categoryName = selectBlogPostCategory(blogpost.getCategory_id());
                        blogpost.setCategory(categoryName);
                        blogposts.add(blogpost);}
                    return blogposts;
                },author

        );

    }



}

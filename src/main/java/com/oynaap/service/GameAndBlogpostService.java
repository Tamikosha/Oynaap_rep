package com.oynaap.service;

import com.oynaap.models.BlogPost;
import com.oynaap.models.Game;
import com.oynaap.repository.BlogPostRepository;
import com.oynaap.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class GameAndBlogpostService {
    
    @Autowired
    private GameRepository gameRepo;
    @Autowired
    private BlogPostRepository blogpostRepo;

    public void addNewGame(Game game){
        gameRepo.insertGame(game);
    }

    public Integer selectCategoryID(String category){
        Integer categoryId = gameRepo.selectCategoryID(category);
        return categoryId;
    }

    public String selectGameCategory(Integer id){
        String categoryName = gameRepo.selectGameCategory(id);
        return categoryName;
    }

    public List<Game> getAllGames() throws SQLException{
        return gameRepo.selectAllGame();
    }

    public void deleteGame(Integer gm_id) {
        gameRepo.deleteGame(gm_id);
    }

    public Game selectGame(Integer gm_id) throws SQLException {
        Game game = gameRepo.selectGame(gm_id);
        return game;
    }

    public void updateGame(Game game) {
        gameRepo.updateGame(game);
    }

    public List<Game> getAllGameImages( ) throws SQLException{
        return gameRepo.selectGameImage();
    }

    public Game getOneGameImage(Integer gm_id) throws SQLException{
        return gameRepo.selectOneGameImage(gm_id);
    }

    public List<Game> searchGamesByNameAndCategory(String name, Integer category_id) throws SQLException{
        return gameRepo.searchGamesByNameAndCategory(name,category_id);
    }

    public List<Game> searchGamesByName(String name) throws SQLException{
        return gameRepo.searchGamesByName(name);
    }

    public String selectBlogPostCategory(Integer id){
        String categoryName = blogpostRepo.selectBlogPostCategory(id);
        return categoryName;
    }

    public List<BlogPost> getAllBlogposts() throws SQLException{
        return blogpostRepo.selectAllBlogposts();
    }
    public void addNewBlogposts(BlogPost blogpost) {
        blogpostRepo.insertBlogpost(blogpost);
    }
    public void deleteBlogPost(Integer blog_id) {
        blogpostRepo.deleteBlogPost(blog_id);
    }

    public BlogPost selectBlogPost(Integer blog_id) throws SQLException {
        BlogPost blogpost = blogpostRepo.selectBlogPost(blog_id);
        return blogpost;
    }

    public void updateBlogPost(BlogPost blogpost) {
        blogpostRepo.updateBlogPost(blogpost);
    }

    public List<BlogPost> getAllBlogPostImages( ) throws SQLException{
        return blogpostRepo.selectBlogPostImage();
    }

    public BlogPost getOneBlogPostImage(Integer blog_id) throws SQLException{
        return blogpostRepo.selectOneBlogPostImage(blog_id);
    }

    public List<BlogPost> searchBlogPostsByNameAndCategory(String author,Integer category_id) throws SQLException{
        return blogpostRepo.searchBlogPostsByNameAndCategory(author,category_id);
    }

    public List<BlogPost> searchBlogPostsByName(String author) throws SQLException{
        return blogpostRepo.searchBlogPostsByName(author);
    }


}

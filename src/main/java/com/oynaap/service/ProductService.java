package com.oynaap.service;

import com.oynaap.models.BlogPost;
import com.oynaap.models.Product;
import com.oynaap.repository.BlogPostRepository;
import com.oynaap.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private BlogPostRepository blogpostRepo;

    public void addNewProduct(Product product){
        productRepo.insertProduct(product);
    }

    public Integer selectCategoryID(String category){
        Integer categoryId = productRepo.selectCategoryID(category);
        return categoryId;
    }

    public String selectProductCategory(Integer id){
        String categoryName = productRepo.selectProductCategory(id);
        return categoryName;
    }

    public List<Product> getAllProducts() throws SQLException{
        return productRepo.selectAllProducts();
    }

    public void deleteProduct(Integer prod_id) {
        productRepo.deleteProduct(prod_id);
    }

    public Product selectProduct(Integer prod_id) throws SQLException {
        Product product = productRepo.selectProduct(prod_id);
        return product;
    }

    public void updateProduct(Product product) {
        productRepo.updateProduct(product);
    }

    public List<Product> getAllProductImages( ) throws SQLException{
        return productRepo.selectProductImage();
    }

    public Product getOneProductImage(Integer prod_id) throws SQLException{
        return productRepo.selectOneProductImage(prod_id);
    }

    public List<Product> searchProductsByNameAndCategory(String name,Integer category_id) throws SQLException{
        return productRepo.searchProductsByNameAndCategory(name,category_id);
    }

    public List<Product> searchProductsByName(String name) throws SQLException{
        return productRepo.searchProductsByName(name);
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

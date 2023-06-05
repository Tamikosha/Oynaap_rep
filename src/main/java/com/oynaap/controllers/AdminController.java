package com.oynaap.controllers;

import com.oynaap.models.BlogPost;
import com.oynaap.models.Category;
import com.oynaap.models.Product;
import com.oynaap.service.CategoryService;
import com.oynaap.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping(path="/admin")
public class AdminController {

    @Autowired
    private CategoryService categorySvc;

    @Autowired
    private ProductService productSvc;

    @Autowired
    private ProductService blogpostSvc;

    @GetMapping(path="/category")
    public ModelAndView getAdminCategory(){

        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("admincategory");
        List<Category> categories = categorySvc.getAllCategories();
        mvc.addObject("categories",categories);
        System.out.println(">>>>>> categories: " +categories);

        return mvc;
    }

    @PostMapping(path="/category")
    public ModelAndView addCategory(@RequestBody MultiValueMap<String, String> form){
        String name = form.getFirst("name");
        String description = form.getFirst("description");

        Category category = new Category();
        category.setCategory_name(name);
        category.setDescription(description);
        categorySvc.addNewCategory(category);

        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("admincategory");
        List<Category> categories = categorySvc.getAllCategories();
        mvc.addObject("categories",categories);

        return mvc;
    }

    @GetMapping(path="category/delete/{category_id}")
    public ModelAndView deleteCategory(@PathVariable(name="category_id") Integer category_id){

        categorySvc.deleteCategory(category_id);

        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("admincategory");
        List<Category> categories = categorySvc.getAllCategories();
        mvc.addObject("categories",categories);

        return mvc;
    }

    @GetMapping(path="category/edit/{category_id}")
    public ModelAndView editCategory(@PathVariable(name="category_id") Integer category_id){

        Category category = categorySvc.selectCategory(category_id);

        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("editcategory");
        mvc.addObject("category",category);

        return mvc;
    }

    @PostMapping(path="/category/updatecategory")
    public ModelAndView updateCategory(@RequestBody MultiValueMap<String, String> form){
        String name = form.getFirst("name");
        String description = form.getFirst("description");
        Integer id = Integer.parseInt(form.getFirst("id"));

        Category category = new Category();
        category.setCategory_name(name);
        category.setDescription(description);
        category.setCategory_id(id);
        categorySvc.updateCategory(category);

        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("admincategory");
        List<Category> categories = categorySvc.getAllCategories();
        mvc.addObject("categories",categories);

        return mvc;
    }

    @GetMapping(path="/product")
    public ModelAndView getProducts() throws SQLException{

        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("adminproduct");
        List<Product> products = productSvc.getAllProducts();
        System.out.println(">>>>>> products: " +products);
        mvc.addObject("products",products);
        List<Category> categories = categorySvc.getAllCategories();
        mvc.addObject("categories",categories);
        
        return mvc;
    }

    @PostMapping(path="/product",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView addProduct(
        @RequestParam MultipartFile image,
        @RequestParam String name,
        @RequestParam String description,
        @RequestParam String price,
        @RequestParam String category
    ) throws SQLException{
        byte[] buff = new byte[0];

        try {
            buff=image.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Product product = new Product();
        product.setImage(buff);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(Double.parseDouble(price));

        Integer categoryID = productSvc.selectCategoryID(category);
        product.setCategory_id(categoryID);

        productSvc.addNewProduct(product);

        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("adminproduct");
        List<Product> products = productSvc.getAllProducts();
        mvc.addObject("products",products);
        List<Category> categories = categorySvc.getAllCategories();
        mvc.addObject("categories",categories);

        return mvc;
    }

    @GetMapping(path="product/delete/{prod_id}")
    public ModelAndView deleteProduct(@PathVariable(name="prod_id") Integer prod_id) throws SQLException{

        productSvc.deleteProduct(prod_id);

        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("adminproduct");
        List<Product> products = productSvc.getAllProducts();
        mvc.addObject("products",products);
        List<Category> categories = categorySvc.getAllCategories();
        mvc.addObject("categories",categories);

        return mvc;
    }

    @GetMapping(path="product/edit/{prod_id}")
    public ModelAndView editProduct(@PathVariable(name="prod_id") Integer prod_id) throws SQLException{

        Product product = productSvc.selectProduct(prod_id);

        String categoryName = productSvc.selectProductCategory(product.getCategory_id());
        product.setCategory(categoryName);

        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("editproduct");
        mvc.addObject("product",product);
        List<Category> categories = categorySvc.getAllCategories();
        mvc.addObject("categories",categories);

        return mvc;
    }

    @PostMapping(path="/product/updateproduct",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView updateProduct(
            @RequestParam MultipartFile image,
        @RequestParam String name,
        @RequestParam String description,
        @RequestParam String price,
        @RequestParam String category,
        @RequestParam String id
    ) throws SQLException{
        byte[] buff = new byte[0];

        try {
            buff=image.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Product product = new Product();
        product.setImage(buff);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(Double.parseDouble(price));
        product.setProd_id(Integer.parseInt(id));

        Integer categoryID = productSvc.selectCategoryID(category);
        product.setCategory_id(categoryID);

        productSvc.updateProduct(product);

        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("adminproduct");
        List<Product> products = productSvc.getAllProducts();
        mvc.addObject("products",products);
        List<Category> categories = categorySvc.getAllCategories();
        mvc.addObject("categories",categories);

        return mvc;
    }
    @GetMapping(path="/blogpost")
    public ModelAndView getBlogposts() throws SQLException{

        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("adminblogpost");
        List<BlogPost> blogposts = blogpostSvc.getAllBlogposts();
        System.out.println(">>>>>> blogposts: " +blogposts);
        mvc.addObject("blogposts",blogposts);
        List<Category> categories = categorySvc.getAllCategories();
        mvc.addObject("categories",categories);

        return mvc;
    }

    @PostMapping(path="/blogpost", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView addBlogPost(
            @RequestParam MultipartFile image,
            @RequestParam String author    ,
            @RequestParam String description,
            @RequestParam String title,
            @RequestParam String category
    ) throws SQLException{
        byte[] buff = new byte[0];

        try {
            buff=image.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BlogPost blogpost = new BlogPost();
        blogpost.setImage(buff);
        blogpost.setAuthor(author);
        blogpost.setDescription(description);
        blogpost.setTitle(title);

        Integer categoryID = blogpostSvc.selectCategoryID(category);
        blogpost.setCategory_id(categoryID);

        blogpostSvc.addNewBlogposts(blogpost);

        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("adminblogpost");
        List<BlogPost> blogposts = blogpostSvc.getAllBlogposts();
        mvc.addObject("blogposts",blogposts);
        List<Category> categories = categorySvc.getAllCategories();
        mvc.addObject("categories",categories);

        return mvc;
    }

    @GetMapping(path="blogpost/delete/{blog_id}")
    public ModelAndView deleteBlogPost(@PathVariable(name="blog_id") Integer blog_id) throws SQLException{

        blogpostSvc.deleteBlogPost(blog_id);

        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("adminblogpost");
        List<BlogPost> blogposts = blogpostSvc.getAllBlogposts();
        mvc.addObject("blogposts",blogposts);
        List<Category> categories = categorySvc.getAllCategories();
        mvc.addObject("categories",categories);

        return mvc;
    }

    @GetMapping(path="blogpost/edit/{blog_id}")
    public ModelAndView editBlogPost(@PathVariable(name="blog_id") Integer blog_id) throws SQLException{

        BlogPost blogpost = blogpostSvc.selectBlogPost(blog_id);

        String categoryName = blogpostSvc.selectBlogPostCategory(blogpost.getCategory_id());
        blogpost.setCategory(categoryName);

        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("editblogpost");
        mvc.addObject("blogpost",blogpost);
        List<Category> categories = categorySvc.getAllCategories();
        mvc.addObject("categories",categories);

        return mvc;
    }

    @PostMapping(path="/blogpost/updateblogpost",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView updateBlogPost(
            @RequestParam MultipartFile image,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String author,
            @RequestParam String category,
            @RequestParam String id
    ) throws SQLException{
        byte[] buff = new byte[0];

        try {
            buff=image.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BlogPost blogpost = new BlogPost();
        blogpost.setImage(buff);
        blogpost.setAuthor(author);
        blogpost.setDescription(description);
        blogpost.setTitle(title);
        blogpost.setBlog_id(Integer.parseInt(id));

        Integer categoryID = blogpostSvc.selectCategoryID(category);
        blogpost.setCategory_id(categoryID);

        blogpostSvc.updateBlogPost(blogpost);

        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("adminblogpost");
        List<BlogPost> blogposts = blogpostSvc.getAllBlogposts();
        mvc.addObject("blogpost",blogposts);
        List<Category> categories = categorySvc.getAllCategories();
        mvc.addObject("categories",categories);

        return mvc;
    }
}

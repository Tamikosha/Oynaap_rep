package com.oynaap.controllers;

import com.oynaap.models.BlogPost;
import com.oynaap.models.Product;
import com.oynaap.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping(path="/shop/image")

public class ImageRestController {
    
    @Autowired
    private ProductService productSvc;

    @Autowired
    private ProductService blogpostSvc;

    @GetMapping(path="/{prod_id}")
    public ResponseEntity<byte[]> getPostImage(@PathVariable(name="prod_id") Integer prod_id) throws SQLException{

        Product product = productSvc.getOneProductImage(prod_id);
       
        return ResponseEntity.ok()
            .body(product.getImage());
    }
    @RequestMapping(path="/blogpost/image")
    @GetMapping(path="/{blog_id}")
    public ResponseEntity<byte[]> getPostImagee(@PathVariable(name="blog_id") Integer blog_id) throws SQLException{

         BlogPost blogpost = blogpostSvc.getOneBlogPostImage(blog_id);

        return ResponseEntity.ok()
                .body(blogpost.getImage());
    }
}

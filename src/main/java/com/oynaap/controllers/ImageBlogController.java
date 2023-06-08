package com.oynaap.controllers;

import com.oynaap.models.BlogPost;

import com.oynaap.service.GameAndBlogpostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping(path="/blogpost/image")
public class ImageBlogController {
    @Autowired
    private GameAndBlogpostService blogpostSvc;
    @GetMapping(path="/{blog_id}")
    public ResponseEntity<byte[]> getPostImage(@PathVariable(name="blog_id") Integer blog_id) throws SQLException{

        BlogPost blogpost = blogpostSvc.getOneBlogPostImage(blog_id);

        return ResponseEntity.ok()
                .body(blogpost.getImage());
    }
}

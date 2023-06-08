package com.oynaap.controllers;


import com.oynaap.models.Game;
import com.oynaap.service.GameAndBlogpostService;
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
    private GameAndBlogpostService gameSvc;



    @GetMapping(path="/{gm_id}")
    public ResponseEntity<byte[]> getPostImage(@PathVariable(name="gm_id") Integer gm_id) throws SQLException{

        Game game = gameSvc.getOneGameImage(gm_id);
       
        return ResponseEntity.ok()
            .body(game.getImage());
    }

}

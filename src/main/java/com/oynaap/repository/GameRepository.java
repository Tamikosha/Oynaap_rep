package com.oynaap.repository;

import com.oynaap.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Repository
public class GameRepository implements SQLCOMMAND {

    @Autowired
    private JdbcTemplate template;

    public boolean insertGame(Game game){

        int count = template.update(SQL_INSERT_GAME,
                game.getImage(),
                game.getName(),
                game.getDescription(),
                game.getPrice(),
                game.getCategory_id());

        return count == 1;
    }

    public Integer selectCategoryID(String category){
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_CATEGORYID,category);
        rs.next();
        Integer categoryID = rs.getInt("category_id");
        return categoryID;
    }

    public String selectGameCategory(Integer id){
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_GAME_CATEGORY,id);
        rs.next();
        String categoryName = rs.getString("category_name");
        return categoryName;
    }

    public List<Game> selectAllGame() throws SQLException{
        List <Game> games = new LinkedList<>();
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_ALL_GAME);
        while(rs.next()){
            Game game = Game.convert(rs);
            String categoryName = selectGameCategory(game.getCategory_id());
            game.setCategory(categoryName);
            games.add(game);
        }

        return games;
    }

    public boolean deleteGame(Integer gm_id){
        int count = template.update(SQL_DELETE_GAME,
                gm_id);

        return count == 1;
    }

    public Game selectGame(Integer gm_id) throws SQLException{
        Game game = new Game();
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_GAME,gm_id);
        rs.next();
        game = Game.convert(rs);
        return game;

    }

    public boolean updateGame(Game game){

        int count = template.update(SQL_UPDATE_GAME,
                game.getName(),
                game.getDescription(),
                game.getPrice(),
                game.getCategory_id(),
                game.getImage(),
                game.getGm_id());

        return count == 1;
    }

    public List <Game> selectGameImage() throws SQLException{
        List <Game> games = new LinkedList<>();
        return template.query(
                SQL_SELECT_ALL_IMAGES,
                (ResultSet rs)->{
                    while(rs.next()){
                        final Game game = Game.populateImage(rs);
                        games.add(game);}
                    return games;
                }

        );

    }

    public Game selectOneGameImage(Integer gm_id) throws SQLException{

        return template.query(
                SQL_SELECT_AN_IMAGE,
                (ResultSet rs)->{
                    rs.next();
                    final Game game = Game.populateImage(rs);

                    return game;
                },gm_id

        );

    }

    public List<Game> searchGamesByNameAndCategory(String name, Integer category_id) throws SQLException{
        List <Game> games = new LinkedList<>();
        return template.query(
                SQL_SEARCH_GAME_BY_NAME_AND_CATEGORY,
                (ResultSet rs)->{
                    while(rs.next()){
                        final Game game = Game.populateImage(rs);
                        String categoryName = selectGameCategory(game.getCategory_id());
                        game.setCategory(categoryName);
                        games.add(game);}
                    return games;
                },name,category_id

        );
    }

    public List<Game> searchGamesByName(String name) throws SQLException{
        List <Game> games = new LinkedList<>();
        return template.query(
                SQL_SEARCH_GAME_BY_NAME,
                (ResultSet rs)->{
                    while(rs.next()){
                        final Game game = Game.populateImage(rs);
                        String categoryName = selectGameCategory(game.getCategory_id());
                        game.setCategory(categoryName);
                        games.add(game);}
                    return games;
                },name

        );

    }



}

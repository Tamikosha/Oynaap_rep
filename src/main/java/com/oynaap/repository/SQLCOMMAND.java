package com.oynaap.repository;

public interface SQLCOMMAND {


    public static final String SQL_INSERT_GAME =
    "insert into games(image,name,description,price,category_id) value (?,?,?,?,?)";
    public static final String SQL_INSERT_BLOGPOST =
            "insert into blogposts(image, author,description,title, category_id) value (?,?,?,?,?)";

    public static final String SQL_SELECT_CATEGORYID =
    "select category_id from category where category_name=?";

    public static final String SQL_SELECT_CATEGORYNAME=
    "select category_name from category where category_id=?";

    public static final String SQL_SELECT_GAME_CATEGORY =
    "select category_name from category where category_id=?";

    public static final String SQL_SELECT_BLOGPOST_CATEGORY =
            "select category_name from category where category_id=?";

    public static final String SQL_SELECT_ALL_GAME =
    "select gm_id,name,description,price,category_id from games order by name";

    public static final String SQL_SELECT_ALL_BLOGPOST =
            "select blog_id,author,description, title, category_id from blogposts order by author";

    public static final String SQL_DELETE_GAME =
    "delete from games where gm_id=?";

    public static final String SQL_DELETE_BLOGPOST =
            "delete from blogposts where blog_id=?";

    public static final String SQL_SELECT_GAME =
    "select gm_id,name,description,price,category_id from games where gm_id=?";

    public static final String SQL_SELECT_BLOGPOST =
            "SELECT blog_id, title, description, author, category_id FROM blogposts WHERE blog_id = ?";


    public static final String SQL_UPDATE_GAME =
    "update games set name=?,description=?,price=?,category_id=?,image=? where gm_id=?";

    public static final String SQL_UPDATE_BLOGPOST=
            "update blogposts set title=?,description=?,author=?,category_id=?, image=? where blog_id=?";

    public static final String SQL_SELECT_ALL_IMAGES=
    "select gm_id,name,description,price,category_id,image from games";

    public static final String SQL_SELECT_ALL_IMAGESS=
            "select blog_id,title,description,author,category_id,image from blogposts";



    public static final String SQL_SELECT_AN_IMAGE=
    "select gm_id,name,description,price,category_id,image from games where gm_id=?";
    public static final String SQL_SELECT_AN_IMAGEE=
            "select blog_id,title,description,author,category_id,image from blogposts where blog_id=?";


    public static final String SQL_INSERT_USER=
    "insert into users(username,password,customer_name,customer_address,customer_number) value (?,?,?,?,?)";

    public static final String SQL_AUTHETICATE_USER=
    "select username,password,role from users where username=?";

    public static final String SQL_INSERT_CART=
    "insert into cart(price,quantity,status,gm_id,username,game_name) value (?,?,?,?,?,?)";

    public static final String SQL_SELECT_ALL_CART_ITEMS=
    "select cart_id,game_name,status,quantity,price from cart where username=? and payment_status='np'";

    public static final String SQL_REMOVE_CART_ITEM=
    "delete from cart where cart_id=?";

    public static final String SQL_UPDATE_CART_ITEM=
    "update cart set quantity=? where cart_id=?";

    public static final String SQL_SELECT_ALL_CART_ITEMS_ID=
    "select cart_id from cart where username=? and payment_status='np'";

    public static final String SQL_GRAND_TOTAL=
    "select price * quantity as subtotal from cart where username=? and payment_status='np'";

    public static final String SQL_INSERT_ORDER=
    "insert into orders(total_amount,order_date,username) value (?,?,?)";

    public static final String SQL_UPDATE_ORDER_CART_ITEMS=
    "update cart set payment_status='p',order_id=? where cart_id=?";

    public static final String SQL_UPDATE_ORDER_PAYMENT=
    "update orders set payment_intent=?,payment_intent_client_secret=? where order_id=?";

    public static final String SQL_SEARCH_GAME_BY_NAME_AND_CATEGORY =
    "select * from games where name like concat('%',?,'%') and category_id=?";

    public static final String SQL_SEARCH_BLOGPOST_BY_NAME_AND_CATEGORY=
            "select * from blogposts where author like concat('%',?,'%') and category_id=?";

    public static final String SQL_SEARCH_GAME_BY_NAME =
    "select * from games where name like concat('%',?,'%')";

    public static final String SQL_SEARCH_BLOGPOST_BY_NAME=
            "select * from blogposts where author like concat('%',?,'%')";



    public static final String SQL_SELECT_USER_PAID_ORDERS=
    "select * from orders where username=? and payment_intent is not null and payment_intent_client_secret is not null;";

    public static final String SQL_SELECT_ALL_PAID_ORDERS=
    "select * from orders where payment_intent is not null and payment_intent_client_secret is not null";

    public static final String SQL_SELECT_USER_PAID_CART_ITEMS=
    "select * from cart where order_id=?";

    public static final String SQL_INSERT_CATEGORY =
            "insert into category(category_name,description) value (?,?)";

    public static final String SQL_SELECT_ALL_CATEGORY =
            "select category_id,category_name,description from category order by category_name";

    public static final String SQL_DELETE_CATEGORY =
            "delete from category where category_id=?";

    public static final String SQL_UPDATE_CATEGORY =
            "update category set category_name = ?, description = ? where category_id = ?";

    public static final String SQL_SELECT_CATEGORY =
            "select category_id,category_name,description from category where category_id = ?";

}

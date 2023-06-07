package com.oynaap.controllers;

import com.oynaap.models.*;
import com.oynaap.repository.SQLCOMMAND;
import com.oynaap.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping
public class ShopController implements SQLCOMMAND {

    @Autowired
    private GameAndBlogpostService gameSvc;

    @Autowired
    private ShopService shopSvc;

    @Autowired
    private CartService cartSvc;

    @Autowired
    private OrderService orderSvc;

    @Autowired
    private CategoryService categorySvc;

    @Autowired
    private JdbcTemplate template;



    @GetMapping(path="/shop")
    public ModelAndView Shop() throws SQLException{
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("shop");
        List<Game> games = gameSvc.getAllGameImages();
        mvc.addObject("games", games);
        List<Category> categories = categorySvc.getAllCategories();
        mvc.addObject("categories",categories);

        return mvc;
   
    }

    @GetMapping(path="/search")
    public ModelAndView Search(
        @RequestParam String name, @RequestParam String category) throws SQLException{
            ModelAndView mvc = new ModelAndView();
            mvc.setViewName("shop");

            if(category.equals("All")){
                System.out.println(">>>>>>  category1: " +category);
                List<Game> games = gameSvc.searchGamesByName(name);
                mvc.addObject("games", games);
            }else{
                System.out.println(">>>>>> category2: " +category);
                SqlRowSet rs = template.queryForRowSet(SQL_SELECT_CATEGORYID,category);
                rs.next();
                Integer category_id=rs.getInt("category_id");
                List<Game> games = gameSvc.searchGamesByNameAndCategory(name,category_id);
                mvc.addObject("games", games);
                System.out.println(">>>>>> category2: " +category);
            }

            List<Category> categories = categorySvc.getAllCategories();
            mvc.addObject("categories",categories);
    
            return mvc;
    }

    @GetMapping(path="/shop/game/{gm_id}")
    public ModelAndView Game(@PathVariable(name="gm_id") Integer gm_id) throws SQLException{
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("shopgame");
        Game game = gameSvc.getOneGameImage(gm_id);
        mvc.addObject("game", game);
        System.out.println(">>>>>> gm_id: " + game.getGm_id());
        
        return mvc;
   
    }

    @GetMapping(path="/register")
    public ModelAndView Register(){
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("register");
        return mvc;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(path="/register/success")
    public ModelAndView registerSuccess(@RequestBody MultiValueMap<String, String> form){
        String username = form.getFirst("username");
        String password = form.getFirst("password");
        String bycrptPassword = passwordEncoder.encode(password);

        String name = form.getFirst("name");
        String address = form.getFirst("address");
        String number = form.getFirst("number");

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(bycrptPassword);
        account.setCustomer_name(name);
        account.setCustomer_address(address);
        account.setCustomer_number(number);

        shopSvc.addNewUser(account);
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("registersuccess");
        return mvc;
    }

    
    @GetMapping(path="/login")
    public ModelAndView login(){
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("login");
        return mvc;
    }

    @GetMapping(path="/cart")
    public ModelAndView cart(Cart cart){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        cart.setUsername(username);

        List <Cart> cartItems = new LinkedList<>();
        cartItems = cartSvc.getAllCartItems(cart);

        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("cart");
        mvc.addObject("cartItems",cartItems);

        Double grandTotal = cartSvc.grandTotal(cart);
        mvc.addObject("grandTotal",grandTotal);
        
        return mvc;
    }


    @PostMapping(path="/addToCart")
    public ModelAndView addToCart(
        @RequestParam (name="status") String status,
        @RequestParam (name="quantity") Integer quantity,
        @RequestParam (name="gm_id") Integer gm_id) throws SQLException{

            Cart cart = new Cart();
    
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();

            Game game = gameSvc.selectGame(gm_id);
            Double price = game.getPrice();
            String Game_name = game.getName();

            cart.setStatus(status);
            cart.setQuantity(quantity);
            cart.setUsername(username);
            cart.setGm_id(gm_id);
            cart.setPrice(price);
            cart.setGame_name(Game_name);

            cartSvc.addNewCartItem(cart);

            ModelAndView mvc = new ModelAndView();
            mvc.setViewName("addtocart");
            mvc.addObject("cart",cart);
        
            return mvc;

    }

    @GetMapping(path="cart/remove/{cart_id}")
    public ModelAndView deleteCartItem(@PathVariable(name="cart_id") Integer cart_id, Cart cart) throws SQLException{

        cartSvc.removeCartItem(cart_id);

        ModelAndView mvc = new ModelAndView();

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        cart.setUsername(username);
        List <Cart> cartItems = new LinkedList<>();
        cartItems = cartSvc.getAllCartItems(cart);
        mvc.addObject("cartItems",cartItems);
        mvc.setViewName("cart");

        Double grandTotal = cartSvc.grandTotal(cart);
        mvc.addObject("grandTotal",grandTotal);
        
        return mvc;
    }

    @GetMapping(path="cart/update/{cart_id}")
    public ModelAndView updateCartItem(@PathVariable(name="cart_id") Integer cart_id, Cart cart,@RequestParam (name="quantity") Integer quantity) throws SQLException{

        cart.setCart_id(cart_id);
        cart.setQuantity(quantity);
        cartSvc.updateCartItem(cart);

        ModelAndView mvc = new ModelAndView();

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        cart.setUsername(username);
        List <Cart> cartItems = new LinkedList<>();
        cartItems = cartSvc.getAllCartItems(cart);
        mvc.addObject("cartItems",cartItems);
        mvc.setViewName("cart");

        Double grandTotal = cartSvc.grandTotal(cart);
        mvc.addObject("grandTotal",grandTotal);
        
        return mvc;
    }

        
    @GetMapping(path="/myaccount")
    public ModelAndView myaccount(){
        ModelAndView mvc = new ModelAndView();
        List <Order> orders = new LinkedList<>();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        orders = orderSvc.selectAllUserPaidOrders(username);
        System.out.println(">>>>>> orders: " +orders);
        mvc.addObject("orders",orders);
        mvc.setViewName("myaccount");
        return mvc;
    }

    @GetMapping(path="myaccount/items/{order_id}")
    public ModelAndView viewUserPaidItems(@PathVariable(name="order_id") Integer order_id) throws SQLException{

        List <Cart> cartItems = new LinkedList<>();
        cartItems = cartSvc.selectUserPaidCartItems(order_id);
        System.out.println(">>>>>> cartItems: " +cartItems);
        ModelAndView mvc = new ModelAndView();
        mvc.addObject("cartItems",cartItems);
        mvc.setViewName("items");
        
        return mvc;
    }

    @GetMapping(path="/admin/order")
    public ModelAndView order(){
        ModelAndView mvc = new ModelAndView();
        List <Order> orders = new LinkedList<>();
        orders = orderSvc.selectAllPaidOrders();
        mvc.addObject("orders",orders);
        mvc.setViewName("order");
        return mvc;
    }

    @GetMapping(path="/admin/order/items/{order_id}")
    public ModelAndView viewOrderPaidItems(@PathVariable(name="order_id") Integer order_id) throws SQLException{

        List <Cart> cartItems = new LinkedList<>();
        cartItems = cartSvc.selectUserPaidCartItems(order_id);
        ModelAndView mvc = new ModelAndView();
        mvc.addObject("cartItems",cartItems);
        mvc.setViewName("items");
        
        return mvc;
    }

    @PostMapping(path="/checkout")
    public ModelAndView checkOut(Cart cart){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        cart.setUsername(username);

        List <Cart> cartItems = new LinkedList<>();
        cartItems = cartSvc.getAllCartItems(cart);

        ModelAndView mvc = new ModelAndView();
        mvc.addObject("cartItems",cartItems);

        Double grandTotal = cartSvc.grandTotal(cart);
        mvc.addObject("grandTotal",grandTotal);
        mvc.addObject("cartId", cart.getCart_id());
        mvc.setViewName("checkout");

        return mvc;
    }





}

package com.mateEase.controller;

import com.mateEase.mapper.UserMapper;
import com.mateEase.pojo.Order;
import com.mateEase.pojo.Product;
import com.mateEase.pojo.User;
import com.mateEase.service.OrderService;
import com.mateEase.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;
    @RequestMapping("/main.html")
    public String toMainPage(Model model,HttpSession session){
        List<Product> products = productService.queryAllProduct();
        model.addAttribute("products",products);
        // 找出已经购买的商品
        String user_name = (String)session.getAttribute("loginUser");
        List<Order> orders = orderService.queryAllOrderByName(user_name);
        List<Integer> buys = new ArrayList<>();
        for (Order order : orders) {
            buys.add(order.getProd_id());
        }
        model.addAttribute("buys",buys);
        return "dashboard";
    }
    @RequestMapping("/admin.html")
    public String toAdminPage(Model model,HttpSession session){
        String user_name = (String)session.getAttribute("loginUser");
        List<Product> products = productService.queryProductByName(user_name);
        model.addAttribute("products",products);
        return "/admin";
    }

    @RequestMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model, HttpSession session){
        // 具体业务

//        if (!StringUtils.isEmpty(username) && "123456".equals(password)){
        User login = new User();
        login.setName(username);
        login.setPwd(password);
        User user = userMapper.queryUser(login);
        if (user!=null) {
            session.setAttribute("loginUser",username);
            session.setAttribute("role",user.getRole());
            if (user.getRole()==1){
                // 买家
                return "redirect:/main.html";
            }else{
                // 卖家
                return "redirect:/admin.html";
            }
        }else {
            model.addAttribute("msg", "用户名或密码错误！");
            return "/index";
        }
    }

    @RequestMapping("/user/logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginUser");
        return "redirect:index.html";
    }
}

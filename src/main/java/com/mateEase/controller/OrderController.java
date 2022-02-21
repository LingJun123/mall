package com.mateEase.controller;

import com.mateEase.pojo.Order;
import com.mateEase.pojo.Product;
import com.mateEase.service.OrderService;
import com.mateEase.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;

    @RequestMapping("/orders")
    public String getAllOrder(Model model, HttpSession session){
        String user_name = (String) session.getAttribute("loginUser");
        List<Order> orders = orderService.queryAllOrderByName(user_name);
//        System.out.println(orders);
        List<tmpOrder> tmpOrders = new ArrayList<>();
        float money = 0.0f;
        for (Order order : orders) {
            Product product = productService.queryProductByID(order.getProd_id());
            tmpOrder to = new tmpOrder();
            to.setProd_name(product.getProd_name());
            to.setProd_pic(product.getProd_pic());
            to.setOrder_date(order.getOrder_date());
            to.setPrice(order.getPrice());
            to.setOrder_count(order.getOrder_count());
            to.setMoney(to.getPrice()*to.getOrder_count());
            money += to.getMoney();
            tmpOrders.add(to);
        }
//        System.out.println(tmpOrders);
        model.addAttribute("tmpOrders",tmpOrders);
        model.addAttribute("money",money);
        return "/order/list";
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class tmpOrder{
    private String prod_name;
    private String prod_pic;
    private Date order_date;
    private float price;
    private float money;
    private int order_count;
}

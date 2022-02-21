package com.mateEase.controller;

import com.mateEase.pojo.Basket;
import com.mateEase.pojo.Order;
import com.mateEase.pojo.Product;
import com.mateEase.service.BasketService;
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
import java.util.List;

@Controller
@RequestMapping("/basket")
public class BasketController {
    @Autowired
    BasketService basketService;
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;

    @RequestMapping("/baskets")
    public String getAllBasket(HttpSession session, Model model){
        String user_name = (String) session.getAttribute("loginUser");
        List<Basket> baskets = basketService.queryBasketByName(user_name);
        List<tmpBasket> tmpBaskets = new ArrayList<>();
        for (Basket basket : baskets) {
            tmpBasket tb = new tmpBasket();
            tb.setCount(basket.getBasket_count());
            tb.setBasket_id(basket.getBasket_id());
            Product product = productService.queryProductByID(basket.getBasket_id());
            tb.setProd_name(product.getProd_name());
            tb.setPrice(product.getPrice());
            tb.setMoney(tb.getPrice()*tb.getCount());
            tmpBaskets.add(tb);
        }
        model.addAttribute("tmpBaskets",tmpBaskets);
        return "/basket/list";
    }
    @RequestMapping("/buy")
    public String buy(HttpSession session){
        String user_name = (String)session.getAttribute("loginUser");
        // 加入到订单表
        List<Basket> baskets = basketService.queryBasketByName(user_name);
        for (Basket basket : baskets) {
            Order order = new Order();
            order.setProd_id(basket.getProd_id());
            order.setOrder_count(basket.getBasket_count());
            order.setPrice(productService.queryProductByID(basket.getProd_id()).getPrice());
            order.setUser_name(user_name);
            orderService.insertOrder(order);
        }
        // 清空购物车
        basketService.clearBasketByName(user_name);
        return "redirect:/main.html";
    }


}
@Data
@AllArgsConstructor
@NoArgsConstructor
class tmpBasket{
    private int basket_id;
    private String prod_name;
    private float price;
    private float money;
    private int count;
}

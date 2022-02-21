package com.mateEase.controller;

import com.mateEase.pojo.Basket;
import com.mateEase.pojo.Order;
import com.mateEase.pojo.Product;
import com.mateEase.service.BasketService;
import com.mateEase.service.OrderService;
import com.mateEase.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    @Autowired
    BasketService basketService;

    @RequestMapping("/allProducts")
    public String queryAllProduct(){
        return "redirect:/main.html";
    }
    @RequestMapping("/allNoBuyProd")
    public String queryAllNoBuyProd(Model model, HttpSession session){
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
        return "/prod/list";
    }
    @RequestMapping("/detail/{prod_id}")
    public String queryProductByID(@PathVariable("prod_id") int prod_id, HttpSession session,Model model){
        Product product = productService.queryProductByID(prod_id);
        model.addAttribute("product",product);
        // 查询是否已经购买
        String user_name = (String)session.getAttribute("loginUser");
        Order order = new Order();
        order.setProd_id(prod_id);
        order.setUser_name(user_name);
        Order tmpOrder = orderService.queryOrder(order);
        boolean flag = false;
        if (tmpOrder != null){
            flag = true;
        }
        model.addAttribute("flag",flag);
        int role = (int) session.getAttribute("role");
        System.out.println(role);
        model.addAttribute("role",role);
        return "/prod/detail";
    }

    @RequestMapping("/buy/{prod_id}")
    public String buy(@PathVariable("prod_id") int prod_id,HttpSession session){
        String loginUser = (String) session.getAttribute("loginUser");
        //
        Basket basket = new Basket();
        basket.setUser_name(loginUser);
        basket.setProd_id(prod_id);
        Basket tmpBasket = basketService.queryBasketByNameID(basket);
        if (tmpBasket != null){
            basket.setBasket_count(tmpBasket.getBasket_count()+1);
            basketService.repeatAddBasket(basket);
        }else{
            basketService.insertBasket(basket);
        }
        return "redirect:/product/detail/"+prod_id;
    }

    @RequestMapping("/put")
    public String toAdd(){
        return "/prod/add";
    }
    @RequestMapping("/edit/{prod_id}")
    public String toEdit(@PathVariable("prod_id") int prod_id,Model model){
        Product product = productService.queryProductByID(prod_id);
        model.addAttribute("product",product);
        return "/prod/update";
    }


    @Value("${file.upload.path}")
    private String filePath;

    @Value("${file.upload.path.relative}")
    private String fileRelativePath;

    @PostMapping("/add")
    public String add(Product product, @RequestParam("imgFile") MultipartFile file, HttpSession session){
        // 获取上传文件名
        String filename = file.getOriginalFilename();
        String path = filePath;

        String user = (String) session.getAttribute("loginUser");
        product.setUser_name(user);
        // 新建文件
        File filepath = new File(path, filename);
        // 判断路径是否存在，如果不存在就创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }
        try {
            // 写入文件
            file.transferTo(new File(path + File.separator + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String prod_pic = fileRelativePath + filename;
        product.setProd_pic(prod_pic);
        productService.insertProduct(product);
        return "redirect:/admin.html";
    }

    @PostMapping("/update")
    public String update(Product product,@RequestParam("imgFile") MultipartFile file){
        Product notEdit = productService.queryProductByID(product.getProd_id());
        product.setProd_pic(notEdit.getProd_pic());
        product.setUser_name(notEdit.getUser_name());
        // 获取上传文件名
        String filename = file.getOriginalFilename();
        //选择上传本地文件，picture中picUrl='',需要将文件地址插入picUrl
        if (!"".equals(filename)){
            String path = filePath;
            // 新建文件
            File filepath = new File(path, filename);
            // 判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            try {
                // 写入文件
                file.transferTo(new File(path + File.separator + filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String prod_pic = fileRelativePath + filename;
            product.setProd_pic(prod_pic);
        }
        System.out.println(product);
        productService.updateProduct(product);
        return "redirect:/admin.html";
    }

    @RequestMapping("/delete/{prod_id}")
    public String delete(@PathVariable("prod_id") int prod_id){
        productService.deleteProductByID(prod_id);
        return "redirect:/admin.html";
    }
}

package com.example.affablebeanui.controller;

import com.example.affablebeanui.model.CartItem;
import com.example.affablebeanui.model.ProductDto;
import com.example.affablebeanui.service.CartService;
import com.example.affablebeanui.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ui")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CartService cartService;
    boolean checkButton = false;

    @PostMapping("/make-transaction")
    public String makeTransaction(@RequestParam("accountNumber")
                                  String accountNumber,
                                  @ModelAttribute("totalPrice") double totalPrice) {
        String responseString = productService.makeTransaction(accountNumber, totalPrice);
        if (responseString.equalsIgnoreCase("successful transaction!")) {
            return "success";
        } else {
            throw new RuntimeException("Transaction Failed");
        }
    }

    @GetMapping("/home")
    public String home() {
        checkButton = false;

//        Set<CartItem> cartItems = cartService.cartItems()
//                .stream()
//                .map(c -> {
//                    c.setVisible(false);
//                    return c;
//                })
//                .collect(Collectors.toSet());
//
//        cartService.setCartItems(cartItems);

        for (CartItem cartItem : cartService.cartItems()) {
            cartItem.setVisible(false);
        }

        return "home";
    }

    @GetMapping("/products")
    public String findProductsByCategoryName(@RequestParam("category") String category,
                                             Model model) {
        model.addAttribute("products", productService.listProductsByCategoryName(category));
        return "products";
    }

    @ModelAttribute("cartSize")
    public int cartSize() {
        return cartService.cartSize();
    }

    @GetMapping("/add-cart")
    public String addToCart(@RequestParam("id") int productId) {
        ProductDto productDto = productService.findProductDtoById(productId);
        cartService.addToCart(productService.toCartItem(productDto));

        return "redirect:/ui/products?category=" + productDto.getCategoryName();
    }

    @GetMapping("/view-cart")
    public String viewCart(Model model) {
        model.addAttribute("cartItem", new CartItem());
        model.addAttribute("cartItems", cartService.cartItems());
        model.addAttribute("checkButton", checkButton);
        return "cartView";
    }

    @PostMapping("/check-price")
    public String check(CartItem cartItem) {
        int counter = 0;

        for (CartItem cart : cartService.cartItems()) {
            cart.setQuantity(cartItem.getQuantityList().get(counter));
            cart.setVisible(true);
            counter++;
        }

        checkButton = true;
        return "redirect:/ui/view-cart";
    }

    @ModelAttribute("totalPrice")
    public double totalPrice() {
        return cartService.cartItems()
                .stream().map(item -> item.getPrice() * item.getQuantity())
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    @GetMapping("/delete")
    public String removeCartItem(@RequestParam("id") int id) {
        cartService.removeCartItem(id);
        return "redirect:/ui/view-cart";
    }

    @GetMapping("/clear-cart")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/ui/view-cart";
    }

    @GetMapping("/checkout")
    public String checkoutPage(Model model, @ModelAttribute("totalPrice") double totalPrice) {
        model.addAttribute("deliveryCharge", 3.0);
        return "checkout";
    }
}

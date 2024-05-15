package com.ashapiro.auction.controller;

import com.ashapiro.auction.dto.product.ProductDto;
import com.ashapiro.auction.dto.product.ProductWithEmailDto;
import com.ashapiro.auction.dto.product.SimpleProductDto;
import com.ashapiro.auction.entity.statuses.ProductStatus;
import com.ashapiro.auction.service.ProductService;
import com.ashapiro.auction.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/user/{userId}")
    public String getProductsByUserId(@PathVariable Long userId, Model model) {
        List<SimpleProductDto> productList = productService.getProductsByUserId(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("productList", productList);
        return "product/myProducts";
    }

    @GetMapping("/user/product/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        ProductDto product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product/product";
    }

    @GetMapping("/admin/all")
    public String getAll(@ModelAttribute("message") String message, Model model) {
        List<ProductWithEmailDto> products = productService.getAllProductsWithUserEmail();
        ProductStatus.Status[] statuses = ProductStatus.Status.values();
        model.addAttribute("products", products);
        model.addAttribute("statuses", statuses);
        if (message != null && !message.isEmpty()) {
            model.addAttribute("message", message);
        }
        return "product/allProducts";
    }

    @GetMapping("/admin/add")
    public String add(Model model) {
        List<String> emails = userService.getAllUsersEmail();
        model.addAttribute("product", new ProductWithEmailDto());
        model.addAttribute("emails", emails);
        return "product/addProduct";
    }

    @PostMapping("/admin/add")
    public String add(ProductWithEmailDto productWithEmailDto, RedirectAttributes redirectAttributes) {
        String message = productService.add(productWithEmailDto);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/products/admin/add";
    }

    @GetMapping("/admin/product/update/{productId}")
    public String updateProduct(@PathVariable Long productId, @ModelAttribute("message") String message, Model model) {
        ProductWithEmailDto product = productService.getProductsWithUserEmail(productId);
        model.addAttribute("product", product);
        if (message != null && !message.isEmpty()) {
            model.addAttribute("message", message);
        }
        return "product/updateProduct";
    }

    @PutMapping("/admin/product/update")
    public String updateProduct(ProductWithEmailDto productWithEmailDto, RedirectAttributes redirectAttributes) {
        String message = productService.updateProduct(productWithEmailDto);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/products/admin/product/update/" + productWithEmailDto.getId();
    }

    @PatchMapping("/admin/product/update/status/{productId}")
    public String updateAuctionStatus(@PathVariable Long productId, @RequestParam("status") String status,
                                      RedirectAttributes redirectAttributes) {
        String message = productService.updateStatus(productId, status);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/products/admin/all";
    }

    @GetMapping("/admin/product/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId, RedirectAttributes redirectAttributes) {
        String message = productService.delete(productId);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/products/admin/all";
    }
}

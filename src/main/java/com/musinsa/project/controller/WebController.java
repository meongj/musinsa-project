package com.musinsa.project.controller;

import com.musinsa.project.domain.Category;
import com.musinsa.project.repository.BrandRepository;
import com.musinsa.project.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final BrandService brandService;
    private final BrandRepository brandRepository;

    @GetMapping("/")
    public String home() {
        return "redirect:/client";
    }

    @GetMapping("/client")
    public String clientView(Model model) {
        // 클라이언트용 조회 데이터
        model.addAttribute(
            "lowestPrices",
            brandService.findLowestPricesByCategory()
        );
        model.addAttribute(
            "lowestTotalPrice",
            brandService.findLowestTotalPriceBrand()
        );

        model.addAttribute("brands", brandRepository.findAll());
        model.addAttribute("categories", Category.values());
        return "client";
    }

    @GetMapping("/admin")
    public String adminView(Model model) {
        // 어드민용 전체 데이터
        model.addAttribute("brands", brandRepository.findAll());
        model.addAttribute("categories", Category.values());
        return "admin";
    }
}

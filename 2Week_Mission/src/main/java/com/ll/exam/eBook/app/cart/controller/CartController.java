package com.ll.exam.eBook.app.cart.controller;

import com.ll.exam.eBook.app.cart.entity.CartItem;
import com.ll.exam.eBook.app.cart.service.CartService;
import com.ll.exam.eBook.app.member.entity.Member;
import com.ll.exam.eBook.app.security.dto.MemberContext;
import com.ll.exam.eBook.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public String showItems(@AuthenticationPrincipal MemberContext memberContext, Model model) {
        Member buyer = memberContext.getMember();

        List<CartItem> items = cartService.getItemsByBuyer(buyer);

        model.addAttribute("items", items);

        return "cart/list";
    }

    @PostMapping("/removeItems")
    @PreAuthorize("isAuthenticated()")
    public String removeItems(@AuthenticationPrincipal MemberContext memberContext, String ids) {
        Member buyer = memberContext.getMember();

        String[] idsArr = ids.split(",");

        Arrays.stream(idsArr)
                .mapToLong(Long::parseLong)
                .forEach(id -> {
                    CartItem cartItem = cartService.findItemById(id).orElse(null);

                    if (cartService.actorCanDelete(buyer, cartItem)) {
                        cartService.removeItem(cartItem);
                    }
                });

        return "redirect:/cart/list?msg=" + Util.url.encode("%d건의 품목을 삭제하였습니다.".formatted(idsArr.length));
    }
}
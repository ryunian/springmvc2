package com.shoon.mvc.web.validation;

import com.shoon.mvc.domain.item.Item;
import com.shoon.mvc.domain.item.ItemRepository;
import com.shoon.mvc.domain.item.SaveCheck;
import com.shoon.mvc.domain.item.UpdateCheck;
import com.shoon.mvc.web.validation.form.ItemSaveForm;
import com.shoon.mvc.web.validation.form.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v4/items")
@RequiredArgsConstructor
public class ValidationItemControllerV4 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v4/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v4/addForm";
    }


    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute("item") ItemSaveForm saveForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // 특정 필드가 아닌 복합 룰 검증
       if (saveForm.getPrice() != null && saveForm.getQuantity() != null) {
            int resultPrice = saveForm.getPrice() * saveForm.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }


        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors : {}", bindingResult);
            return "validation/v4/addForm"; // 다시 입력폼으로 forward
        }

        // 성공로직
        Item item = Item.builder()
                .itemName(saveForm.getItemName())
                .price(saveForm.getPrice())
                .quantity(saveForm.getQuantity())
                .build();

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v4/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute("item") ItemUpdateForm updateForm, BindingResult bindingResult) {
        if (updateForm.getPrice() != null && updateForm.getQuantity() != null) {
            int resultPrice = updateForm.getPrice() * updateForm.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v4/editForm";
        }

        Item item = Item.builder()
                .itemName(updateForm.getItemName())
                .price(updateForm.getPrice())
                .quantity(updateForm.getQuantity())
                .build();

        itemRepository.update(itemId, item);
        return "redirect:/validation/v4/items/{itemId}";
    }
}


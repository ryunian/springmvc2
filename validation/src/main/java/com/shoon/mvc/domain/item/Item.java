package com.shoon.mvc.domain.item;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
/*
// 메시지코드 {ScriptAssert.item , ScriptAssert} 가 생성된다.
// 문제점 : 기능적으로 부족한게 많다. 따로 사용법을 익혀야되는 부분이 있다.
// 그러므로 오브젝트 에러는 controller 에서 자바코드로 작성하는 것을 추천한다.
@ScriptAssert(lang = "javascript",
        script = "_this.price * _this.quantity >= 100000",
        message = "총합이 10000원 넘게 설정해주세요.")
*/
public class Item {

    @NotNull(groups = UpdateCheck.class)
    private Long id;

    @NotBlank(groups = {SaveCheck.class, UpdateCheck.class})
    private String itemName;

    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    @Range(min = 1000, max = 1000000, groups = {SaveCheck.class, UpdateCheck.class})
    private Integer price;

    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    @Max(value = 9999, groups = SaveCheck.class)
    private Integer quantity;

    @Builder
    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}

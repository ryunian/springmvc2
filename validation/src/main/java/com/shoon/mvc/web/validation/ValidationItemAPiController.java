package com.shoon.mvc.web.validation;

import com.shoon.mvc.web.validation.form.ItemSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/validation/api/items")
public class ValidationItemAPiController {

    // @ModelAttribute 와는 다르게 전체 객체 단위로 적용된다.
    // 즉, 메시지 컨버터가 작동이 성공하여 ItemSaveForm 객체를 만들어야지 validation 및 컨트롤러가 호출된다
    //
    @PostMapping("/add")
    public Object addItem(@RequestBody @Validated ItemSaveForm form, BindingResult bindingResult){
        log.info("API 컨트롤러 호출");

        if(bindingResult.hasErrors()){
            log.info("검증 오류 발생 errors={}", bindingResult);
            return bindingResult.getAllErrors();
        }
        log.info("성공 로직 실행");
        return form;
    }
}

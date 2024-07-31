package com.example.backend.tag.controller;

import com.example.backend.base.dto.BaseResponse;
import com.example.backend.tag.dto.*;
import com.example.backend.tag.entity.Tag;
import com.example.backend.tag.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/tags")
public class TagAdminController {
    private final TagService tagService;

    @Operation(summary = "Create", description = "새로운 태그를 생성합니다.")
    @PostMapping("")
    public String createTag(@ModelAttribute("category") TagNameRequest tagNameRequest, Model model) {
        try {
            BaseResponse baseResponse = tagService.createTag(tagNameRequest);
            model.addAttribute("response", baseResponse);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/tags";
    }

    @Operation(summary = "Read", description = "공개된 태그를 조회합니다.")
    @GetMapping("")
    public String readTags(HttpServletRequest request, Model model) {
        try {
            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            model.addAttribute("_csrf", csrfToken);
            model.addAttribute("tags", tagService.getAllTags());
        } catch (Exception e){
            e.printStackTrace();
        }
        return "tag/tags";
    }

    @Operation(summary = "Read", description = "태그 이력 조회합니다.")
    @GetMapping("/history")
    public String readTagHistory(HttpServletRequest request, Model model) {
        try {
            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            model.addAttribute("_csrf", csrfToken);
            model.addAttribute("tagHistory", tagService.getAllTagHistory());
        } catch (Exception e){
            e.printStackTrace();
        }
        return "tag/tags_history";
    }

    @Operation(summary = "Update", description = "기존 태그를 수정합니다.")
    @PostMapping("/edit/{id}")
    public String editTag(@PathVariable Long id, @ModelAttribute("post") TagIdNameRequest tagIdNameRequest, Model model) {
        try {
            tagIdNameRequest.setId(id);
            BaseResponse baseResponse = tagService.updateTag(tagIdNameRequest);
            model.addAttribute("response", baseResponse);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/tags";
    }

    @Operation(summary = "Delete", description = "태그를 삭제합니다.")
    @PostMapping("/delete/{id}")
    public String deleteTag(@PathVariable Long id, Model model) {
        try {
            TagIdRequest tagIdRequest = new TagIdRequest();
            tagIdRequest.setId(id);
            BaseResponse baseResponse = tagService.deleteTag(tagIdRequest);
            model.addAttribute("response", baseResponse);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/tags";
    }

    @Operation(summary = "Form", description = "태그 작성 폼을 보여줍니다.")
    @GetMapping("/new")
    public String createTagForm(Model model) {
        try {
            model.addAttribute("tag", new Tag());
        } catch (Exception e){
            e.printStackTrace();
        }
        return "tag/create_tag";
    }

    @Operation(summary = "Form", description = "태그 수정 폼을 보여줍니다.")
    @GetMapping("/edit/{id}")
    public String editTagForm(@PathVariable long id, Model model) {
        try {
            TagDto tag = tagService.getTags(id);
            if (tag == null) {
                return "redirect:/admin/tags";
            }
            model.addAttribute("tag", tag);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "tag/edit_tag";
    }
}

package com.fzcoder.opensource.blog.controller;

import com.fzcoder.opensource.blog.bean.JsonResponse;
import com.fzcoder.opensource.blog.dto.TagForm;
import com.fzcoder.opensource.blog.entity.Tag;
import com.fzcoder.opensource.blog.service.ArticleService;
import com.fzcoder.opensource.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleService articleService;

    @PostMapping("/admin/tag")
    public Object addTag(@RequestBody TagForm form) {
        if (tagService.save(form)) {
            return new JsonResponse(HttpServletResponse.SC_OK, "添加成功!");
        } else {
            return new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, "添加失败!");
        }
    }

    @GetMapping("/admin/tag/list")
    public Object getList(@RequestParam Map<String, Object> params) {
        return new JsonResponse(tagService.list(params, false));
    }

    @PutMapping("/admin/tag")
    public Object updateTag(@RequestBody Tag tag) {
        if (tagService.update(tag)) {
            return new JsonResponse(HttpServletResponse.SC_OK, "修改成功!");
        } else {
            return new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, "修改失败!");
        }
    }

    @DeleteMapping("/admin/tag/{id}")
    public Object deleteTag(@PathVariable("id") String id) {
        if (tagService.removeById(id)) {
            return new JsonResponse(HttpServletResponse.SC_OK, "删除成功!");
        } else {
            return new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, "删除失败!");
        }
    }
}

package com.fzcoder.controller;


import com.fzcoder.entity.Carousel;
import com.fzcoder.bean.JsonResponse;
import com.fzcoder.service.CarouselService;
import com.fzcoder.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author frank fang
 * @since 2020-03-30
 */
@RestController
@RequestMapping("/api")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;

    @PostMapping("/admin/setting/carousel")
    public Object add (@RequestBody Carousel entity) {
        if (carouselService.save(entity)) {
            return new JsonResponse(HttpUtils.Status_OK, "添加成功！");
        } else {
            return new JsonResponse(HttpUtils.Status_BadRequest, "添加失败！");
        }
    }

    @GetMapping("/admin/setting/carousel/{id}")
    public Object get (@PathVariable("id") Integer id) {
        return new JsonResponse(carouselService.getById(id));
    }

    @GetMapping("/carousel")
    public Object list() {
        return new JsonResponse(carouselService.list());
    }

    @PutMapping("/admin/setting/carousel")
    public Object update (Carousel entity) {
        if (carouselService.updateById(entity)) {
            return new JsonResponse(HttpUtils.Status_OK, "修改成功！");
        } else {
            return new JsonResponse(HttpUtils.Status_BadRequest, "修改失败！");
        }
    }

    @DeleteMapping("/admin/setting/carousel/{id}")
    public Object delete (@PathVariable("id") Integer id) {
        if (carouselService.removeById(id)) {
            return new JsonResponse(HttpUtils.Status_OK, "删除成功！");
        } else {
            return new JsonResponse(HttpUtils.Status_BadRequest, "删除失败！");
        }
    }
}

package com.fzcoder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzcoder.annotation.*;
import com.fzcoder.bean.ArticleDownloadConfigInfo;
import com.fzcoder.dto.ArticleForm;
import com.fzcoder.mapper.CategoryMapper;
import com.fzcoder.service.TagService;
import com.fzcoder.utils.IdGenerator;
import com.fzcoder.vo.ArticleView;
import com.fzcoder.vo.Post;
import com.fzcoder.vo.TagView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzcoder.entity.Article;
import com.fzcoder.mapper.ArticleMapper;
import com.fzcoder.service.ArticleService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private ArticleMapper articleMapper;

    private CategoryMapper categoryMapper;

    private TagService tagService;

    public ArticleServiceImpl(ArticleMapper articleMapper, CategoryMapper categoryMapper, TagService tagService) {
        this.articleMapper = articleMapper;
        this.categoryMapper = categoryMapper;
        this.tagService = tagService;
    }

    @Record(type = RecordType.ARTICLE, operationType = RecordOperationType.CREATE)
    @Override
    public boolean save(@RecordParam(RecordParamType.ENTITY) ArticleForm form,
                        @RecordParam(RecordParamType.DATE) Date date) {
        try {
            // 文章id的容量
            int max = 1000;
            // 生成文章id
            Long id = IdGenerator.createIdByDate(date, max);
            log.info("successful to create article id: " + id.toString());
            // 判断id是否重复
            Long id_base = id;
            Long max_id = IdGenerator.getMaxIdValueForCreateByDate(date, max);
            while (articleMapper.countById(id) > 0) {
                id = (id + 1) % max_id;
                if (id.equals(id_base)) {
                    max *= 10;
                    id = IdGenerator.createIdByDate(date, max);
                    id_base = id;
                    max_id = IdGenerator.getMaxIdValueForCreateByDate(date, max);
                }
                if (max > 10000) {
                    return false;
                }
            }
            // 填入id
            form.setId(id);
            // 构建Article对象
            Article article = form.build();
            // 对时间进行格式化
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 设置时区为东8区(北京时间)
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            // 设置文章创建时间
            article.setDate(sdf.format(date));
            // 设置文章最后一次修改时间
            article.setUpdateTime(sdf.format(date));
            // 将文章添加至数据库中
            boolean result = articleMapper.insert(article) > 0;
            // 处理文章标签
            log.info("try to process tags...");
            tagService.saveRelation(form.getTags(), id);
            // 返回结果
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Post getViewById(Long id) {
        return articleMapper.selectPostById(id);
    }

    @Override
    public ArticleForm getFormById(Long id) {
        return new ArticleForm(articleMapper.selectById(id), tagService.listRelation(id));
    }

    @Override
    public IPage<ArticleView> getPages(Integer uid, String keyword,
                                       long pageNum, long pageSize, int status, Map<String, Object> params) {
        QueryWrapper<ArticleView> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("author_id", uid);
        queryWrapper.eq("status", status);
        queryWrapper.like("title", keyword);
        if (params.containsKey("order_by") && params.containsKey("is_reverse")) {
            queryWrapper.orderBy(true,
                    params.get("is_reverse").toString().equals("false"),
                    params.get("order_by").toString());
        }
        // 当路径参数中存在category_id时
        if (params.containsKey("category_id")) {
            if (params.get("category_id").toString().equals("default")) {
                queryWrapper.isNull("category_id");
            } else {
                queryWrapper.eq("category_id", params.get("category_id"));
            }
        }
        return articleMapper.selectPages(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @Override
    public void upload(HttpServletRequest request, HttpServletResponse response) {
        log.info("[upload]-->Start to upload file");
        // 获取上传的二进制文件
        MultipartFile multipartFile = ((MultipartHttpServletRequest) request).getFile("file");
        // 判断上传的二进制文件是否为空
        if (multipartFile == null) {
            log.error("[upload]-->Not found the binary file");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            try {
                // 设置响应体类型和字符集
                response.setContentType("text/plain;charset=utf-8");
                // 将文章内容写入到输出流
                response.getWriter().write(new String(multipartFile.getBytes(), StandardCharsets.UTF_8));
            } catch (Exception e) {
                log.error("[upload]-->There has something wrong with the upload", e);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } finally {
                log.info("[upload]-->End of upload");
            }
        }
    }

    @Override
    public void download(Long articleId, HttpServletResponse response, ArticleDownloadConfigInfo info) {
        // 获取文章信息
        Article article = articleMapper.selectById(articleId);
        // 获取markdown格式的文章内容
        String content = article.getContentMd();
        // 添加头部信息
        if (info.isWithArticleInfo()) {
            if (!"YAML".equals(info.getInfoFormat()) && !"TOML".equals(info.getInfoFormat())) {
                try{
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "信息格式出错!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            } else {
                // 初始化文章信息
                String header = "";
                // 写入文章信息
                switch (info.getInfoFormat()) {
                    case "YAML":
                        // 标题
                        if (info.isWithTitle()) {
                            header = header.concat("title: " + article.getTitle() + "\n");
                        }
                        // 创建时间
                        if (info.isWithCreateTime()) {
                            header = header.concat("date: " + article.getDate() + "\n");
                        }
                        // 最后一次修改时间
                        if (info.isWithUpdateTime()) {
                            header = header.concat("updated: " + article.getUpdateTime() + "\n");
                        }
                        // 标签
                        if (info.isWithTags()) {
                            header = header.concat("tags: \n");
                            List<TagView> tagViews = tagService.listRelation(articleId);
                            for (TagView tagView : tagViews) {
                                header = header.concat("- " + tagView.getTagName() + "\n");
                            }
                        }
                        // 目录
                        if (info.isWithCategory()) {
                            header = header.concat("categories: " +
                                    categoryMapper.selectById(article.getCategoryId()).getName() + "\n");
                        }
                        // 描述
                        if (info.isWithDescription()) {
                            header = header.concat("description: " + article.getIntroduction() + "\n");
                        }
                        // 封面URL
                        if (info.isWithCoverUrl()) {
                            header = header.concat("cover: " + article.getCover() + "\n");
                        }
                        // 自定义信息
                        if (info.getCustomInfo() != null && !info.getCustomInfo().isEmpty()) {
                            for (Map.Entry<String, Object> entry : info.getCustomInfo().entrySet()) {
                                header = header.concat(entry.getKey() + ": " +
                                        entry.getValue().toString() + "\n");
                            }
                        }
                        // 写入首尾分隔符
                        header = "---\n".concat(header);
                        header = header.concat("---\n");
                        break;
                    case "TOML":
                        // 标题
                        if (info.isWithTitle()) {
                            header = header.concat("title = \"" + article.getTitle() + "\"\n");
                        }
                        // 创建时间
                        if (info.isWithCreateTime()) {
                            header = header.concat("date = \"" + article.getDate() + "\"\n");
                        }
                        // 最后一次修改时间
                        if (info.isWithUpdateTime()) {
                            header = header.concat("updated = \"" + article.getUpdateTime() + "\"\n");
                        }
                        // 标签
                        if (info.isWithTags()) {
                            header = header.concat("tags = [ ");
                            List<TagView> tagViews = tagService.listRelation(articleId);
                            for (TagView tagView : tagViews) {
                                header = header.concat("\"" + tagView.getTagName() + "\", ");
                            }
                            header = header.substring(0, header.length() - 2);
                            header = header.concat(" ]\n");
                        }
                        // 目录
                        if (info.isWithCategory()) {
                            header = header.concat("categories = \"" +
                                    categoryMapper.selectById(article.getCategoryId()).getName() + "\"\n");
                        }
                        // 描述
                        if (info.isWithDescription()) {
                            header = header.concat("description = \"" + article.getIntroduction() + "\"\n");
                        }
                        // 封面URL
                        if (info.isWithCoverUrl()) {
                            header = header.concat("cover = \"" + article.getCover() + "\"\n");
                        }
                        // 自定义信息
                        if (info.getCustomInfo() != null && !info.getCustomInfo().isEmpty()) {
                            for (Map.Entry<String, Object> entry : info.getCustomInfo().entrySet()) {
                                header = header.concat(entry.getKey() + " = \"" +
                                        entry.getValue().toString() + "\"\n");
                            }
                        }
                        // 写入首尾分隔符
                        header = "+++\n".concat(header);
                        header = header.concat("+++\n");
                        break;
                    default:
                        break;
                }
                // 将文章信息写入文章内容中
                content = header.concat(content);
            }
        }
        // 将文章内容写入输出流
        try(BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(content.getBytes()));
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {
            log.info("[Download]-->Start to download file...");
            // 设置响应体内容格式
            response.setContentType("application/octet-stream");
            // 设置文件名
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    article.getId().toString() + ".md");
            log.info("[Download]-->Filename: " + article.getId().toString() + ".md");
            // 将markdown格式的内容写入到输出流
            int len;
            byte[] buf = new byte[1024];
            while ((len = bis.read(buf)) != -1) {
                bos.write(buf, 0, len);
            }
        } catch (IOException e) {
            log.error("[Download]-->There has something wrong with the download", e);
        } finally {
            log.info("[Download]-->End of download");
        }
    }

    @Record(type = RecordType.ARTICLE, operationType = RecordOperationType.UPDATE)
    @Override
    public boolean update(@RecordParam(RecordParamType.ENTITY) ArticleForm form,
                          @RecordParam(RecordParamType.DATE) Date date,
                          @RecordParam(RecordParamType.STATUS) Integer beforeStatus) {
        // 获取文章实体对象
        Article article = form.build();
        // 对时间进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 设置时区为东8区(北京时间)
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        // 设置时间
        article.setUpdateTime(sdf.format(date));
        // 处理文章标签
        tagService.saveOrUpdateRelation(form.getTags(), article.getId());
        // 更新文章并返回结果
        return articleMapper.updateById(article) > 0;
    }

    @Record(type = RecordType.ARTICLE, operationType = RecordOperationType.DELETE)
    @Override
    public boolean removeById(@RecordParam(RecordParamType.ENTITY) ArticleForm form,
                              @RecordParam(RecordParamType.DATE) Date date) {
        // 1.删除所有tag-article关联
        tagService.removeRelationByAid(form.getId());
        // 2.删除文章并返回结果
        return articleMapper.deleteById(form.getId()) > 0;
    }
}

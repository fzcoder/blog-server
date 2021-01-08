package com.fzcoder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzcoder.bean.ArticleDownloadConfigInfo;
import com.fzcoder.mapper.CategoryMapper;
import com.fzcoder.view.ArticleView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzcoder.entity.Article;
import com.fzcoder.mapper.ArticleMapper;
import com.fzcoder.service.ArticleService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;

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
                            String[] tags = article.getTags().split(","); // 标签数组
                            for (String tag : tags) {
                                header = header.concat("- " + tag + "\n");
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
                            String[] tags = article.getTags().split(","); // 标签数组
                            for (String tag : tags) {
                                header = header.concat("\"" + tag + "\", ");
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
}

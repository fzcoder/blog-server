package com.fzcoder.opensource.blog.bean;

import com.fzcoder.opensource.blog.vo.ArticleView;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dynamic {

    private String timeStamp;

    private List<ArticleView> content;
}

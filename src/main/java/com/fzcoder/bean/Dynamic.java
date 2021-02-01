package com.fzcoder.bean;

import com.fzcoder.vo.ArticleView;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dynamic {

    private String timeStamp;

    private List<ArticleView> content;
}

package com.fzcoder.bean;

import com.fzcoder.entity.Event;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dynamic {

    @ApiModelProperty(value = "时间戳")
    private String timeStamp;

    @ApiModelProperty(value = "动态内容列表")
    private List<Event> content;

    /* public Dynamic() {
    }

    public Dynamic(String timeStamp, String type, List<Map<String, Object>> content) {
        this.timeStamp = timeStamp;
        this.type = type;
        this.content = content;
    }

    public String getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Map<String, Object>> getContent() {
        return this.content;
    }

    public void setContent(List<Map<String, Object>> content) {
        this.content = content;
    }

    /**
     * 重新实现比较接口
     * @param d
     * @return
     */
    // @Override
    /* public int compareTo(Dynamic d) {
        return this.timeStamp.compareTo(d.timeStamp);
    }


    public int hashCode () {
        // 这里将所有的 hashCode 值全部置0，目的是让 HashSet 调用 equals() 进行判断
        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dynamic)) {
            return false;
        }
        Dynamic d = (Dynamic)o;

        return this.timeStamp.equals(d.getTimeStamp());
    } */
}

package com.fzcoder.bean;

import com.fzcoder.entity.Event;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dynamic {

    private String timeStamp;

    private List<Event> content;
}

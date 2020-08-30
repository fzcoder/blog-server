package com.frankfang.service;

import com.fasterxml.jackson.core.SerializableString;
import com.frankfang.bean.Dynamic;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface CountService {

    List<Dynamic> getDynamicList(Serializable userId, String type, long partNum);
}

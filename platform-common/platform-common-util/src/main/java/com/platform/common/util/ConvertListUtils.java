package com.platform.common.util;

import java.util.ArrayList;
import java.util.List;

public class ConvertListUtils {

    public static <T> List<T> convertList(int pageNum, int pageSize, List<T> list) {
        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = pageNum * pageSize;
        if (fromIndex > list.size()) return new ArrayList<>();
        if (toIndex > list.size()) toIndex = list.size();
        return list.subList(fromIndex, toIndex);
    }

}

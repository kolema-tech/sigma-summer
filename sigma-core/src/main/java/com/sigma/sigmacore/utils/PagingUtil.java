package com.sigma.sigmacore.utils;

import com.sigma.sigmacore.web.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018-06-
 * desc:
 **/
public class PagingUtil {
    public static <T> List<T> paginate(PageRequest pageRequest, List<T> list) {
        return list.stream()
                .skip((pageRequest.getPageIndex() - 1) * pageRequest.getPageSize())
                .limit(pageRequest.getPageSize())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

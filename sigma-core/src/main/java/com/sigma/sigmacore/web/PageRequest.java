package com.sigma.sigmacore.web;

import lombok.*;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018-06-
 * desc:
 **/
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {

    /**
     * from 1 to n
     */
    private int pageIndex = 1;

    private int pageSize = 20;

    public static PageRequest createPageRequest(int pageIndex, int pageSize) {
        return new PageRequest(pageIndex, pageSize);
    }
}

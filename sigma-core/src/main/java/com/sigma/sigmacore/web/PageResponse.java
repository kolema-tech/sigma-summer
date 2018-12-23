package com.sigma.sigmacore.web;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018-06-
 * desc:
 **/
@Getter
@Setter
public class PageResponse extends PageRequest {

    private int total;

    private int totalPages;
}

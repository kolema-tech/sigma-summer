/*
 *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>

 *  AG-Enterprise 企业版源码
 *  郑重声明:
 *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  老A将追究授予人和传播人的法律责任!

 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.sigma.sigmadatamybatiscore.entity;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 */
public class SigmaPage extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    /**
     * 当前页码
     */
    private int page = 1;
    /**
     * 每页条数
     */
    private int limit = 10;

    public SigmaPage(Map<String, Object> params) {
        this.putAll(params);
        //分页参数
        if (params.get("page") != null) {
            this.page = Integer.parseInt(params.get("page").toString());
        }
        if (params.get("limit") != null) {
            this.limit = Integer.parseInt(params.get("limit").toString());
        }
        this.remove("page");
        this.remove("limit");
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}

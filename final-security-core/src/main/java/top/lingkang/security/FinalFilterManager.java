package top.lingkang.security;

import top.lingkang.filter.FinalFilter;
import top.lingkang.filter.impl.FinalAuthFilter;

import java.util.List;

/**
 * @author lingkang
 * @date 2021/8/21 18:06
 * @description
 */
public class FinalFilterManager {

    private List<FinalFilter> filters;

    public List<FinalFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<FinalFilter> filters) {
        this.filters = filters;
    }
}

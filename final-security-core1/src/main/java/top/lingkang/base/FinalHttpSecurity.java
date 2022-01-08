package top.lingkang.base;

import java.util.HashMap;
import java.util.List;

/**
 * @author lingkang
 * @date 2022/1/8
 */
public interface FinalHttpSecurity {
    List<String> getExcludePath();

    void setExcludePath(List<String> excludePath);

    HashMap<String, FinalAuth> getCheckAuths();

    void setCheckAuths(HashMap<String, FinalAuth> checkAuths);
}

package top.lingkang.security;

import java.io.Serializable;
import java.util.List;

/**
 * @author lingkang
 * @date 2021/8/13 15:17
 * @description
 */
public interface FinalPermission {
    void addPermission(String permission);

    void addPermission(List<String> permission);

    void deletePermission(String permission);

    List<String> getPermission();

}

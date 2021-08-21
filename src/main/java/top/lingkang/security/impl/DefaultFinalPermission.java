package top.lingkang.security.impl;

import top.lingkang.security.FinalPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * @author lingkang
 * @date 2021/8/13 15:22
 * @description
 */
public class DefaultFinalPermission implements FinalPermission {
    private List<String> list = new ArrayList<>();

    @Override
    public void addPermission(String permission) {
        list.add(permission);
    }

    @Override
    public void addPermission(List<String> permission) {
        list.addAll(permission);
        list = new ArrayList<String>(new TreeSet<String>(list));
    }

    @Override
    public void deletePermission(String permission) {
        list.remove(permission);
    }

    @Override
    public List<String> getPermission() {
        return list;
    }
}

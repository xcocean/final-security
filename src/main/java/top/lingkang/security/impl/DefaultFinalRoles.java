package top.lingkang.security.impl;

import top.lingkang.security.FinalRoles;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * @author lingkang
 * @date 2021/8/13 15:19
 * @description
 */
public class DefaultFinalRoles implements FinalRoles {
    private List<String> list = new ArrayList<String>();

    public void addRole(String role) {
        list.add(role);
    }

    public void addRoles(List<String> roles) {
        list.addAll(roles);
        list = new ArrayList<String>(new TreeSet<String>(list));
    }

    public void deleteRole(String role) {
        list.remove(role);
    }

    public List<String> getRoles() {
        return list;
    }
}

package top.lingkang.service.impl;

import org.springframework.stereotype.Service;
import top.lingkang.annotation.FinalCheck;
import top.lingkang.service.UserService;

/**
 * @author lingkang
 * date 2022/1/16
 */
@FinalCheck(anyRole = "user")
@Service
public class UserServiceImpl implements UserService {

    @Override
    public String getNickname() {
        return "lingkang";
    }

    @FinalCheck(anyRole = "admin")
    @Override
    public String getUsername() {
        return "asd";
    }
}

package com.stitch.mynovel.service.Impl;

import cn.hutool.core.util.StrUtil;
import com.stitch.mynovel.core.common.constant.ErrorCodeEnum;
import com.stitch.mynovel.core.common.exception.BusinessException;
import com.stitch.mynovel.core.common.resp.RestResp;
import com.stitch.mynovel.core.constant.SystemConfigConsts;
import com.stitch.mynovel.domain.dto.require.UserRegisterReqDto;
import com.stitch.mynovel.domain.po.UserInfo;
import com.stitch.mynovel.domain.vo.UserRegisterVO;
import com.stitch.mynovel.manage.redis.VerifyCodeManage;
import com.stitch.mynovel.mapper.UserInfoMapper;
import com.stitch.mynovel.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author Stitch
 * @since 2026-11-19
 */
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    private final VerifyCodeManage verifyCodeManage;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtUtils jwtUtils;

    @Override
    public RestResp<UserRegisterVO> register(UserRegisterReqDto dto) {
        // 1.校验验证码
        if (StrUtil.hasBlank(dto.getVelCode(), dto.getSessionId(), dto.getUsername(), dto.getPassword())) {
            throw new BusinessException(ErrorCodeEnum.USER_REQUEST_PARAM_ERROR);
        }
        if(verifyCodeManage.checkVerifyCode(dto.getSessionId(), dto.getVelCode())) {
            throw new BusinessException(ErrorCodeEnum.USER_VERIFY_CODE_ERROR);
        }
        // 2.判断用户是否已存在
        UserInfo one = lambdaQuery().eq(UserInfo::getUsername, dto.getUsername()).one();
        if(one != null) {
            throw new BusinessException(ErrorCodeEnum.USER_NAME_EXIST);
        }
        // 3.保存用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setPassword(passwordEncoder.encode(dto.getPassword()));
        userInfo.setUsername(dto.getUsername());
        userInfo.setNickName(dto.getUsername());
        userInfo.setCreateTime(LocalDateTime.now());
        userInfo.setUpdateTime(LocalDateTime.now());
        // 4.返回jwt
        return RestResp.ok(
                UserRegisterVO.builder()
                        .userId(userInfo.getId())
                        .token(jwtUtils.generateToken(userInfo.getId(), SystemConfigConsts.NOVEL_FRONT_KEY))
                        .build()
        );
    }
}

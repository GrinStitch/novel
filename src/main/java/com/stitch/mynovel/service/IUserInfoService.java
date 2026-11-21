package com.stitch.mynovel.service;

import com.stitch.mynovel.core.common.resp.RestResp;
import com.stitch.mynovel.domain.dto.require.UserRegisterReqDto;
import com.stitch.mynovel.domain.po.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stitch.mynovel.domain.vo.UserRegisterVO;
import jakarta.validation.Valid;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author Stitch
 * @since 2026-11-19
 */
public interface IUserInfoService extends IService<UserInfo> {

    RestResp<UserRegisterVO> register(@Valid UserRegisterReqDto dto);
}

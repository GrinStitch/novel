package com.stitch.mynovel.controller.front;


import com.stitch.mynovel.core.common.resp.RestResp;
import com.stitch.mynovel.domain.dto.require.UserRegisterReqDto;
import com.stitch.mynovel.domain.vo.UserRegisterVO;
import com.stitch.mynovel.service.IUserInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author Stitch
 * @since 2026-11-19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/front/user")
@Tag(name = "用户相关接口")
public class UserController {

    private final IUserInfoService userInfoService;

    @PostMapping("/register")
    public RestResp<UserRegisterVO> register(@Valid  @RequestBody UserRegisterReqDto dto) {
        return userInfoService.register(dto);
    }
}

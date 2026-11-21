package com.stitch.mynovel.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterVO {
    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户令牌")
    private String token;
}

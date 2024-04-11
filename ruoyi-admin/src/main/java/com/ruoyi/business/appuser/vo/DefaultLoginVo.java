package com.ruoyi.business.appuser.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 账号密码登录参数
 *
 * @author Tellsea
 * @date 2022/9/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DefaultLoginVo {

    private String userName;

    private String password;
}

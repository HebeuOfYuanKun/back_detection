package com.ruoyi.common.constant;

/**
 * @author Tellsea
 * @date 2021/06/28
 */
public enum RoleConstants {

	ADMIN(1L, "admin"),
	USER(2L, "user");

	private final Long roleId;
	private final String roleKey;

	public Long getRoleId() {
		return roleId;
	}

	public String getRoleKey() {
		return roleKey;
	}

	RoleConstants(Long roleId, String roleKey) {
		this.roleId = roleId;
		this.roleKey = roleKey;
	}
}

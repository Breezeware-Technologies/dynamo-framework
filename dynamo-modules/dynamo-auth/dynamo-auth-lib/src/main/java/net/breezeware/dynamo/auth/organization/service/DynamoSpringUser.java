package net.breezeware.dynamo.auth.organization.service;

import java.util.Collection;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class DynamoSpringUser extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DynamoSpringUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		// TODO Auto-generated constructor stub
	}

	public long organizationId = 0;

	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}
}
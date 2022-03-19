package com.example.MongoJwt.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserdetails implements UserDetails {
	private User user;

	public CustomUserdetails(User user) {
		super();
		this.user = user;
	}

	public CustomUserdetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
//        System.out.println("ROLE_" + user.getRoles().toUpperCase());
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(
				"ROLE_" + user.getRoles().toUpperCase());

		return List.of(simpleGrantedAuthority);
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}

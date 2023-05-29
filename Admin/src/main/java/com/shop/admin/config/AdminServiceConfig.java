package com.shop.admin.config;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shop.library.model.Admin;
import com.shop.library.model.Role;
import com.shop.library.repository.AdminRepository;

public class AdminServiceConfig implements UserDetailsService {

	@Autowired
	private AdminRepository adminRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Admin admin = adminRepository.findByUsername(username);
		if(admin==null) {
			throw new UsernameNotFoundException("không tìm thấy username");
		}
		return new User(
				admin.getUsername(),
				admin.getPassword(),
				admin.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
	}

}










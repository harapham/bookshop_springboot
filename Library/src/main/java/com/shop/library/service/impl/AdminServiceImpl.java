package com.shop.library.service.impl;



import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shop.library.dto.AdminDto;
import com.shop.library.model.Admin;
import com.shop.library.repository.AdminRepository;
import com.shop.library.repository.RoleRepository;
import com.shop.library.service.AdminService;
@Service
public class AdminServiceImpl implements AdminService{

//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Admin findByUsername(String username) {
		// TODO Auto-generated method stub
		return adminRepository.findByUsername(username);
	}

	@Override
	public Admin save(AdminDto adminDto) {
		// TODO Auto-generated method stub
		Admin admin = new Admin();
		admin.setFirstName(adminDto.getFirstName());
		admin.setLastName(adminDto.getLastName());
		admin.setUsername(adminDto.getUsername());
		admin.setPassword(adminDto.getPassword());
		admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
		return adminRepository.save(admin);
	}
	
}

package com.argonautb04.sirio.security;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.repository.EmployeeDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeDB employeeDB;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Employee employee = employeeDB.findByUsernameAndStatus(username, Employee.Status.AKTIF).get();

        final Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(employee.getRole().getNamaRole()));

        return new User(employee.getUsername(), employee.getPassword(), grantedAuthorities);
    }
}

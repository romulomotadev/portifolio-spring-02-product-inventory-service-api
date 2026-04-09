package com.rpdevelopment.product_inventory_service.service;

import com.rpdevelopment.product_inventory_service.dto.projection.UserDetailsProjection;
import com.rpdevelopment.product_inventory_service.entity.Role;
import com.rpdevelopment.product_inventory_service.entity.User;
import com.rpdevelopment.product_inventory_service.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;


    // ========== CONSTRUTORES DAS INSTANCIAS =============

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // =================== METODOS =====================

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<UserDetailsProjection> result = userRepository.searchUserAndRolesByEmail(username);
        if(result.isEmpty()){ throw new UsernameNotFoundException(username);}

        User user = new User();
        user.setEmail(username);
        user.setPassword(result.getFirst().getPassword());

        for(UserDetailsProjection projection : result){
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }

        return user;
    }
}

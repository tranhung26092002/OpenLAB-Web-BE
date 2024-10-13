package edu.ptit.openlab.service.Impl;

import edu.ptit.openlab.DTO.UserDTO;
import edu.ptit.openlab.entity.User;
import edu.ptit.openlab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepositoty;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userEntity = userRepositoty.findByEmail(email);
        if(userEntity == null){
            throw new UsernameNotFoundException("Không tim thấy người dùng với email: " + email);
        }
        return toUserDTO(userEntity);
    }

    private UserDTO toUserDTO(User userEntity){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(userEntity.getId());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setPhoneNumber(userEntity.getPhoneNumber());
        userDTO.setVaiTro(userEntity.getVaiTro());
        userDTO.setCourses(userEntity.getCourses());
        userDTO.setProducts(userEntity.getProducts());
        userDTO.setAddress(userEntity.getAddress());
        
        return userDTO;
    }
}

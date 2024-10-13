package edu.ptit.openlab.DTO;

import edu.ptit.openlab.entity.Product;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import edu.ptit.openlab.entity.Course;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
@Getter
@Setter
public class UserDTO implements UserDetails {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String address;

    private String thumbnail;
    private String phoneNumber;

    private Date dob;
    private int vaiTro;
    private List<Course> courses = new ArrayList<>();
    private List<Product> products = new ArrayList<>();

    public UserDTO() {

    }

    public enum Role{
        ROLE_USER, ROLE_ADMIN
    }

    private Role role;

    public UserDTO(Long id, String address, String email, String username, String password, String phoneNumber, int vaiTro) {
        this.id = id;
        this.address = address;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        setVaiTro(vaiTro);
    }

    public void setVaiTro(int vaiTro){
        this.vaiTro = vaiTro;
        switch (vaiTro) {
            case 1:
                this.role = Role.ROLE_USER;
                break;
            case 2:
                this.role = Role.ROLE_ADMIN;
                break;
            default:
                throw new IllegalArgumentException("Invalid vaTro value");
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }
}

package vn.com.openlab.mapper;

import org.mapstruct.Mapper;
import vn.com.openlab.dto.UserDTO;
import vn.com.openlab.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDTO userDTO);
}
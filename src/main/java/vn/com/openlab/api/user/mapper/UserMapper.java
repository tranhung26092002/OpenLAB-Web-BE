package vn.com.openlab.api.user.mapper;

import org.mapstruct.Mapper;
import vn.com.openlab.api.user.dto.UserDTO;
import vn.com.openlab.api.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDTO userDTO);
}
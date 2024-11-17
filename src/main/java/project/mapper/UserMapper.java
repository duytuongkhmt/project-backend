package project.mapper;

import org.springframework.beans.BeanUtils;
import project.model.Account;
import project.resource.UserResource;

public class UserMapper {
    private UserMapper() {

    }

    public static UserResource map(Account user) {
        UserResource usersResource = new UserResource();
        BeanUtils.copyProperties(user, usersResource);
        usersResource.setRole(user.getRole().name());
        return usersResource;
    }
}

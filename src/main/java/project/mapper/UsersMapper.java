package project.mapper;

import org.springframework.beans.BeanUtils;
import project.model.Account;
import project.resource.UsersResource;

public class UsersMapper {
    private UsersMapper(){

    }
    public static UsersResource map(Account user) {
        UsersResource usersResource=new UsersResource();
        BeanUtils.copyProperties(user,usersResource);
        return usersResource;
    }
}

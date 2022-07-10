package pers.czj.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;
import pers.czj.entity.User;

import javax.validation.constraints.Email;

/**
 * 创建在 2020/7/10 16:28
 */
@Data
@ApiModel
public class RegisterUserInputDto {

    @Length(min = 6, max = 12)
    private String username;

    @Length(min = 6, max = 12)
    private String account;

    @Length(min = 6, max = 12)
    private String password;

    @Email
    private String email;

    public User convert() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}

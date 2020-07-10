package pers.czj.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 创建在 2020/7/10 22:06
 */
@Data
@ApiModel
public class LoginUserInputDto {

    @Length(min = 6,max = 12)
    private String account;

    @Length(min = 6,max = 12)
    private String password;
}

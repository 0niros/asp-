package cn.com.oniros.security.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * cn.com.oniros.security.entity.dto.LoginDto
 *
 * @author Li Xiaoxu
 * 2024/4/14 16:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}

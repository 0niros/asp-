package cn.com.oniros.security.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * cn.com.oniros.security.entity.dto.GrantedAuthority
 *
 * @author Li Xiaoxu
 * 2024/4/14 16:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrantedAuthorityDto implements GrantedAuthority {

    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    public static List<GrantedAuthorityDto> getList(List<String> authorities) {
        return authorities.stream().map(GrantedAuthorityDto::new).toList();
    }
}

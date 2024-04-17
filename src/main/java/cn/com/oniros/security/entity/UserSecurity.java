package cn.com.oniros.security.entity;

import cn.com.oniros.security.entity.dto.GrantedAuthorityDto;
import cn.com.oniros.user.manager.api.dto.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * cn.com.oniros.security.entity.UserSecurity
 *
 * @author Li Xiaoxu
 * 2024/4/14 16:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSecurity implements UserDetails {

    private UserInfoDto user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> authorities = user.getAuthorities();

        return GrantedAuthorityDto.getList(authorities);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

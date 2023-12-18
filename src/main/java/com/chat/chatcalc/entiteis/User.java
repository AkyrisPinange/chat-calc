package com.chat.chatcalc.entiteis;

import com.chat.chatcalc.enums.Role;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "user")
public class User implements UserDetails {

    private String id;
    private String name;
    private String email;
    private String password;
    private String urlImg;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    private Collection<? extends GrantedAuthority> authorities;
    private  boolean accountNonExpired;
    private  boolean accountNonLocked;
    private  boolean credentialsNonExpired;
    private  boolean enabled;
    private  String username;

    Role role;


    private transient List<ChatReference> chats = new ArrayList<>();
    @Override
    public String getUsername() {
        return email;
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

package com.tomateritmo.arqemergente.iam.infrastructure.authorization.sfs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.tomateritmo.arqemergente.iam.domain.model.aggregates.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is responsible for providing the user details to the Spring Security framework.
 * It implements the UserDetails interface.
 */
@Getter
@EqualsAndHashCode
public class UserDetailsImpl implements UserDetails {

  private final String username;
  @JsonIgnore
  private final String password;
  private final boolean accountNonExpired;
  private final boolean accountNonLocked;
  private final boolean credentialsNonExpired;
  private final boolean enabled;

  public UserDetailsImpl(String username, String password) {
    this.username = username;
    this.password = password;
    this.accountNonExpired = true;
    this.accountNonLocked = true;
    this.credentialsNonExpired = true;
    this.enabled = true;
  }

  /**
   * This method is responsible for building the UserDetailsImpl object from the User object.
   * @param user The user object.
   * @return The UserDetailsImpl object.
   */
  public static UserDetailsImpl build(User user) {
    return new UserDetailsImpl(user.getUsername(), user.getPassword());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }
}

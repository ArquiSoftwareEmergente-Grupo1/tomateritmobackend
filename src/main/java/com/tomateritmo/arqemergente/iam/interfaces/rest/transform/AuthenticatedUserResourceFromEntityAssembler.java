package com.tomateritmo.arqemergente.iam.interfaces.rest.transform;

import com.tomateritmo.arqemergente.iam.domain.model.aggregates.User;
import com.tomateritmo.arqemergente.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {

  public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
    return new AuthenticatedUserResource(user.getId(), user.getUsername(), token);
  }
}

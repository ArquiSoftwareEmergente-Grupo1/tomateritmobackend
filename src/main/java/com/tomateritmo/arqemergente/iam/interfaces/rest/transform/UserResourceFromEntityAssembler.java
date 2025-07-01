package com.tomateritmo.arqemergente.iam.interfaces.rest.transform;

import com.tomateritmo.arqemergente.iam.domain.model.aggregates.User;
import com.tomateritmo.arqemergente.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {

  public static UserResource toResourceFromEntity(User user) {
    return new UserResource(user.getId(), user.getUsername());
  }
}

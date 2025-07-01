package com.tomateritmo.arqemergente.iam.interfaces.rest.transform;

import com.tomateritmo.arqemergente.iam.domain.model.commands.SignInCommand;
import com.tomateritmo.arqemergente.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {

  public static SignInCommand toCommandFromResource(SignInResource signInResource) {
    return new SignInCommand(signInResource.username(), signInResource.password());
  }
}

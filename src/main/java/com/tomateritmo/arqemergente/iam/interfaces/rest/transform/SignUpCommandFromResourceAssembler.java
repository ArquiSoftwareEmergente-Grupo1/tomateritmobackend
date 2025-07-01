package com.tomateritmo.arqemergente.iam.interfaces.rest.transform;

import com.tomateritmo.arqemergente.iam.domain.model.commands.SignUpCommand;
import com.tomateritmo.arqemergente.iam.interfaces.rest.resources.SignUpResource;

import java.util.ArrayList;

public class SignUpCommandFromResourceAssembler {

  public static SignUpCommand toCommandFromResource(SignUpResource resource) {
    return new SignUpCommand(resource.username(), resource.password());
  }
}

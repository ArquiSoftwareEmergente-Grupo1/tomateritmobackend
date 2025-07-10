package com.tomateritmo.arqemergente.iam.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tomateritmo.arqemergente.iam.domain.services.UserCommandService;
import com.tomateritmo.arqemergente.iam.interfaces.rest.resources.AuthenticatedUserResource;
import com.tomateritmo.arqemergente.iam.interfaces.rest.resources.SignInResource;
import com.tomateritmo.arqemergente.iam.interfaces.rest.resources.SignUpResource;
import com.tomateritmo.arqemergente.iam.interfaces.rest.resources.UserResource;
import com.tomateritmo.arqemergente.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.tomateritmo.arqemergente.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.tomateritmo.arqemergente.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import com.tomateritmo.arqemergente.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;

/**
 * AuthenticationController
 * <p>
 *     This controller is responsible for handling authentication requests.
 *     It exposes two endpoints:
 *     <ul>
 *         <li>POST /api/v1/auth/sign-in</li>
 *         <li>POST /api/v1/auth/sign-up</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {

  private final UserCommandService userCommandService;

  public AuthenticationController(UserCommandService userCommandService) {
    this.userCommandService = userCommandService;
  }

  /**
   * Handles the sign-in request.
   * @param signInResource the sign-in request body.
   * @return the authenticated user resource.
   */
  @PostMapping("/sign-in")
  public ResponseEntity<AuthenticatedUserResource> signIn(
      @RequestBody SignInResource signInResource) {

    var signInCommand = SignInCommandFromResourceAssembler
        .toCommandFromResource(signInResource);
    var authenticatedUser = userCommandService.handle(signInCommand);
    if (authenticatedUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler
        .toResourceFromEntity(
            authenticatedUser.get().getLeft(), authenticatedUser.get().getRight());
    return ResponseEntity.ok(authenticatedUserResource);
  }

  /**
   * Handles the sign-up request.
   * @param signUpResource the sign-up request body.
   * @return the created user resource.
   */
  @PostMapping("/sign-up")
  public ResponseEntity<Object> signUp(@RequestBody SignUpResource signUpResource) {
    try {
      // Validar entrada
      if (signUpResource == null || signUpResource.username() == null || signUpResource.password() == null) {
        return ResponseEntity.badRequest().body("Username and password are required");
      }
      
      // Transformar y procesar comando
      var signUpCommand = SignUpCommandFromResourceAssembler
          .toCommandFromResource(signUpResource);
      var user = userCommandService.handle(signUpCommand);
      
      if (user.isEmpty()) {
        return ResponseEntity.badRequest().build();
      }
      
      // Transformar respuesta
      var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
      return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    } catch (RuntimeException e) {
      // Log del error para diagnóstico
      System.err.println("Error during sign-up: " + e.getMessage());
      e.printStackTrace();
      
      // Devolver mensaje de error adecuado
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      // Log del error para diagnóstico
      System.err.println("Unexpected error during sign-up: " + e.getMessage());
      e.printStackTrace();
      
      // Devolver mensaje de error genérico para errores inesperados
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
  }
}

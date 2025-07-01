package com.tomateritmo.arqemergente.iam.domain.services;

import com.tomateritmo.arqemergente.iam.domain.model.aggregates.User;
import com.tomateritmo.arqemergente.iam.domain.model.queries.GetAllUsersQuery;
import com.tomateritmo.arqemergente.iam.domain.model.queries.GetUserByIdQuery;
import com.tomateritmo.arqemergente.iam.domain.model.queries.GetUserByUsernameQuery;
import com.tomateritmo.arqemergente.iam.domain.model.queries.GetUserIdByUsernameQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
  List<User> handle(GetAllUsersQuery query);
  Optional<User> handle(GetUserByIdQuery query);
  Optional<User> handle(GetUserByUsernameQuery query);
  Optional<User> handle(GetUserIdByUsernameQuery query);
}

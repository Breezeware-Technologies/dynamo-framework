package net.breezeware.dynamo.auth.oauth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.StringPath;

import net.breezeware.dynamo.auth.oauth.entity.QUserOAuthToken;
import net.breezeware.dynamo.auth.oauth.entity.UserOAuthToken;

@Repository
public interface UserOAuthTokenRepository extends JpaRepository<UserOAuthToken, Long>,
        QuerydslPredicateExecutor<UserOAuthToken>, QuerydslBinderCustomizer<QUserOAuthToken> {

    UserOAuthToken findByUserIdAtSource(String userIdAtSource);

    default public void customize(QuerydslBindings bindings, QUserOAuthToken token) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}

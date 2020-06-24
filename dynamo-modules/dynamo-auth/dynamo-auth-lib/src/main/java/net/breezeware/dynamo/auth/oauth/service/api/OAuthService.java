package net.breezeware.dynamo.auth.oauth.service.api;

import java.util.List;
import java.util.Optional;

import com.querydsl.core.types.Predicate;

import net.breezeware.dynamo.auth.oauth.dto.OAuthConnectionProperties;
import net.breezeware.dynamo.auth.oauth.entity.UserOAuthToken;

public interface OAuthService {

    UserOAuthToken createToken(UserOAuthToken userOAuthToken);

    List<UserOAuthToken> retrieveToken(Predicate predicate);

    /**
     * Renew the User token if it is about to expire in the next 5 minutes or if it
     * has already expired.
     * 
     * @param userOAuthToken
     * @param oauthConnectionProperties
     * @return
     */
    Optional<UserOAuthToken> renewToken(UserOAuthToken userOAuthToken,
            OAuthConnectionProperties oauthConnectionProperties);

    boolean deleteToken(long tokenId);

    boolean deleteToken(UserOAuthToken token);

    UserOAuthToken retrieveTokenByUserIdAtSource(String userIdAtSource);
}
package net.breezeware.dynamo.auth.oauth.service.api;

import java.util.Optional;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import net.breezeware.dynamo.auth.oauth.entity.UserOAuthToken;

/**
 * Interface to build a UserOAuthToken entity. This interface will have
 * implementations dedicated to different OAuth Authorization Services.
 */
public interface UserOAuthTokenBuilder {

    /**
     * Build (but not store/persist) a UserOAuthToken from Spring Security's
     * OAuth2AuthenticationToken entity.
     * @param oAuth2Token the Spring Security's OAuth2AuthenticationToken entity
     * @return a non-empty UserOAuthToken entity if token was built successfully.
     *         Else, an empty result.
     */
    Optional<UserOAuthToken> buildToken(OAuth2AuthenticationToken oAuth2Token);
}
package net.breezeware.dynamo.auth.oauth.service.api;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import net.breezeware.dynamo.auth.oauth.entity.UserOAuthToken;

/**
 * Services to build a UserOAuthToken based on the OAuth service and data
 * structure.
 */
public interface UserOAuthTokenBuilder {

    /**
     * Build (but not store/persist) a UserOAuthToken from Spring Security's
     * OAuth2AuthenticationToken entity.
     * @param oAuth2Token the Spring Security's OAuth2AuthenticationToken entity
     * @return a UserOAuthToken entity created from the Spring token.
     */
    UserOAuthToken buildToken(OAuth2AuthenticationToken oAuth2Token);
}
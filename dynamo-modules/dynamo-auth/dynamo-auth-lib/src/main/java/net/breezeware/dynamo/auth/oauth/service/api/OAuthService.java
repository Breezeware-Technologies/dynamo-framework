package net.breezeware.dynamo.auth.oauth.service.api;

import java.util.List;
import java.util.Optional;

import com.querydsl.core.types.Predicate;

import net.breezeware.dynamo.auth.oauth.dto.OAuthConnectionProperties;
import net.breezeware.dynamo.auth.oauth.entity.UserOAuthToken;

public interface OAuthService {

    /**
     * Persist the UserOAuthToken in the database.
     * @param userOAuthToken the token to be saved
     * @return Token that was persisted in the database
     */
    UserOAuthToken createToken(UserOAuthToken userOAuthToken);

    /**
     * Retrieve a list of UserOAuthTokens that match the criteria.
     * @param predicate the interface for Boolean typed expressions.
     * @return list of UserOAuthToken that match the filter criteria
     */
    List<UserOAuthToken> retrieveToken(Predicate predicate);

    /**
     * Renew the User token if it is about to expire in the next 5 minutes or if it
     * has already expired.
     * @param userOAuthToken            the authentication token associated with a
     *                                  Dynamo User
     * @param oauthConnectionProperties the properties to hold connection details to
     *                                  an external service
     * @return an non-null UserOAuthToken if present else an empty value
     */
    Optional<UserOAuthToken> renewToken(UserOAuthToken userOAuthToken,
            OAuthConnectionProperties oauthConnectionProperties);

    /**
     * Delete the UserOAuthToken identified by a token ID.
     * @param tokenId the token Id to uniquely identify the token
     * @return true if the token is deleted successfully, else false
     */
    boolean deleteToken(long tokenId);

    /**
     * Delete the UserOAuthToken passed to this method.
     * @param token the token to be deleted
     * @return true if the token is deleted successfully, else false
     */
    boolean deleteToken(UserOAuthToken token);

    /**
     * Retrieve the UserOAuthToken identified by the userIdAtSource property.
     * @param userIdAtSource the property within the UserOAuthToken entity
     * @return a non-null token if the UserOAuthToken is present, else return null
     */
    UserOAuthToken retrieveTokenByUserIdAtSource(String userIdAtSource);
}
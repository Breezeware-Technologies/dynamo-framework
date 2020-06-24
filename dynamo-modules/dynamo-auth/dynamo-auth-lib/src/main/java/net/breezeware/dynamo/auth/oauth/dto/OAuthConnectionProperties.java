package net.breezeware.dynamo.auth.oauth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity to hold connection properties for OAuth connection to a single
 * external service like Zoom teleconferencing.
 *
 */

@ToString
public class OAuthConnectionProperties {

    @Getter
    @Setter
    private String serviceName;

    @Getter
    @Setter
    private String clientId;

    @Getter
    @Setter
    private String clientSecret;

    @Getter
    @Setter
    private String accessTokenUrl;

    @Getter
    @Setter
    private String refreshTokenUrl;
}
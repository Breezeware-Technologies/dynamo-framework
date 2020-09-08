package net.breezeware.dynamo.auth.oauth.service.impl;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.querydsl.core.types.Predicate;

import net.breezeware.dynamo.auth.oauth.dao.UserOAuthTokenRepository;
import net.breezeware.dynamo.auth.oauth.dto.OAuthConnectionProperties;
import net.breezeware.dynamo.auth.oauth.entity.UserOAuthToken;
import net.breezeware.dynamo.auth.oauth.service.api.OAuthService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OAuthServiceImpl implements OAuthService {

    @Autowired
    UserOAuthTokenRepository userOAuthTokenRepository;

    @Autowired(required = false)
    RestTemplate restTemplate;

    /**
     * {@inheritDoc}
     */
    @Transactional
    public UserOAuthToken createToken(UserOAuthToken userOAuthToken) {
        log.info("Entering createToken(). UserOAuthToken = {}", userOAuthToken);

        userOAuthToken = userOAuthTokenRepository.save(userOAuthToken);

        log.info("Leaving createToken(). UserOAuthToken = {}", userOAuthToken);
        return userOAuthToken;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public List<UserOAuthToken> retrieveToken(Predicate predicate) {
        log.info("Entering retrieveToken(). Predicate = {}", predicate);

        Sort listSort = Sort.by(Direction.DESC, "id");
        Iterable<UserOAuthToken> tokens = userOAuthTokenRepository.findAll(predicate, listSort);
        List<UserOAuthToken> tokensList = new ArrayList<UserOAuthToken>();
        tokens.forEach(tokensList::add);

        log.info("Leaving retrieveToken(). # of tokens retrieved = {}", tokensList.size());
        return tokensList;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public UserOAuthToken retrieveTokenByUserIdAtSource(String userIdAtSource) {
        log.info("Entering retrieveTokenByUserIdAtSource(), userIdAtSource = {}", userIdAtSource);
        UserOAuthToken token = userOAuthTokenRepository.findByUserIdAtSource(userIdAtSource);

        log.info("Leaving retrieveTokenByUserIdAtSource(), UserOAuthToken = {}", token);
        if (token == null) {
            return null;
        } else {
            return token;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public Optional<UserOAuthToken> renewToken(UserOAuthToken userOAuthToken,
            OAuthConnectionProperties oauthConnectionProperties) {
        log.info("Entering renewToken(). UserOAuthToken = {}", userOAuthToken);

        if (userOAuthToken.getExpiresAt().isAfter(Instant.now().plusSeconds(300))) {
            log.info("Access token is valid, returning it.");
            return Optional.ofNullable(userOAuthToken);
        } else {
            log.info("Access token is about to expire in 5 minutes or less or has "
                    + "already expired. Thereby renewing it.");

            HttpHeaders headers = new HttpHeaders();
            String encodedHeader = getBase64EncodedHeader(oauthConnectionProperties.getClientId(),
                    oauthConnectionProperties.getClientSecret());
            if (!encodedHeader.isEmpty()) {

                // call external service to get new token
                headers.add("Authorization", "Basic " + encodedHeader);
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                HttpEntity<String> request = new HttpEntity<String>(headers);

                // NOTE: adding query params (grant_type & refresh_token) is required for ZOOM
                // call integration.
                // This may differ for other services. Address this !!!
                String queryParams = "grant_type=refresh_token&refresh_token=" + userOAuthToken.getRefreshToken();
                String url = oauthConnectionProperties.getRefreshTokenUrl() + "?" + queryParams;

                ResponseEntity<String> response = getApiResponse(url, request, HttpMethod.POST);

                if (response != null) {
                    // parse the response and create a local token entity
                    UserOAuthToken newUserOAuthToken = parseJsonResponse(response, userOAuthToken);
                    log.info("New User OAuth Token = {}", newUserOAuthToken);

                    // delete existing one and persist the new token for future use
                    deleteToken(userOAuthToken);
                    newUserOAuthToken = createToken(newUserOAuthToken);

                    log.info("Leaving renewToken(). New User OAuth Token = {}", newUserOAuthToken);
                    return Optional.ofNullable(newUserOAuthToken);
                } else {
                    log.info("Leaving renewToken(). Reponse to renew token returned null. "
                            + "Therefore returning null.");
                    return Optional.ofNullable(null);
                }
            } else {
                log.info("Leaving renewToken(). Returning null as header is empty.");
                return Optional.ofNullable(null);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public boolean deleteToken(long tokenId) {
        log.info("Entering deleteToken(). Token ID = {}", tokenId);

        userOAuthTokenRepository.deleteById(tokenId);

        log.info("Leaving deleteToken()");
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public boolean deleteToken(UserOAuthToken userOAuthToken) {
        log.info("Entering deleteToken(). Token = {}", userOAuthToken);

        userOAuthTokenRepository.delete(userOAuthToken);

        log.info("Leaving deleteToken()");
        return false;
    }

    private String getBase64EncodedHeader(String clientID, String clientSecret) {
        log.info("Entering getBase64EncodedHeader()");

        StringBuilder sb = new StringBuilder();
        sb.append(clientID).append(":").append(clientSecret);
        try {
            return Base64.getEncoder().encodeToString(sb.toString().getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("Error while generating Fitbit Base64 encoded url {}" + e.getMessage());
        }

        log.info("Leaving getBase64EncodedHeader()");
        return null;
    }

    private ResponseEntity<String> getApiResponse(String url, HttpEntity<String> request, HttpMethod httpMethod) {
        log.info("Entering getApiResponse(). URL = {}", url);

        if (url != null && request != null && !request.getHeaders().isEmpty()) {
            try {
                ResponseEntity<String> response = restTemplate.exchange(url.toString(), httpMethod, request,
                        String.class);
                if (!response.getStatusCode().equals(HttpStatus.OK)) {
                    log.error("Error response header = {}", response.getStatusCode());
                    log.error("Error response body = {}", response.getBody());
                    return null;
                } else {
                    return response;
                }
            } catch (HttpClientErrorException ex) {
                log.error("Exception occured in receiving ResponseEntity: {}", ex.getMessage());
            }
        }

        log.info("Leaving getApiResponse().");
        return null;
    }

    private UserOAuthToken parseJsonResponse(ResponseEntity<String> response, UserOAuthToken token) {
        log.info("Entering parseJsonResponse().");

        UserOAuthToken newToken = new UserOAuthToken();

        if (response != null) {
            try {
                JSONObject json = new JSONObject(response.getBody());
                newToken.setAccessToken(json.get("access_token").toString());
                newToken.setApplication(token.getApplication());
                newToken.setExpiresAt(Instant.now().plusSeconds(json.getInt("expires_in")));
                newToken.setModifiedDate(Instant.now());
                newToken.setRefreshToken(json.get("refresh_token").toString());
                newToken.setTokenUser(token.getTokenUser());
                newToken.setUserIdAtSource(token.getUserIdAtSource());
            } catch (JSONException e) {
                log.error("There was some error while parsing the json response of renew token");
                log.error("Error = {}", e.getMessage());
            }
        } else {
            log.error("Response received to renew token is NULL");
            token = null;
        }

        log.info("Leaving parseJsonResponse().");
        return newToken;
    }

}
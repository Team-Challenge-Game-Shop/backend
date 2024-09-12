package com.gameshop.ecommerce.security.auth.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GoogleTokenInfo {

    @JsonProperty("iss")
    private String issuer;

    @JsonProperty("azp")
    private String authorizedParty;

    @JsonProperty("aud")
    private String audience;

    @JsonProperty("sub")
    private String subject;

    @JsonProperty("email")
    private String email;

    @JsonProperty("email_verified")
    private Boolean emailVerified;

    @JsonProperty("at_hash")
    private String accessTokenHash;

    @JsonProperty("name")
    private String name;

    @JsonProperty("picture")
    private String picture;

    @JsonProperty("given_name")
    private String givenName;

    @JsonProperty("family_name")
    private String familyName;

    @JsonProperty("locale")
    private String locale;

    @JsonProperty("iat")
    private Long issuedAt;

    @JsonProperty("exp")
    private Long expirationTime;

}

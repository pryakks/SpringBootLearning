package com.auth.server.config;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.PasswordLookup;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class AuthorizationServerConfiguration {

	private static final String ROLES_CLAIM = "roles";

	@Autowired
	private UserDetailsService userDetailsService;

	@Value("${keyFile}")
	private String keyFile;

	@Value("${password}")
	private String password;

	@Value("${alias}")
	private String alias;

	@Value("${providerUrl}")
	private String providerUrl;

	@Value("${clientId}")
	private String clientId;
	@Value("${clientSecret}")
	private String clientSecret;

	@Value("${redirectURL}")
	private String redirectURL;
	@Autowired
	private PasswordEncoder passwordEncoder;


	@Bean
	public RegisteredClientRepository registeredClientRepository() {
		RegisteredClient registredClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId(clientId)
				.clientSecret(passwordEncoder.encode(clientSecret))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.redirectUri(redirectURL)
				.scope("read").scope(OidcScopes.OPENID)
				.tokenSettings(tokenSettings())
				.build();
		return new InMemoryRegisteredClientRepository(registredClient);

	}

	@Bean
	public TokenSettings tokenSettings() {
		return TokenSettings.builder()
				.accessTokenTimeToLive(Duration.ofMinutes(30l)).build();

	}

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
		return http.userDetailsService(userDetailsService)
				.formLogin(Customizer.withDefaults()).build();

	}

	@Bean
	public ProviderSettings providerSettings() {
		return ProviderSettings.builder().issuer(providerUrl).build();

	}

	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);

	}

	@Bean
	public JWKSource<SecurityContext> jwkSource()
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		JWKSet jwkSet = buildJWKSet();
		return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);

	}

	private JWKSet buildJWKSet() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore keyStore = KeyStore.getInstance("pkcs12");
		try (InputStream fis = this.getClass().getClassLoader().getResourceAsStream(keyFile);) {
			keyStore.load(fis, alias.toCharArray());
			return JWKSet.load(keyStore, new PasswordLookup() {

				@Override
				public char[] lookupPassword(String name) {
					return password.toCharArray();
				}
			});
		}

	}

//	@Bean
//	public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
//		RSAKey rsaKey = generateRsa();
//		JWKSet jwkSet = new JWKSet(rsaKey);
//
//		return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
//	}

//	private static RSAKey generateRsa() throws NoSuchAlgorithmException {
//		KeyPair keyPair = generateRsaKey();
//		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//
//		return new RSAKey.Builder(publicKey)
//				.privateKey(privateKey)
//				.keyID(UUID.randomUUID().toString())
//				.build();
//	}
//
//	private static KeyPair generateRsaKey() throws NoSuchAlgorithmException {
//		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//		keyPairGenerator.initialize(2048);
//
//		return keyPairGenerator.generateKeyPair();
//	}

	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
		return context -> {
			if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
				Authentication principal = context.getPrincipal();
				Set<String> authorities = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
						.collect(Collectors.toSet());
				context.getClaims().claim(ROLES_CLAIM, authorities);
			}
		};

	}

}
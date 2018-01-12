package com.logicq.ngr.helper;

import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;

import com.logicq.ngr.controllers.LoadApplicationData;
import com.logicq.ngr.service.security.UserService;
import com.logicq.ngr.validator.Preconditions;
import com.logicq.ngr.vo.UserVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public final class TokenHandler {
	

	private static final String CLAIM_KEY_CREATED = "created";
	private final String secret;
	private final UserService userService;
	

	public TokenHandler(String secret, UserService userService) {
		this.secret = Base64.getEncoder().encodeToString(secret.getBytes());
		this.userService = Preconditions.checkNotNull(userService);
	}

	public UserVO parseUserFromToken(String token) {
		String username = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
		return userService.loadUserByUsername(username);
	}

	public String createTokenForUser(UserVO user) throws ParseException {
		Date now = new Date();
		return Jwts.builder().setId(UUID.randomUUID().toString()).setSubject(user.getUsername()).setIssuedAt(now)
				.setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) throws ParseException {
		UserVO user = (UserVO) userDetails;
		final String username = getUsernameFromToken(token);
		return (username.equals(user.getUsername()) && !isTokenExpired(token));
	}

	private Date generateExpirationDate() throws ParseException {
		long tokenExpiry = Long.valueOf(LoadApplicationData.siteOptionMap.get("expirytimeinseconds"));
		return new Date(System.currentTimeMillis() + tokenExpiry * 1000);
	}

	public Boolean isTokenExpired(String token) throws ParseException {
		final Date expiration = getExpirationDateFromToken(token);
		if(expiration.getTime() <= System.currentTimeMillis())
			return true;
		else
			return false;
	}

	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	public Date getCreatedDateFromToken(String token) {
		Date created;
		try {
			final Claims claims = getClaimsFromToken(token);
			created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
		} catch (Exception e) {
			created = null;
		}
		return created;
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimsFromToken(token).getExpiration();
	}

	
	public  void expireToken(String token) throws Exception {
		/*final Claims claims = getClaimsFromToken(token);
		claims.setExpiration(new Date(System.currentTimeMillis()));
		System.out.println("Expiry Date = "+new Date(System.currentTimeMillis()));*/
		getClaimsFromToken(token).setExpiration(new Date(System.currentTimeMillis()));
		//Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().setExpiration(new Date(System.currentTimeMillis()));
	}
	
	private Claims getClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
}

package com.example.demosopt.authenticaion

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.time.ZonedDateTime
import java.util.*


@Component
class JwtUtil(
        @Value("\${jwt.secret}") secretKey: String?,
        @Value("\${jwt.expiration-time}") accessTokenExpTime: Long
) {
    private val key: Key
    private val accessTokenExpTime: Long
    private val log = org.slf4j.LoggerFactory.getLogger(JwtUtil::class.java)

    init {
        val keyBytes: ByteArray = Decoders.BASE64.decode(secretKey)
        this.key = Keys.hmacShaKeyFor(keyBytes)
        this.accessTokenExpTime = accessTokenExpTime
    }

    /**
     * Access Token 생성
     * @param member
     * @return Access Token String
     */
    fun createAccessToken(): String {
        return createToken(accessTokenExpTime)
    }


    /**
     * JWT 생성
     * @param member
     * @param expireTime
     * @return JWT String
     */
    private fun createToken(expireTime: Long): String {
        val claims = Jwts.claims()
        claims["memberId"] = "test"
        claims["email"] = "test"
        claims["role"] = "test"

        val now = ZonedDateTime.now()
        val tokenValidity = now.plusSeconds(expireTime)


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact()
    }


    /**
     * Token에서 User ID 추출
     * @param token
     * @return User ID
     */
    fun getUserId(token: String?): Long {
        return 1L
    }


    /**
     * JWT 검증
     * @param token
     * @return IsValidate
     */
    fun validateToken(token: String?): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: SecurityException) {
            log.info("Invalid JWT Token", e)
        } catch (e: MalformedJwtException) {
            log.info("Invalid JWT Token", e)
        } catch (e: ExpiredJwtException) {
            log.info("Expired JWT Token", e)
        } catch (e: UnsupportedJwtException) {
            log.info("Unsupported JWT Token", e)
        } catch (e: IllegalArgumentException) {
            log.info("JWT claims string is empty.", e)
        }
        return false
    }


    /**
     * JWT Claims 추출
     * @param accessToken
     * @return JWT Claims
     */
    fun parseClaims(accessToken: String?): Claims {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody()
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }
}
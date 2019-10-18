package com.TigranCorporations.oauth2.am.Starter;

import com.TigranCorporations.oauth2.configuration.OauthParams;
import com.TigranCorporations.oauth2.controller.model.Payload;
import com.TigranCorporations.oauth2.core.ex.TokenException;
import com.TigranCorporations.oauth2.core.utility.OauthHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OauthHelperTest {
	@Autowired
	private OauthHelper oauthHelper;

	@Autowired
	private RedissonClient redissonClient;

	@Autowired
	private OauthParams oauthParams;

	@Before
	public void init() throws IOException{
		RMapCache<String,String> refreshTokenCache = redissonClient.getMapCache(OauthHelper.REFRESH_TOKEN_CACHE);
		Payload payload = oauthHelper.getPayload(oauthParams.getTestTokenId());
		refreshTokenCache.put(payload.getSub(),oauthParams.getTestRefreshToken());
	}

	@Test
	public void testRefreshToken() throws IOException, TokenException {
		Payload oldPayload = oauthHelper.getPayload(oauthParams.getTestTokenId());
		Payload payload = oauthHelper.refreshTokenId(oldPayload.getSub());

		Assert.assertNotEquals(payload.getTokenId(),oauthParams.getTestTokenId());

		Long currentTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
		Assert.assertTrue("Refreshed token is already expired",currentTime < payload.getExp());

		Assert.assertEquals(payload.getSub(),oldPayload.getSub());
	}

	@Test(expected = TokenException.class)
	public void testRefreshTokenException() throws IOException, TokenException {
		oauthHelper.refreshTokenId(RandomStringUtils.randomAlphanumeric(4));
	}

	@Test
	public void redissonTest() throws IOException {
		RMapCache<String,String> refreshTokenCache = redissonClient.getMapCache(OauthHelper.REFRESH_TOKEN_CACHE);
		String nonExistingRefreshToken = refreshTokenCache.get(RandomStringUtils.randomAlphabetic(3));
		Assert.assertNull(nonExistingRefreshToken);

		Payload payload = oauthHelper.getPayload(oauthParams.getTestTokenId());
		String existingRefreshToken = refreshTokenCache.get(payload.getSub());
		Assert.assertNotNull(existingRefreshToken);
		Assert.assertEquals(oauthParams.getTestRefreshToken(),existingRefreshToken);
	}

}

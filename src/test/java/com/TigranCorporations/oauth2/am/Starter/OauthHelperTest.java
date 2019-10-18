package com.TigranCorporations.oauth2.am.Starter;

import com.TigranCorporations.oauth2.controller.model.AccessToken;
import com.TigranCorporations.oauth2.core.domain.dto.UserDto;
import com.TigranCorporations.oauth2.core.ex.TokenException;
import com.TigranCorporations.oauth2.core.service.UserService;
import com.TigranCorporations.oauth2.core.utility.OauthParams;
import com.TigranCorporations.oauth2.core.utility.OauthHelper;
import org.apache.commons.lang3.RandomStringUtils;
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


@RunWith(SpringRunner.class)
@SpringBootTest
public class OauthHelperTest {
	private static final String ACCESS_TOKEN_CACHE = "ACCESS_TOKEN";

	@Autowired
	private OauthHelper oauthHelper;

	@Autowired
	private RedissonClient redissonClient;

	@Autowired
	private UserService userService;

	@Autowired
	private OauthParams oauthParams;

	@Test
	public void getUserTest() throws IOException {
		String accessToken = oauthHelper.getAccessToken(oauthParams.getTestRefreshToken()).getToken();
		UserDto user = oauthHelper.getUserWithToken(accessToken);
		Assert.assertNotNull(user);
	}

	@Test
	public void crudTest(){
		UserDto user = new UserDto();
		String name = RandomStringUtils.randomAlphabetic(4);
		String accountId = RandomStringUtils.randomNumeric(4);

		user.setName(name);
		user.setRefreshToken(oauthParams.getTestRefreshToken());
		user.setType(oauthParams.getType());
		user.setAccountId(accountId);

		UserDto savedUser = userService.save(user);
		Assert.assertNotNull(savedUser.getId());
		Assert.assertEquals(name,savedUser.getName());
		Assert.assertEquals(accountId,savedUser.getAccountId());
	}

	@Test
	public void accessTokenTest() throws IOException, TokenException {
		AccessToken accessToken = oauthHelper.getAccessToken(oauthParams.getTestRefreshToken());
		UserDto user = oauthHelper.getUserWithToken(accessToken.getToken());
		RMapCache<String,AccessToken> cache = redissonClient.getMapCache(OauthHelper.ACCESS_TOKEN_CACHE);
		cache.put(user.getAccountId(),accessToken);

		UserDto secondUser = oauthHelper.getUser(user.getAccountId());
		Assert.assertEquals(user,secondUser);

		userService.save(secondUser);
		accessToken.setExpiresIn(0);
		UserDto thirdUser = oauthHelper.getUser(secondUser.getAccountId());
		Assert.assertEquals(thirdUser,secondUser);
	}

	@Test(expected = TokenException.class)
	public void noAccessTokenTest() throws IOException, TokenException {
		oauthHelper.getUser(RandomStringUtils.randomNumeric(4));
	}





}

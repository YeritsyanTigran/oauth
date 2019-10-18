package com.TigranCorporations.oauth2.core.utility;

import com.TigranCorporations.oauth2.controller.model.TokenResponse;
import com.TigranCorporations.oauth2.core.domain.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@UtilityClass
public class HttpUrlConnectionHelper {

    public HttpURLConnection constructConnection(String endpoint,Map<String,String> params) throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        writeParamsToUrl(params,connection);
        return connection;
    }

    public HttpURLConnection constructConnection(String endpoint,String accessCode) throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization","Bearer " + accessCode);
        return connection;
    }

    public void writeParamsToUrl(Map<String,String> params, HttpURLConnection connection) throws IOException {
        connection.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(ParametrStringBuilder.getParamsString(params));
        out.flush();
        out.close();
    }

    public static TokenResponse getTokenResponse(HttpURLConnection connection) throws IOException {
        String content = getContent(connection);
        return new ObjectMapper().readValue(content, TokenResponse.class);
    }

    public static UserDto getUserResponse(HttpURLConnection connection) throws IOException {
        String content = getContent(connection);
        return new ObjectMapper().readValue(content,UserDto.class);
    }

    private static String getContent(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        StringBuilder content = new StringBuilder();
        String inputLine;
        while((inputLine = in.readLine())!=null){
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }
}

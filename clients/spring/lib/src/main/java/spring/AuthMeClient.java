package spring;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spring.dto.ServiceDTO;
import spring.dto.ValidateProfileBindingModel;
import spring.exceptions.ExpiredGoldenTokenException;
import spring.exceptions.InvalidGoldenTokenException;
import spring.exceptions.InvalidPlatinumTokenException;
import spring.exceptions.NoPermissionException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class AuthMeClient {
    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd").create();
    private String goldenTokenPrevRequest = "";
    private String platinumTokenLeftPrevRequest = "";

    public void firstRequest(String goldenToken, String issuerName) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder(new URI("http://localhost:8080/api/identity/check/"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .setHeader("goldenToken", goldenToken)
                .setHeader("issuerName", issuerName)
                .build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        ServiceDTO body = gson.fromJson(response.body(), ServiceDTO.class);

        if (response.statusCode() == 401) {
            if (body.getStatus().equals("Invalid golden token!"))
                throw new InvalidGoldenTokenException();
            else if (body.getStatus().equals("Expired golden token!"))
                throw new ExpiredGoldenTokenException();
        }
        platinumTokenLeftPrevRequest = body.getResult();
        goldenTokenPrevRequest = goldenToken;
    }

    public boolean secondRequest(String platinumTokenRight,
                                 ValidateProfileBindingModel bindingModel) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder(new URI("http://localhost:8080/api/identity/check/validate"))
                .setHeader("goldenToken", goldenTokenPrevRequest)
                .setHeader("platinumTokenLeft", platinumTokenLeftPrevRequest)
                .setHeader("platinumTokenRight", platinumTokenRight)
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(bindingModel)))
                .build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        ServiceDTO body = gson.fromJson(response.body(), ServiceDTO.class);


        if (response.statusCode() == 401) {
            if (body.getStatus().equals("No permissions for the requested fields!"))
                throw new NoPermissionException();
            else if (body.getStatus().equals("Invalid platinum token!"))
                throw new InvalidPlatinumTokenException();
        }

        return body.getResult().equals("data-valid");
    }
}

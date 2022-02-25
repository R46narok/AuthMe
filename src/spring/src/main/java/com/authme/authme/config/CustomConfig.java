package com.authme.authme.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "custom")
public class CustomConfig {
    private String dotnetEndpoint;
    private String dotnetEndpoint2;
    private String azureClientId;
    private String azureClientSecret;
    private String azureResource;
    private String azureTenant;
    private String azureInstance;

    public String getDotnetEndpoint() {
        return dotnetEndpoint;
    }

    public CustomConfig setDotnetEndpoint(String dotnetEndpoint) {
        this.dotnetEndpoint = dotnetEndpoint;
        return this;
    }

    public String getAzureClientId() {
        return azureClientId;
    }

    public CustomConfig setAzureClientId(String azureClientId) {
        this.azureClientId = azureClientId;
        return this;
    }

    public String getAzureClientSecret() {
        return azureClientSecret;
    }

    public CustomConfig setAzureClientSecret(String azureClientSecret) {
        this.azureClientSecret = azureClientSecret;
        return this;
    }

    public String getAzureResource() {
        return azureResource;
    }

    public CustomConfig setAzureResource(String azureResource) {
        this.azureResource = azureResource;
        return this;
    }

    public String getAzureTenant() {
        return azureTenant;
    }

    public CustomConfig setAzureTenant(String azureTenant) {
        this.azureTenant = azureTenant;
        return this;
    }

    public String getAzureInstance() {
        return azureInstance;
    }

    public CustomConfig setAzureInstance(String azureInstance) {
        this.azureInstance = azureInstance;
        return this;
    }

    public String getDotnetEndpoint2() {
        return dotnetEndpoint2;
    }

    public CustomConfig setDotnetEndpoint2(String dotnetEndpoint2) {
        this.dotnetEndpoint2 = dotnetEndpoint2;
        return this;
    }
}

# Configure the Azure provider
terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 2.65"
    }
  }

  required_version = ">= 1.1.0"
}

provider "azurerm" {
  features {}
}

# Create a resource group
resource "azurerm_resource_group" "rg" {
  name     = "AuthMeDepl"
  location = "northeurope"
}

# Create a virtual network
resource "azurerm_virtual_network" "vnet" {
    name                = "AuthMeDeplNet"
    address_space       = ["10.0.0.0/16"]
    location            = "northeurope"
    resource_group_name = azurerm_resource_group.rg.name
}

# TODO: Azure Service Fabric

resource "azurerm_app_service" "name" {
  
}

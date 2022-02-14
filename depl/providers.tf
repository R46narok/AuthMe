terraform {
  required_providers {
      kubernetes = {
          source = "hashicorp/kubernetes"
      }
      azurerm = {
        source = "hashicorp/azurerm"
      }
  }
}


provider "kubernetes" {
  config_path = "C:/Users/Acer/.kube/config"
}

provider "azurerm" {
  features {}
}
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
  config_path = "authme-cluster-kubeconfig.yaml"
}

provider "azurerm" {
  features {}
}
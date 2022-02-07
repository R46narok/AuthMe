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

variable "host" {
  type = string
  default = "https://kubernetes.docker.internal:6443"
}

provider "kubernetes" {
  host = var.host
  insecure = true
  config_path = "C:/Users/Acer/.kube/config"
}

provider "azurerm" {
  features {}
}
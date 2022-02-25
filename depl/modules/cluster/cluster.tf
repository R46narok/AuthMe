resource "azurerm_resource_group" "rg" {
  name = "AuthMeDepl"
  location = var.location
}

resource "azurerm_container_registry" "container_registry" {
  name = "AuthMeContainerRegistry"
  resource_group_name = azurerm_resource_group.rg.name
  location = azurerm_resource_group.rg.location
  sku = "Standard"
  admin_enabled = false
}

resource "azurerm_kubernetes_cluster" "cluster" {
  name = "AuthMeCluster"
  resource_group_name = azurerm_resource_group.rg.name
  location = azurerm_resource_group.rg.location
  dns_prefix = "authmecluster"

  default_node_pool {
    name       = "default"
    node_count = "1"
    vm_size    = "standard_d2_v2"
  }

  service_principal  {
    client_id = var.serviceprinciple_id
    client_secret = var.serviceprinciple_key
  }

  linux_profile {
    admin_username = "authmeuser"
    ssh_key {
        key_data = var.ssh_key
    }
  }

  network_profile {
      network_plugin = "kubenet"
      load_balancer_sku = "Standard"
  }

  addon_profile {
    aci_connector_linux {
      enabled = false
    }

    azure_policy {
      enabled = false
    }

    http_application_routing {
      enabled = true
    }

    kube_dashboard {
      enabled = false
    }

    oms_agent {
      enabled = false
    }
  }
}

# resource "azurerm_role_assignment" "role" {
#   principal_id                     = var.serviceprinciple_id
#   role_definition_name             = "AcrPull"
#   scope                            = azurerm_container_registry.container_registry.id
#   skip_service_principal_aad_check = true
# }
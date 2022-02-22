resource "azurerm_resource_group" "rg" {
  name = "AuthMeResourceGroup"
  location = "westeurope"
}

resource "azurerm_container_registry" "container_registry" {
  name = "AuthMeContainerRegistry"
  resource_group_name = "AuthMeResourceGroup"
  location = "westeurope"
  sku = "Standard"
  admin_enabled = false
}
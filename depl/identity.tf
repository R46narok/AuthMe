resource "kubernetes_deployment_v1" "identity" {
  metadata {
    name = "identity-depl"
  }
  spec {
    replicas = 1
    selector {
      match_labels = {
        "service" = "identity"
      }
    }
    template {
      metadata {
        labels = {
          "app"     = "identity"
          "service" = "identity"
        }
      }

      spec {
        container {
          name  = "identity"
          image = "d3ds3g/identity"
          port {
            container_port = 80
            protocol       = "TCP"
          }
          env {
            name  = "ASPNETCORE_URLS"
            value = "http://+:80"
          }
        }
      }
    }
  }
}

resource "kubernetes_service_v1" "identity-clusterip-srv" {
  metadata {
    name = "identity-clusterip-srv"
  }
  spec {
    type = "ClusterIP"
    selector = {
      "app" = "identity"
    }
    port {
      name        = "identity"
      protocol    = "TCP"
      port        = 80
    }
  }
} 

# Identity db

resource "kubernetes_persistent_volume_claim" "identity-identity-mssql-claim" {
  metadata {
    name = "identity-mssql-claim"
  }
  spec {
    access_modes = ["ReadWriteMany"]
    resources {
      requests = {
        storage = "2Gi"
      }
    }
  }
}

resource "kubernetes_deployment_v1" "identity-mssql" {
  metadata {
    name = "identity-mssql-depl"
  }
  spec {
    replicas = 1
    selector {
      match_labels = {
        "app" = "identity-mssql"
      }
    }
    template {
      metadata {
        labels = {
          "app" = "identity-mssql"
        }
      }

      spec {
        container {
          name  = "identity-mssql"
          image = "mcr.microsoft.com/mssql/server"
          port {
            container_port = 1433
          }
          env {
            name  = "ACCEPT_EULA"
            value = "Y"
          }
          env {
            name  = "SA_PASSWORD"
            value = "AuthMeSuperSecurePassword123"
          }
      }
    }
    
    }
  }
}

resource "kubernetes_service_v1" "identity-mssql_clusterip_srv" {
  metadata {
    name = "identity-mssql-clusterip-srv"
  }
  spec {
    type = "ClusterIP"
    selector = {
      "app" = "identity-mssql"
    }
    port {
      name        = "identity-mssql"
      protocol    = "TCP"
      port        = 1433
    }
  }
}
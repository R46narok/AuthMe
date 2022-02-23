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

resource "kubernetes_service" "identity-loadbalancer" {
  metadata {
    name = "identity-loadbalancer"
    annotations = {
        "service.beta.kubernetes.io/azure-load-balancer-internal" = "true"
    }
  }
  spec {
    selector = {
      app = "identity"
    }
    port {
      port        = 80
      target_port = 80
    }

    type = "LoadBalancer"
  }
}

# Identity db

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
      name     = "identity-mssql"
      protocol = "TCP"
      port     = 1433
    }
  }
}
resource "kubernetes_deployment_v1" "dotnet-worker" {
  metadata {
    name = "identity-document-depl"
  }
  spec {
    replicas = 1
    selector {
      match_labels = {
        "service" = "identity-document"
      }
    }
    template {
      metadata {
        labels = {
          "app"     = "identity-document"
          "service" = "identity-document"
        }
      }

      spec {
        container {
          name  = "identity-document"
          image = "d3ds3g/identity-document"
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

resource "kubernetes_service" "identity-document-loadbalancer" {
  metadata {
    name = "identity-document-loadbalancer"
    annotations = {
        "service.beta.kubernetes.io/azure-load-balancer-internal" = "true"
    }
  }
  spec {
    selector = {
      app = "identity-document"
    }
    port {
      port        = 80
      target_port = 80
    }

    type = "LoadBalancer"
  }
}


# identity-document db

resource "kubernetes_deployment_v1" "identity-document-mssql" {
  metadata {
    name = "identity-document-mssql-depl"
  }
  spec {
    replicas = 1
    selector {
      match_labels = {
        "app" = "identity-document-mssql"
      }
    }
    template {
      metadata {
        labels = {
          "app" = "identity-document-mssql"
        }
      }

      spec {
        container {
          name  = "identity-document-mssql"
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

resource "kubernetes_service_v1" "identity-document-document-mssql_clusterip_srv" {
  metadata {
    name = "identity-document-mssql-clusterip-srv"
  }
  spec {
    type = "ClusterIP"
    selector = {
      "app" = "identity-document-mssql"
    }
    port {
      name        = "identity-document-mssql"
      protocol    = "TCP"
      port        = 1433
    }
  }
}
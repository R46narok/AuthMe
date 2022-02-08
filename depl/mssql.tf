
resource "kubernetes_persistent_volume_claim" "mssql-claim" {
  metadata {
    name = "mssql-claim"
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

resource "kubernetes_deployment_v1" "mssql" {
  metadata {
    name = "mssql-depl"
  }
  spec {
    replicas = 1
    selector {
      match_labels = {
        "app" = "mssql"
      }
    }
    template {
      metadata {
        labels = {
          "app" = "mssql"
        }
      }

      spec {
        container {
          name  = "mssql"
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

resource "kubernetes_service_v1" "mssql_clusterip_srv" {
  metadata {
    name = "mssql-clusterip-srv"
  }
  spec {
    type = "ClusterIP"
    selector = {
      "app" = "mssql"
    }
    port {
      name        = "mssql"
      protocol    = "TCP"
      port        = 1433
      target_port = 1433
    }
  }
}

resource "kubernetes_service_v1" "mssql_load_balancer" {
  metadata {
    name = "mssql-loadbalancer"
  }
  spec {
    type = "LoadBalancer"
    selector = {
      "app" = "mssql"
    }
    port {
      protocol    = "TCP"
      port        = 1433
      target_port = 1433
    }
  }
} 
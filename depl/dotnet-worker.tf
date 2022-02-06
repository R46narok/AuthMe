resource "kubernetes_deployment_v1" "dotnet-worker" {
  metadata {
    name = "dotnet-worker-depl"
  }
  spec {
    replicas = 1
    selector {
      match_labels = {
        "service" = "dotnet-worker"
      }
    }
    template {
      metadata {
        labels = {
          "app"     = "dotnet-worker"
          "service" = "dotnet-worker"
        }
      }

      spec {
        container {
          name  = "dotnet-worker"
          image = "d3ds3g/authmedotnetworker:latest"
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

resource "kubernetes_service_v1" "dotnet-worker-loadbalancer" {
  metadata {
    name = "dotnet-worker-loadbalancer"
    labels = {
        "app" = "dotnet-worker"
        "service" = "dotnet-worker"
    }
  }
  spec {
    type = "LoadBalancer"
    port {
      protocol    = "TCP"
      port        = 80
      target_port = 80
    }
    selector = {
        "service" = "dotnet-worker"
    }
  }
} 
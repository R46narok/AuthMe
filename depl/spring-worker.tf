resource "kubernetes_deployment_v1" "spring-worker" {
  metadata {
    name = "spring-worker-depl"
  }
  spec {
    replicas = 1
    selector {
      match_labels = {
        "service" = "spring-worker"
      }
    }
    template {
      metadata {
        labels = {
          "app"     = "spring-worker"
          "service" = "spring-worker"
        }
      }

      spec {
        container {
          name  = "spring-worker"
          image = "d3ds3g/authme-spring-worker:latest"
          port {
            container_port = 8080
            protocol       = "TCP"
          }
        }
      }
    }
  }
}

resource "kubernetes_service_v1" "spring-worker-loadbalancer" {
  metadata {
    name = "spring-worker-loadbalancer"
    labels = {
        "app" = "spring-worker"
        "service" = "spring-worker"
    }
  }
  spec {
    type = "LoadBalancer"
    port {
      protocol    = "TCP"
      port        = 8080
      target_port = 8080
    }
    selector = {
        "service" = "spring-worker"
    }
  }
} 


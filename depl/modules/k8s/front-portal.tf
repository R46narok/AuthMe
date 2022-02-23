resource "kubernetes_deployment_v1" "front-portal" {
  metadata {
    name = "front-portal-depl"
  }
  spec {
    replicas = 1
    selector {
      match_labels = {
        "service" = "front-portal"
      }
    }
    template {
      metadata {
        labels = {
          "app"     = "front-portal"
          "service" = "front-portal"
        }
      }

      spec {
        container {
          name  = "front-portal"
          image = "d3ds3g/front-portal"
          port {
            container_port = 8080
            protocol       = "TCP"
          }
        }
      }
    }
  }
}

resource "kubernetes_service_v1" "front-portal-loadbalancer" {
  metadata {
    name = "front-portal-loadbalancer"
    labels = {
        "app" = "front-portal"
        "service" = "front-portal"
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
        "service" = "front-portal"
    }
  }
} 

# MySql

resource "kubernetes_deployment_v1" "mysql" {
  metadata {
    name = "mysql-depl"
  }
  spec {
    replicas = 1
    selector {
      match_labels = {
        "app" = "mysql"
      }
    }
    template {
      metadata {
        labels = {
          "app" = "mysql"
        }
      }

      spec {
        container {
          name  = "mysql"
          image = "mysql:latest"
          port {
            container_port = 3306
          }
          env {
            name  = "MYSQL_ROOT_PASSWORD"
            value = "example"
          }
          
      }
    }
  }
  }
}


resource "kubernetes_service_v1" "mysql-clusterip-srv" {
  metadata {
    name = "mysql-clusterip-srv"
  }
  spec {
    type = "ClusterIP"
    selector = {
      "app" = "mysql"
    }
    port {
      name        = "mysql"
      protocol    = "TCP"
      port        = 3306
      target_port = 3306
    }
  }
}

resource "kubernetes_service_v1" "mysql-loadbalancer" {
  metadata {
    name = "mysql-loadbalancer"
  }
  spec {
    type = "LoadBalancer"
    selector = {
      "app" = "mysql"
    }
    port {
      protocol    = "TCP"
      port        = 3306
      target_port = 3306
    }
  }
} 
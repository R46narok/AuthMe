
resource "kubernetes_persistent_volume_claim" "mysql-claim" {
  metadata {
    name = "mysql-claim"
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
          volume_mount {
            mount_path = "/var/lib/mysql"
            name       = "mysqldb"
          }
        }
        volume {
          name = "mysqldb"
          persistent_volume_claim {
            claim_name = "mysql-claim"
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
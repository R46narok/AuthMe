resource "kubernetes_ingress" "ingress" {
  metadata {
    name = "ingress"
    annotations = {
      "kubernetes.io/ingress.class" = "addon-http-application-routing"
    }
  }
  spec {/*
    backend {
      service_name = "identity-loadbalancer"
      service_port = 80
    }*/
    rule {
      http {
        path {
          path = "/api/identity/*"
          backend {
            service_name = "identity-loadbalancer"
            service_port = 80
          }
        }
        path {
          path = "/api/identityvalidity/*"
          backend {
            service_name = "identity-loadbalancer"
            service_port = 80
          }
        }
        path {
          path = "api/identitydocument/*"
          backend {
            service_name = "identity-document-loadbalancer"
            service_port = 80
          }
        }
      }
    }
  }
}
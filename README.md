# Keycloak ldap y providers / java spring
Proyecto de revisión y demostración del uso y conexión de keycloak hacia ldap y uso de Identity Providers con conexión desde Spring.
En este ejemplo se plantea una infraestructura para realizar conexiones desde keycloack a travez de algunos proveedores de identidad y ldap, además de una conexión con java spring boot. 
Los servicios se crean por medio de contenedores que desplegarán las diferentes aplicaciones. 

## Requisitos
Para el despliegue de la arquitectura es requerido la instalación de:
- [Docker](https://www.docker.com/products/docker-desktop)
- [Docker compose](https://docs.docker.com/compose/install/)
- [JDK](https://adoptopenjdk.net/) Opcional se recomienda la versión de adopt openjdk
- [Apache Maven](https://maven.apache.org/guides/index.html)

## Referencia a documentación:
-[OpenLDAP](https://www.openldap.org/doc/)

Por medio de el siguiente archivo se crean los contenedores docker que tendrán los servicios para el desarrollo de la prueba:
-[Docker Compose](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/containers/docker-compose.yml)

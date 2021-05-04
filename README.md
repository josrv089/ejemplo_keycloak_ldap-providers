# Keycloak ldap y providers / java spring
Proyecto de revisión y demostración del uso y conexión de keycloak hacia ldap y uso de Identity Providers con conexión desde Spring.
En este ejemplo se plantea una infraestructura para conextar a keycloack a travez de algunos proveedores de identidad y ldap, adems de una conexión con javas pring boot. 
Los servicios se crean por medio de contenedores que desplegarán las diferentes aplicaciones. 

## Requisitos
Para el despliegue de la arquitectura es requerido la instalación de:
- [Docker](https://www.docker.com/products/docker-desktop)
- [Docker compose](https://docs.docker.com/compose/install/)
- [JDK](https://adoptopenjdk.net/) Opcional se recomienda la versión de adopt openjdk
- [Apache Maven](https://maven.apache.org/guides/index.html)

## Referencia a documentación:
-[OpenLDAP](https://www.openldap.org/doc/)

por medio de el siguiente archivo se crean los contenedores docker que tendrán los servicios para el desarrollo de la prueba

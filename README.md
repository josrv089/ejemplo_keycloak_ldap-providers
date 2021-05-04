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

Con una terminal en la ubicación del archivo docker compose, se debe ejcutar el siguiente comando:
```
docker-compose up -d
```
Una vez ejecutado el comando y desplegados los contenedores se debe ingresar a la siguiente ruta:
https://localhost:6443

y para ingresar se deben usar las credenciales:
```
Login DN: cn=admin,dc=labcr,dc=com
Password: admin
```
Para el servidor LDAP se puede ingresar cualquier estructura organizacional, sin ambargo para el ejemplo practico que se está presentando se ofrece una estrcutura predefinida en la carpeta ldap, la cual puede ser importada desde el administrador de ldap indicado en el punto anterior, para lo cual se puede utilizar la opción de importar:
(imgs/importldap.png)
Una vez que se haya importado la estructura, se deberá ver como la siguiente imagen:
(imgs/importados.png)

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

![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/importldap.png)

Una vez que se haya importado la estructura, se deberá ver como la siguiente imagen:
![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/importados.png)
Con esto la configuración del servidor ldap se encuentra finalizada.

El siguiente paso será ingresar a keycloak por medio del siguiente enlace:
(http://$(url_instalacion):9091/auth/)
Ingresar a la consola de administración, ingresar con los datos asignados en el docer compouse:
```
kc_admin
kc_T1sa0Kpf4
```
### NOTA
-Para las versiones más nuevas de keycloak por defecto se establece la conexión por medio de ssl, por lo que dependiendo el entorno de ejecución puede ser necesario instalar certificados o bien, a nivel de base de datos cambiar en la tabla REALM el valor de la columna ssl_required a "NONE"

## Crear un nuevo realm
-En la parte superior izquierda se debe ubicar el mouse sobre "Master" para que se despliegue la opción de crear otro realm:
-click sobre "Add Realm"
![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/crear_realm.png)

En el nuevo realm se debe de crear un nuevo cliente con los siguientes datos:
- Client ID : laboratorio_web
- Client Protocol: openid-conect
-- click En create
- En la pestaña Settings
  - Para el campo Valid Redirect URIs establecer el valor ```http://localhost:9080```
  - Guardar los cambios
- En la pestaña Roles
  - Agregar un nuevo rol con el nombre user

##

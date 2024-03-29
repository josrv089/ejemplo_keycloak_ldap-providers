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
- click En create
- En la pestaña Settings
  - Para el campo Valid Redirect URIs establecer el valor ```http://localhost:9080```
  - Guardar los cambios
- En la pestaña Roles
  - Agregar un nuevo rol con el nombre user

## Configuración de la conexión hacia LDAP
-En el menú principal seleccionar la opción `User Federation`
-Seleccionar el tipo de proveedor ldap
-Buscar el campo que dice `Vendor` y seleccionar la opción `Other`
Dada la configuración actual, en el campo `Username LDAP attribute` se deberá ingresar `mail`, sin embargo este campo también se puede configurar con el valor por defecto `uid`
-Para el campo `Connection URL` se debe ingresar el valor `ldap://ldap_container` Ya que corresponde al contenedor donde está el servidor de LDAP, este parámetro podrá ser configurado con algún otro servidor ldap. La presionar sobre el botón `Test Connection` deberá mostrarse un mensaje de conexión exitosa.
![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/configLDAP.png)
- El campo Users DN se debe establecer el valor de acceso definido en la configuración, `ou=users,dc=labcr,dc=com`

Retomando el archivo de ldap-import.ldif donde se definió la estructura del servidor ldap, se creó un grupo llamado `developers` con `gidnumber` = `500`,para este ejemplo, se realizará una conexión donde únicamente este grupo se listará por lo tanto en en campo `Custom User LDAP Filter` se establecerá el valor `(gidnumber=500)` respetando los paréntesis
- El campo `Bind DN` debe quedar establecido con el valor `cn=admin,dc=labcr,dc=com` y en `Bind Credential` la contraseña generica usada para acceder `admin` y por ultimo guardar los cambios.
![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/configLDAPTestAuth.png)

Esta configuración es suficiente para probar que los usuarios puedan hacer logín, desde este punto se puede ingresar a la aplicación con una conexión desde Spring o cualquier otra conexión Open  ID, sin embargo para probar el acceso hacia ldap, se puede  dar click sobre el botón `Synchronize all users` y verificar en la sección `Users` que efectivamente se hayan importado los usuarios.

# Aplicación JAVA Spring  Boot para  pruebas.
- Ir a https://start.spring.io y crear un proyecto con las siguientes caracteristicas:
```
  - Maven Project
  - Language Java
```
Los demás campos se pueden llenar a con los valores de preferencia. La recomendación es que se descargue con las dependencias de  Spring Web y Spring Security, aunque estos pueden ser agregados posteriormente.
- Agregar las siguientes dependencias al archivo pom.xml
```
<!-- DEPENDENCIAS KEYCLOAK -->
<dependency>
    <groupId>org.keycloak</groupId>
    <artifactId>keycloak-spring-boot-2-adapter</artifactId>
    <version>10.0.0</version>
</dependency>
<dependency>
    <groupId>org.keycloak</groupId>
    <artifactId>keycloak-tomcat7-adapter</artifactId>
    <version>10.0.0</version>
</dependency>
```

- En src/main agregar la carpeta webapp
- Crear un archivo llamado index.html
- Crear un archivo llamado home.html Este será utilizado para acceder desde el index.html
- Se deberán crear los siguientes archivos java:
  - Archivo de configuración keyclok (KeycloakConfig.java)
  - Archivo de configuración de seguridad (SecurityConfig.java)
  - index controller (IndexController.java)

  - ![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/proyecto.png)

### KeycloakConfig
```
@Configuration
public class KeycloakConfig {

	@Bean
	public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
		return new KeycloakSpringBootConfigResolver();
	}
}
```
### SecurityConfig
```
@KeycloakConfiguration
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        SimpleAuthorityMapper grantedAuthorityMapper = new SimpleAuthorityMapper();
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(grantedAuthorityMapper);
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    @Override
    @ConditionalOnMissingBean(HttpSessionManager.class)
    protected HttpSessionManager httpSessionManager() {
        return new HttpSessionManager();
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
                .authorizeRequests()
                .antMatchers("/admin/*").hasAnyRole("admin")
                .antMatchers("/home.html").hasAnyRole("admin", "user")
                .anyRequest().permitAll();
    }
}
```
### SecurityConfig

```
@Controller
public class IndexController {

	private final HttpServletRequest request;

	@Autowired
	public IndexController(HttpServletRequest request) {
		this.request = request;
	}

	@GetMapping(value = "/logout")
	public String logout() throws ServletException {
		request.logout();
		return "redirect:/index.html";
	}

	private KeycloakSecurityContext getKeycloakSecurityContext() {
		return (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
	}
}
```
Configurar en el aplication.properties los siguientes valores:
```
keycloak.realm=laboratorio
keycloak.auth-server-url=http://localhost:9091/auth
keycloak.ssl-required=none
keycloak.resource=laboratorio_web
keycloak.public-client=true
keycloak.verify-token-audience=true
keycloak.use-resource-role-mappings=true
keycloak.confidential-port=0
```
Importante: http://localhost:9091/auth debe ser reemplazado por la url donde está el servidor keycloak
En la configuración del realm, en la pestaña Login, se debe ajustar el campo `Require SSL` de acuerdo a si se tiene o no ssl desde el cliente

Con lo anterior configurado al dar click sonre  "Entrar" el sistema redireccionará hacia keycloak para realizar el login, en esta pantalla se pueden utilizar los usuarios existentes en ldap:
```
Username or email: bill@outlook.com
Password: 123
```
![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/login.png)

El acceso desde java y conectado a keycloak ha sido creado, sin embargo al iniciar sesión se mostrará un error 403 que indica que no se tiene acceso al recurso solicitado, esto se debe a que en el archivo SecurityConfig se ha indicado que a la página `/home.html` tienen acceso los roles `admin` y `user`, sin embargo al crear estos usuarios desde ldap, los permisos no son asignados.
Para esto se realizará un mapeo desde el `User Federation` configurado anteriormente. Para esto se debe ingresar al registro de ldap creado y configurado y dar click sobre la pestaña `Mappers` y seguir los siguientes pasos:
  - Dar click sobre `Create`
  - Asignar un nombre de preferencia.
  - Seleccionar la opción `hardcoded-ldap-role-mapper`
  - Dar click sobre la opción `Select Role`
  - En la sección `Client Roles` seleccionar `laboratorio_web` y seleccionar el rol user
  - Dar click sobre `Save`
![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/rolMapper.png)
Para cerrar sesión se debe ingresar a la url (http://localhost:8080/logout) y al volver a ingresar se podrá ver que ahora si es permitido el acceso

## Autenticación con Google.
Para poder configurar el acceso con google se requiere contar con una cuenta de desarrollador desde google y se deberá llevar en paralelo la configuración junto con los cambios en keycloak.
### Desde keycloak:
- Dar click sobre `Identity Providers`
- Seleccionar Google
- `Client ID` y `Client Secret` son dos campos que se llenarán más adelante.
- El campo `Redirect URI` se utilizará para la configuración desde google.

### Desde google.
- Ir a [google api](https://console.developers.google.com/)
- En la parte superior dar click al lado del logo, donde  se desplegará un cuadro de dialogo para crear un API.
- Seleccionar `Proyecto Nuevo`
- ![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/google.png)
- Asignar el nombre y dar click sobre el botón `Crear`
- Al crear el proyecto se desplegará el menú de administración.
- ![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/google2.png)
- Dar clock sobre Credenciales.
- En esta pantalla aparecerá un mensaje recordando configurar el consentimiento de OAuth, es importante configurar este consentimiento.
- ![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/google3.png)
- En la pantalla de consentimiento se debe seleccionar "Externos" y dar click sobre `CREAR`
- ![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/google4.png)
- En la siguiente pantalla se configurará la información que aparecerá en el cuadro desplegable a la hora de iniciar sesión la primera vez.
- Seleccionar el nombre de la aplicación que leerán todos los usuarios,
- Llenar los demás campos de acuerdo a los requisitos de la aplicación.
- ![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/google5.png)
- Dar click en guardar y continuar.
- Regresar nuevamente a Credenciales y dar click sobre Crear Credenciales
- Seleccionar la opción Id de cliente de oAuth
- ![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/google6.png)
- Seleccionar tipo de aplicación web.
- Ingresar el nombre
- En el espacio `URI de redireccionamiento autorizados` se debe ingresar el valor del campo `Redirect URI` de keycloak mencionado anteriormente.
- Al guardar los cambios, se mostrarán los valores que se deberán ingresar en keycloak.
- ![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/google7.png)
- Regresar a Keycloak y completar los campos
- ![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/google8.png)

Con esta configuración al ingresar nuevamente a la pantalla de login desde la aplicación java, se mostrará la opción de ingresar con google.



## Autenticación con Facebook.
De la misma forma que la autenticación con Google se deberá configurar en paralelo a keycloak con el api de Facebook

### Desde Keycloak.
- Dar click sobre `Identity Providers`
- Seleccionar `Add provider`
- Seleccionar Facebook
- Tener en cuenta el campo `Redirect URI` ya que será utilizado desde la configuración de Facebook.

### Desde Facebook.
Se debe seguir los pasos indicados en la página de facebook developers, ya que es normal que esté cambiando la parte visual de la misma, por lo que se ofrece una guia general teniendo en cuenta que esto puede variar a lo largo del tiempo.

- Ir a la página de [Facebook developers](https://developers.facebook.com)
- Logearse en la consola de administración
- Registrar la cuenta como cuenta de desarrollador
- ![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/facebook1.png)
- Seguir los pasos para completar el registro como desarrollador
- ![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/facebook2.png)
- Crear nueva aplicación
- ![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/facebook3.png)
- Agregar producto de inicio de sesión con facebook
- ![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/facebook4.png)
- Seleccionar tipo de aplicación web.
- ![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/facebook5.png)
- Ingresar en el campo `URL del sitio web` la dirección generada por Keycloak.
- Desde la cofiguración básica seleccionar los campos de identificador de la app y la clave secreta de la app e incluirlos dentro de Keyclok.
- ![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/facebook6.png)

Después de esta configuración se podrá ingresar a la aplicación y realizar el login, hasta este punto debe aparecer tanto el acceso con google como con Facebook.
- ![](https://github.com/josrv089/ejemplo_keycloak_ldap-providers/blob/main/imgs/facebook7.png)

### Notas:
- De acuerdo a las configuraciones realizadas, al utilizar Google como Proveedor de identidad, este no permite usar ip's por lo que keycloak debe estar bajo un nombre de dominio valido.
- Al usar Facebook como proveedor de identidades, a pesar de que permite agregar ip's en los dominios, no se recomienda que keycloak se encuentre en una ip diferente a la aplicación, ya que se complica el redireccionamiento. Se  recomienda que igual estén bajo un mismo nombre de dominio.


## Autenticación con Twitter.
Se deberá configurar en paralelo a keycloak con el api de Twitter

### Desde Keycloak.
- Dar click sobre `Identity Providers`
- Seleccionar `Add provider`
- Seleccionar Twitter
- Tener en cuenta el campo `Redirect URI` ya que será utilizado desde la configuración de Twitter.

### Desde Twitter.
- Ir a la página de [Twitter developers apps](https://developer.twitter.com/en/apps)

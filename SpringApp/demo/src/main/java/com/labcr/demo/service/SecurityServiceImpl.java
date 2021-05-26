/******************************************************************************
 * Consejo Nacional de Rectores (Conare) Derechos de Autor (c) 2020           *
 * ADI Área de Desarrollo Institucional                                       *
 * ATIC Área de Tecnologías de Información y Comunicación                     *
 ******************************************************************************/
package com.labcr.demo.service;

import com.labcr.demo.Entity.Persona;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Value("${keycloak.realm}")
    private String kcRealm;
    @Value("${keycloak.auth-server-url}")
    private String kcAuthServerUrl;
    @Value("${sfs.keycloak.kc_root}")
    private String kcRoot;
    @Value("${sfs.keycloak.kc_pass}")
    private String kcPass;
    @Value("${keycloak.resource}")
    private String kcResource;
    //@Value("${keycloak.credentials.secret}")
    //private String kcClientSecret;

    @Autowired
    public SecurityServiceImpl() {
    }

    private Keycloak conectarKeycloak() {
        Keycloak keycloak;
        try {
            //System.out.println("REALM " + kcRealm);
            keycloak = Keycloak.getInstance(
                    kcAuthServerUrl,
                    kcRealm,
                    kcRoot,
                    kcPass,
                    "admin-cli");
        } catch (Exception ex) {
            keycloak = null;
        }
        return keycloak;
    }

    @Override
    // @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Boolean crearUsuario(Persona persona, String password, List<String> roles, boolean activo) {
        /*boolean ok = false;
        Keycloak keycloak = conectarKeycloak();
        if (keycloak != null) {
            try {
                UserRepresentation user = new UserRepresentation();
                user.setEnabled(activo);
                user.setUsername(persona.getIdentificacion());
                user.setEmail(persona.getEmail());
                user.setFirstName(persona.getNombre());
                user.setLastName(persona.getApellido1());
                user.setEmailVerified(true);
                RealmResource realmResource = keycloak.realm(kcRealm);
                UsersResource usersResource = realmResource.users();
                Response response = keycloak.realm(kcRealm).users().create(user);
                String userId = CreatedResponseUtil.getCreatedId(response);
                UserResource userResource = usersResource.get(userId);
                ClientRepresentation clientRepresentation = realmResource.clients().findByClientId(kcResource).get(0);
                RoleRepresentation roleRepresentation;
                if (roles != null) {
                    for (String rol : roles) {
                        roleRepresentation = realmResource.clients().get(clientRepresentation.getId()).roles().get(rol).toRepresentation();
                        userResource.roles().clientLevel(clientRepresentation.getId()).add(Arrays.asList(roleRepresentation));
                    }
                }
                if (userId == null || userId.isEmpty()) {
                    return false;
                }
                persona.setUUID(userId);
                personaRepository.save(persona);
                cambiarContrasenna(persona, password);
                ok = true;
            } catch (Exception ex) {
                ex.printStackTrace();
                ErrorManager errorManager = new ErrorManager();
                errorManager.logger(ex, this.getClass());
                System.out.println(ex.getMessage());
            }
        }*/
        return true;
    }

    @Override
    //@Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Persona getPersonaActiva() {
        Persona resultado = null;
        try {
            KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            KeycloakPrincipal<KeycloakSecurityContext> keycloakPrincipal =
                    (KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
            AccessToken accessToken = keycloakPrincipal.getKeycloakSecurityContext().getToken();
            resultado = new Persona();
            resultado.setEmail(accessToken.getEmail());
            resultado.setNombre(accessToken.getName());
            resultado.setUuid(accessToken.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    @Override
    public String modificarUsuario(Persona persona) {
        /*Keycloak keycloak = conectarKeycloak();
        List<String> roles = persona.getRoles().stream().map(TipoRol::getNombre).collect(Collectors.toList());
        RealmResource realmResource = keycloak.realm(kcRealm);
        UsersResource usersResource = realmResource.users();
        String userId = persona.getUUID();
        UserResource userResource = usersResource.get(userId);
        ClientRepresentation clientRepresentation = realmResource.clients().findByClientId(kcResource).get(0);
        RoleRepresentation roleRepresentation;
        if (roles != null) {
            for (String rol : roles) {
                try {
                    roleRepresentation = realmResource.clients().get(clientRepresentation.getId()).roles().get(rol).toRepresentation();
                    userResource.roles().clientLevel(clientRepresentation.getId()).add(Arrays.asList(roleRepresentation));
                } catch (NotFoundException ex) {
                    ex.printStackTrace();
                    ErrorManager errorManager = new ErrorManager();
                    errorManager.logger(ex, this.getClass());
                    return "No se encuentra el rol " + rol + " en el servidor de autenticación";
                }
            }
        }
        List<TipoRol> todos = tipoRolRepository.findAll();
        List<String> eliminar = new ArrayList<>();
        for (TipoRol rol : todos) {
            if (!roles.contains(rol.getNombre())) {
                eliminar.add(rol.getNombre());
            }
        }
        for (String rol : eliminar) {
            try {
                roleRepresentation = realmResource.clients().get(clientRepresentation.getId()).roles().get(rol).toRepresentation();
                userResource.roles().clientLevel(clientRepresentation.getId()).remove(Arrays.asList(roleRepresentation));
            } catch (NotFoundException ex) {
            }
        }
        UserRepresentation usuario = new UserRepresentation();
        usuario.setFirstName(persona.getNombre());
        usuario.setLastName(persona.getApellido1());
        usuario.setUsername(persona.getIdentificacion());
        usuario.setEmail(persona.getEmail());
        usuario.setEnabled(persona.getActivo());
        keycloak.realms().realm(kcRealm).users().get(persona.getUUID()).update(usuario);*/
        return "";
    }

    @Override
    public boolean validarContrasenna(Persona persona, String contrasena) {
        final String uri = kcAuthServerUrl + "realms/" + kcRealm + "/protocol/openid-connect/token";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
        /*requestBody.add("client_secret", kcClientSecret);
        requestBody.add("client_id", kcResource + "_api");
        requestBody.add("grant_type", "password");
        requestBody.add("scope", "openid");
        requestBody.add("username", persona.getEmail());
        requestBody.add("password", contrasena);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(requestBody, headers);
        */
        boolean validAuthentication = true;
        /*
        try {
            //if we dont get an exception with the credentials,
            //it means that we have a success login
            ResponseEntity<String> response =
                    restTemplate.exchange(uri, HttpMethod.POST,
                            request, String.class);
        } catch (Exception e) {
            validAuthentication = false;
        }*/
        return validAuthentication;
    }
}

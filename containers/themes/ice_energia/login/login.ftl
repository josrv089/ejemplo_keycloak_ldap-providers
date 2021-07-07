<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=social.displayInfo displayWide=(realm.password && social.providers??); section>
    <#if section = "header">
    <img src="../login/resources/img/favicon.ico">
    <div class="test">
    </div>
    <div class="makeStyles-paper-16">
        ${msg("logIn")}
    </div>
    <#elseif section = "form">
        <div id="kc-form" <#if realm.password && social.providers??>class="${properties.kcContentWrapperClass!}"</#if>>
            <div id="kc-form-wrapper"
                 <#if realm.password && social.providers??>class="${properties.kcFormSocialAccountContentClass!} ${properties.kcFormSocialAccountClass!}"</#if>>
                <#if realm.password>
                    <form id="kc-form-login" onsubmit="login.disabled = true; return true;" action="${url.loginAction}" method="post">
                        <div class="${properties.kcFormGroupClass!}">
                            <label for="username"
                                   class="${properties.kcLabelClass!}"><#if !realm.loginWithEmailAllowed>${msg("username")}<#elseif !realm.registrationEmailAsUsername>${msg("usernameOrEmail")}<#else>${msg("email")}</#if></label>
                            <#if usernameEditDisabled??>
                                <input tabindex="1" id="username" class="${properties.kcInputClass!}" name="username" value="${(login.username!'')}" type="text"
                                       disabled/>
                            <#else>
                                <input tabindex="1" id="username" class="${properties.kcInputClass!}" name="username" value="${(login.username!'')}" type="text"
                                       autofocus autocomplete="off"/>
                            </#if>
                        </div>
                        <div class="${properties.kcFormGroupClass!}">
                            <label for="password" class="${properties.kcLabelClass!}">${msg("password")}</label>
                            <input tabindex="2" id="password" class="${properties.kcInputClass!}" name="password" type="password" autocomplete="off"/>
                        </div>
                        <div class="${properties.kcFormGroupClass!} ${properties.kcFormSettingClass!}">
                            <div id="kc-form-options">
                                <#if realm.rememberMe && !usernameEditDisabled??>
                                    <div class="checkbox">
                                        <label>
                                            <#if login.rememberMe??>
                                                <input tabindex="3" id="rememberMe" name="rememberMe" type="checkbox" checked> ${msg("rememberMe")}
                                            <#else>
                                                <input tabindex="3" id="rememberMe" name="rememberMe" type="checkbox"> ${msg("rememberMe")}
                                            </#if>
                                        </label>
                                    </div>
                                </#if>
                            </div>
                            <div class="${properties.kcFormOptionsWrapperClass!}">
                                <#if realm.resetPasswordAllowed>
                                    <span><!-- aao inhabilita el reset en la página de login a tabindex="5" href="${url.loginResetCredentialsUrl}">${msg("doForgotPassword")}</a --></span>
                                </#if>
                            </div>
                        </div>
                        <div id="kc-form-buttons" class="${properties.kcFormGroupClass!}" >
                            <input tabindex="4"
                                   class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}"
                                   name="login" id="kc-login" type="submit" value="${msg("doLogIn" )}"/>
                        </div>
                    </form>
                </#if>
            </div>
            <#if realm.password && social.providers??>
                <div id="kc-social-providers" class="${properties.kcFormSocialAccountContentClass!} ${properties.kcFormSocialAccountClass!}">
                    <ul class="${properties.kcFormSocialAccountListClass!} <#if social.providers?size gt 4>${properties.kcFormSocialAccountDoubleListClass!}</#if>">
                        <#list social.providers as p>
                            <li class="${properties.kcFormSocialAccountListLinkClass!}"><a href="${p.loginUrl}" id="zocial-${p.alias}"
                                                                                           class="zocial ${p.providerId}"> <span>${p.displayName}</span></a>
                            </li>
                        </#list>
                    </ul>
                </div>
            </#if>
        </div>
        <!-- sfs personalización -->
        <div id="kc-registration" style="text-align:center">
            
             <div class="text-center" style="margin-bottom: 5px">
               <br/> O ingrese con <br/>
            </div>
            <button class="${properties.kcButtonFacebookClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}"><i class="fa fa-facebook-square"></i> Continuar con Facebook</button>
            <br/>
            <button class="${properties.kcButtonGoogleClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}"><i class="fa fa-google"></i> Continuar con Google</button>
            <br/>
            <button class="${properties.kcButtonInstagramClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}"><i class="fa fa-instagram"></i> Continuar con Instagram</button>
            <div class="text-center" style="margin-bottom: 5px">
                <br/>¿No tiene una cuenta todavía?<br/>
            </div>
            <a href="/public/crearCuenta.xhtml" class="btn-sfs-azul">Registrarme ahora</a>
        </div>
        
    <#elseif section = "info" >
        <#if realm.password && realm.registrationAllowed && !registrationDisabled??>
            <div id="kc-registration">
                <span>${msg("noAccount")} <a tabindex="6" href="${url.registrationUrl}">${msg("doRegister")}</a></span>
            </div>
        </#if>
    </#if>

</@layout.registrationLayout>

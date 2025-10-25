package com.eazybytes.gatwayserver.config; 
 
 import org.springframework.core.convert.converter.Converter; 
 import org.springframework.security.core.GrantedAuthority; 
 import org.springframework.security.core.authority.SimpleGrantedAuthority; 
 import org.springframework.security.oauth2.jwt.Jwt; 
 
 import java.util.ArrayList; 
 import java.util.Collection; 
 import java.util.List; 
 import java.util.Map; 
 import java.util.stream.Collectors; 
 
 public class KeycloakRoleConverter  implements Converter<Jwt, Collection<GrantedAuthority>> { 
 
     @Override 
     public Collection<GrantedAuthority> convert(Jwt source) { 
         Map<String, Object> realmAccess = null;
         Object realmAccessObj = source.getClaims().get("realm_access");
         if (realmAccessObj instanceof Map) {
             @SuppressWarnings("unchecked")
             Map<String, Object> temp = (Map<String, Object>) realmAccessObj;
             realmAccess = temp;
         }
         if (realmAccess == null || realmAccess.isEmpty()) { 
             return new ArrayList<>(); 
         } 
        Object rolesObj = realmAccess.get("roles");
        if (!(rolesObj instanceof List)) {
            return new ArrayList<>();
        }
        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) rolesObj;
        Collection<GrantedAuthority> returnValue = roles
                .stream()
                .map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
         return returnValue; 
     } 
 
 }
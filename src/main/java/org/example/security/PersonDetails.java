package org.example.security;

import org.example.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;


public class PersonDetails implements UserDetails {
    private Person person;

    @Autowired
    public PersonDetails(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));// список прав пользователя
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUsername();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true; // аккаунт действительный не просрочен
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //акк не заблокирован
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //пароль не просрочен
    }

    @Override
    public boolean isEnabled() {
        return true; // акк вкл работает
    }

    public Person getPerson() {
        return this.person;
    }
}

package com.mitocode.controller;

import com.mitocode.ejb.UsuarioFacadeLocal;
import com.mitocode.model.Usuario;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class IndexController implements Serializable {

    @EJB
    private UsuarioFacadeLocal usuarioEJB;
    private Usuario usuario;

    @PostConstruct
    public void init() {

        usuario = new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String iniciarSesion() {
        Usuario us;
        String redireccion = null;
        try {
            us = usuarioEJB.iniciarSesion(usuario);
            if (us != null) {
                
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", us);
                // redireccion = "/protegido/principal?faces-redirect=true";
                 redireccion = "/protegido/principal";
                
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", "Usuario Incorrecto"));

            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "AVISO", "ERROR"));

        }
        return redireccion;
    }

}

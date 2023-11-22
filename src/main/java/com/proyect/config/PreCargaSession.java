
package com.proyect.config;
import com.proyect.session.SessionUsuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class PreCargaSession implements HandlerInterceptor {

    @Autowired
    private SessionUsuario session;

    /**
    * @param request current HTTP request
    * @param response current HTTP response
    * @param handler the handler (or {@link HandlerMethod}) that started asynchronous
    * execution, for type and/or instance examination
    * @param modelAndView the {@code ModelAndView} that the handler returned
    * Funcion que se ejecuta luego de ser procesada en un controlador, en caso de retornar una vista, cargamos una instancia
    * de sessionUser en el modelo para poder usar la session en las vistas
    * @throws Exception in case of errors     
    */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            modelAndView.addObject("sessionUser", session);
        }
    }

}
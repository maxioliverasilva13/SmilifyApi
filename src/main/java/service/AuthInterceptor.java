/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import ENTITIES.Usuario;
import dtos.ResponseMessage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import util.JWT;

/**
 *
 * @author rodrigo
 */
@Provider
public class AuthInterceptor implements ContainerRequestFilter {
    @PersistenceContext( unitName = "my_persistence_unit")
    private EntityManager entityManager;

   
    @Override
    public void filter(ContainerRequestContext request) throws IOException {
//            request.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
//
//            request.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
//            request.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
//            request.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
//            
            String url = request.getUriInfo().getAbsolutePath().toString();

            if (url.contains("/resources/authentication") && !url.contains("/current_user") && !url.contains("/editMeInfo") ) {
                return;
            }
            if (url.contains("/resources/entities.paciente")) {
                return;
            }
            if (url.contains("/resources/entities.reserva/validate")) {
                return;
            }
            if (url.contains("/resources/entities.reserva/obtenerFechasByFechas")) {
                return;
            }
             if (url.contains("/resources/entities.reserva")) {
                return;
            }
            String token = request.getHeaderString("Authorization");
            if (token == null || token.equals("")) {
                ResponseMessage res = new ResponseMessage(401, "Unauthorized");
                request.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(res).build());
                return;
            }
            if (JWT.getInstance().isExpired(token)) {
                ResponseMessage res = new ResponseMessage(401, "Invalid Token");
                request.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(res).build());
                return;
            }
            
            
            Long  uid = JWT.getInstance().getUserIdByToken(token); 
            List<Usuario> usuariosResult = this.entityManager.
                    createNativeQuery("Select * from usuario WHERE id=:id", Usuario.class).
                    setParameter("id", uid).getResultList();
            
            if(usuariosResult.isEmpty()){
                ResponseMessage res = new ResponseMessage(401, "Unauthorized");
                request.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(res).build());
                return;
            }
            
            Usuario user =  usuariosResult.get(0);
            request.setProperty("userData", user);


    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dtos.ResponseMessage;
import java.io.IOException;
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

    @Override
    public void filter(ContainerRequestContext request) throws IOException {
        try {
             request.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
            request.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
            request.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
            request.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
            
            String url = request.getUriInfo().getAbsolutePath().toString();

            if (url.contains("/resources/authentication") && !url.contains("current_user")) {
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

//            Long id = JWT.getInstance().getUserIdByToken(token);
//            UsuarioFacadeREST u = new UsuarioFacadeREST();
//            if (u.find(id) == null) {
//                
//                ResponseMessage res = new ResponseMessage(401, "Unauthorized");
//                request.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(res).build());
//                return;
//            }
        } catch (Exception e) {
            ResponseMessage res = new ResponseMessage(401, "Invalid or Expired token");
            request.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(res).build());
            return;
        }

    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package service;

import dtos.UsuarioLoginDTO;
import ENTITIES.Usuario;
import dtos.ResponseMessage;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.EncryptService;
import util.JWT;

/**
 * REST Web Service
 *
 * @author rodrigo
 */
@Path("authentication")
@Stateless
public class AuthenticationResource extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    /**
     * Creates a new instance of AuthenticationResource
     */
    public AuthenticationResource() {
        super(Usuario.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
        
    }
    
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UsuarioLoginDTO userLogin){
        
        Usuario user = super.validarUsuario(userLogin.getEmail(), userLogin.getPassword());
        if(user == null){
            ResponseMessage error = new ResponseMessage(401, "email o contraseña invalida");
            return Response.status(Status.UNAUTHORIZED).entity(error).build();
        }
       
        Map<String,Object> claims = new HashMap<String,Object>();
        claims.put("id", user.getId());
        claims.put("email",user.getEmail());
        JsonObject token = JWT.getInstance().encode(claims);
       
        
        return Response.status(200).entity(token).build();

         
    }
    
  
      
    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    
    public Response register(Usuario user){
        ResponseMessage res;
        user.setPassword(EncryptService.encryptPass(user.getPassword()));
        if(super.getUserByEmail(user.getEmail()) != null){
             res = new ResponseMessage(400, "Ya existe un usuario con ese email");
             return Response.status(400).entity(res).build();
        }
        super.create(user);
        res = new ResponseMessage(201, "Usuario Creado correctamente");
        return Response.status(201).entity(res).build();  

    }

    
}

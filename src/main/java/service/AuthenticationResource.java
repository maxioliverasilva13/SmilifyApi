/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package service;

import ENTITIES.Configuracion;
import dtos.UsuarioLoginDTO;
import ENTITIES.Usuario;
import dtos.ResponseMessage;
import dtos.UsuarioEditDTO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
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
    /**
     * Creates a new instance of AuthenticationResource
     */
    
    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;
    
    
    
    public AuthenticationResource() {
        super(Usuario.class);
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
            ResponseMessage error = new ResponseMessage(401, "email o contraseña invalidass");
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
        Configuracion conf = new Configuracion();
        conf.setPrecioPorOrden(0);
        conf.setUsuario(user);  
        user.setConfiguracion(conf); 
        this.getEntityManager().persist(conf); // Primero se guarda la configuracion
        this.getEntityManager().persist(user); // Primero se guarda la configuracion


  
        res = new ResponseMessage(201, "Usuario Creado correctamente");
        return Response.status(201).entity(res).build();  
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
    @GET
    @Path("current_user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response current_user(@Context HttpHeaders httpHeaders, @Context HttpServletRequest request){
        // ResponseMessage res;
        Usuario user = (Usuario) request.getAttribute("userData");
        return Response.status(201).entity(user).build();   
    }
    
    
    @PUT
    @Path("editMeInfo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editMeInfo(UsuarioEditDTO userEdit, @Context HttpServletRequest request){
        // ResponseMessage res;
        try{
            Usuario user = (Usuario) request.getAttribute("userData");
            user.setNombre(userEdit.nombre);
            user.setApellido(userEdit.apellido);
            Date fechaNac = new SimpleDateFormat("dd/MM/yyyy").parse(userEdit.fechaNacimiento);
            user.setFechaNacimiento(fechaNac);
            user.setCelular(userEdit.celular);
            user.setAvatar(userEdit.avatar);
            
            super.edit(user);

            ResponseMessage res = new ResponseMessage(200, "Informacion editada correctamente");
            return Response.status(Response.Status.ACCEPTED).entity(res).build();

            
            
        } catch (Exception e) {
            ResponseMessage res = new ResponseMessage(400, e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
        

    }

    

    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import ENTITIES.Configuracion;
import ENTITIES.Usuario;
import dtos.ResponseMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.UsuarioFacadeREST;
import util.JWT;
import dtos.ConfiguracionDto;


/**
 *
 * @author rodrigo
 */
@Stateless
@Path("entities.configuracion")
public class ConfiguracionFacadeREST extends AbstractFacade<Configuracion> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public ConfiguracionFacadeREST() {
        super(Configuracion.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Configuracion entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, ConfiguracionDto entity) {
        Configuracion conf = super.find(id);
        if (conf == null) {
            ResponseMessage res = new ResponseMessage(404, "No existe una configuracion con ese id");
            
            return Response.status(404).entity(res).build(); 
        }
        conf.setPrecioPorOrden(entity.getPrecioPorOrden());
        super.edit(conf);
        return Response.status(201).entity(conf).build(); 
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id) {
        
        Configuracion conf = super.find(id);
        
        return Response.status(200).entity(conf).build();
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Configuracion> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Configuracion> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}

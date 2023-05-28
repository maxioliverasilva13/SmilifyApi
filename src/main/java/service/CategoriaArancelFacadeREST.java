/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import ENTITIES.Arancel;
import ENTITIES.CategoriaArancel;
import dtos.ResponseMessage;
import java.util.List;
import javax.ejb.Stateless;
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

/**
 *
 * @author maximilianoolivera
 */
@Stateless
@Path("entities.categoriaarancel")
public class CategoriaArancelFacadeREST extends AbstractFacade<CategoriaArancel> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public CategoriaArancelFacadeREST() {
        super(CategoriaArancel.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(CategoriaArancel entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, CategoriaArancel entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CategoriaArancel find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public Response listar() {
        List<CategoriaArancel> categorias =  super.findAll();
        return Response.status(200).entity(categorias).build();

       // return Response.status(200).entity(categoria).build();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CategoriaArancel> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    
    @POST
    @Path("/create")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createArancelCategoria(CategoriaArancel entity) {
        try {
            List<CategoriaArancel> alreadyExists = (List<CategoriaArancel>)this.em.createNativeQuery("select * from CategoriaArancel where nombre='" + entity.getNombre() + "'", CategoriaArancel.class).getResultList();
            if (alreadyExists.size() > 0) {
                throw new Exception("Ya existe una categoria con este nombre");
            } else {
               super.create(entity);
               CategoriaArancel newArancel = (CategoriaArancel)this.em.createNativeQuery("select * from CategoriaArancel where nombre='" + entity.getNombre() + "'", CategoriaArancel.class).getSingleResult();
               return Response.status(500).entity(newArancel).build();
            }
        } catch (Exception e) {
            ResponseMessage rm = new ResponseMessage(500, e.getMessage());
            return Response.status(500).entity(rm).build();
        }        
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}

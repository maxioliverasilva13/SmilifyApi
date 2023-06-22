/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import ENTITIES.Arancel;
import ENTITIES.ArancelColectivo;
import ENTITIES.ArancelPrivado;
import ENTITIES.CategoriaArancel;
import ENTITIES.Configuracion;
import dtos.ArancelDTO;
import dtos.CreateArancelDTO;
import dtos.ResponseMessage;
import java.util.ArrayList;
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
@Path("entities.arancel")
public class ArancelFacadeREST extends AbstractFacade<Arancel> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public ArancelFacadeREST() {
        super(Arancel.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Arancel entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Arancel entity) {
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
    public Arancel find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET

    @Override
    @Produces( MediaType.APPLICATION_JSON)
    public List<Arancel> findAll() {
        return super.findAll();
    }
    
    @Consumes( MediaType.APPLICATION_JSON)
    @Produces( MediaType.APPLICATION_JSON)
    public Response listar() {
        //List<Arancel> aranceles = super.findAll();
        List<Arancel> aranceles = super.findAll();
        List<ArancelDTO> result  =  new ArrayList();

        aranceles.forEach(arancel ->{
            double precio;
            String type;
            if(arancel instanceof ArancelPrivado){
              ArancelPrivado arancelPrivado =  (ArancelPrivado)arancel;
              precio = arancelPrivado.getPrecio();
              type = "ArancelPrivado";
            }else{  // is a arancel colectivo
                ArancelColectivo arancelColectivo =  (ArancelColectivo)arancel;
                Configuracion conf =  (Configuracion) this.getEntityManager().createNativeQuery("SELECT * FROM configuracion", Configuracion.class).getSingleResult(); // Primero se guarda la configuracion
                precio =  (conf.getPrecioPorOrden() * arancelColectivo.getCantOrdenes());
                type = "ArancelColectivo";
            }
           //public ArancelDTO(Long id, String nombre, String type, String nombreCategoria, Double precio){
           
            result.add(new ArancelDTO(arancel.getId(), arancel.getNombre(), type , arancel.getCategoria().getNombre(), precio));
            
        });
        return Response.status(Response.Status.ACCEPTED).entity(result).build(); 

    }  

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Arancel> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createArancel(CreateArancelDTO arancelDTO) {
        try {
            if (arancelDTO.getType().equals("Privado") == false && arancelDTO.getType().equals("Colectivo") == false ) {
                throw new Exception("Tipo invalido");
            }
            int categoriaId = arancelDTO.getCategoriaId();
            CategoriaArancel categoria = super.findArancel(new Long(categoriaId));
            if (categoria == null) {
                throw new Exception("Categoria invalido");
            }
            Arancel arancelToSave;
            if (arancelDTO.getType().equals("Privado")) {
                ArancelPrivado ap = new ArancelPrivado();
                ap.setCategoria(categoria);
                ap.setNombre(arancelDTO.getNombre());
                ap.setPrecio(new Double(arancelDTO.getPrecio()));
                arancelToSave = ap;
            } else {
                ArancelColectivo ac = new ArancelColectivo();
                ac.setCategoria(categoria);
                ac.setNombre(arancelDTO.getNombre());
                ac.setCantOrdenes(arancelDTO.getCantOrdenes());
                arancelToSave = ac;
            }
            super.create(arancelToSave);
            ResponseMessage rm = new ResponseMessage(200, "Arancel creado correctamente");
            return Response.status(200).entity(rm).build();
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

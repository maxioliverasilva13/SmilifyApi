/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import ENTITIES.Usuario;
import dtos.ResponseMessage;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
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
 * @author mandi
 */
@Stateless
@Path("entities.usuario")
public class UsuarioFacadeREST extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public UsuarioFacadeREST() {
        super(Usuario.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Usuario entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void editMeInfo(@PathParam("id") Long id, Usuario entity) {
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
    public Usuario find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuario> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("estadisticas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response estadisticas() {
        LocalDate fechaActual = LocalDate.now();
        LocalDate primerDiaMes = fechaActual.withDayOfMonth(1);
        String fechaFormateada = primerDiaMes.toString();
        
       BigInteger Pacientes =(BigInteger) getEntityManager().createNativeQuery("select count(DISTINCT consulta.paciente_id) from reserva  RIGHT join consulta on consulta.reserva_id = reserva.id WHERE reserva.fecha >='" + fechaFormateada + "%';").getSingleResult();
       
       BigInteger Consultas =(BigInteger) getEntityManager().createNativeQuery("select count(consulta.id) from reserva RIGHT join consulta on consulta.reserva_id = reserva.id WHERE reserva.fecha >='" + fechaFormateada + "%';").getSingleResult();
       
      
       BigInteger Nuevos =(BigInteger) getEntityManager().createNativeQuery("SELECT COUNT(DISTINCT consulta.paciente_id) \n" +
                                                                                                                                              "FROM reserva \n" +
                                                                                                                                              "RIGHT JOIN consulta ON consulta.reserva_id = reserva.id \n" +
                                                                                                                                              "WHERE reserva.fecha >= '" + fechaFormateada + "'\n" +
                                                                                                                                              "AND consulta.paciente_id NOT IN (\n" +
                                                                                                                                              "	SELECT DISTINCT consulta.paciente_id \n" +
                                                                                                                                              "                 FROM reserva\n" +
                                                                                                                                                "               RIGHT JOIN consulta ON consulta.reserva_id = reserva.id\n" +
                                                                                                                                                "               WHERE reserva.fecha < '" + fechaFormateada + "'\n" +
                                                                                                                                                ");").getSingleResult();
       
       double GananciasDouble =(double) getEntityManager().createNativeQuery("select CASE WHEN sum(consulta.entrega) IS NULL THEN 0 ELSE SUM(consulta.entrega) END AS total  from reserva RIGHT join consulta on consulta.reserva_id = reserva.id WHERE reserva.fecha >='" + fechaFormateada + "%';").getSingleResult();
       
       BigDecimal gananciasDecimal = BigDecimal.valueOf(GananciasDouble);
       BigInteger ganancias = gananciasDecimal.toBigIntegerExact();
       
       
       BigInteger[] estadisticas = {Pacientes, Consultas, Nuevos, ganancias};
        
        //return Response.status(Response.Status.OK).entity(fechaFormateada).build();
        ResponseMessage res = new ResponseMessage(500, fechaFormateada);
            return Response.status(Response.Status.OK).entity(estadisticas).build();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}

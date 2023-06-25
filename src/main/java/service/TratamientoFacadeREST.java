/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import ENTITIES.Paciente;
import ENTITIES.Reserva;
import ENTITIES.Tratamiento;
import dtos.PacienteDTO;
import dtos.ReservaCreateDTO;
import dtos.ReservaDTO;
import dtos.ResponseMessage;
import dtos.TratamientoCreateDTO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * @author rodrigo
 */
@Stateless
@Path("entities.tratamiento")
public class TratamientoFacadeREST extends AbstractFacade<Tratamiento> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public TratamientoFacadeREST() {
        super(Tratamiento.class);
    }
    
//    @GET
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response listarTratamientosByPaciente(@PathParam("id") Long id) {
//        try {
//            Paciente paciente = this.em.find(Paciente.class, tratInfo.getPaciente_id());
//            if (paciente == null) {
//                throw new Exception("Error, el paciente no existe");
//            }
//            Tratamiento newTratamiento = new Tratamiento();
//            newTratamiento.setDescripcion(tratInfo.getDescripcion());
//            newTratamiento.setPaciente(paciente);
//            this.em.persist(newTratamiento);
//            ResponseMessage res = new ResponseMessage(200, "Tratamiento creado correctamente");
//            return Response.status(Response.Status.CREATED).entity(res).build();
//
//        } catch (Exception e) {
//            ResponseMessage res = new ResponseMessage(400, e.getMessage());
//            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
//        }
//
//    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response nuevoTratamiento(TratamientoCreateDTO tratInfo) {
        try {
            Paciente paciente = this.em.find(Paciente.class, tratInfo.getPaciente_id());
            if (paciente == null) {
                throw new Exception("Error, el paciente no existe");
            }
            Tratamiento newTratamiento = new Tratamiento();
            newTratamiento.setDescripcion(tratInfo.getDescripcion());
            newTratamiento.setPaciente(paciente);
            newTratamiento.setStatus("Activo");
            this.em.persist(newTratamiento);
            ResponseMessage res = new ResponseMessage(200, "Tratamiento creado correctamente");
            return Response.status(Response.Status.CREATED).entity(res).build();

        } catch (Exception e) {
            ResponseMessage res = new ResponseMessage(400, e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }

    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private Long lastId() {
        try {
            Long last = (Long) getEntityManager().createNativeQuery("SELECT id FROM reserva ORDER BY id DESC LIMIT 1").getSingleResult();
            return last;
        } catch (Exception e) {
            return null;
        }

    }

}

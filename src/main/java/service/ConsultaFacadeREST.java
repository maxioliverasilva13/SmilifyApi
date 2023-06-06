/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import ENTITIES.Consulta;
import ENTITIES.Paciente;
import ENTITIES.Reserva;
import ENTITIES.Tratamiento;
import dtos.ConsultaCreateDTO;
import dtos.ConsultaDTO;
import dtos.ConsultaUpdateDTO;
import dtos.PacienteDTO;
import dtos.ReservaDTO;
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
 * @author rodrigo
 */
@Stateless
@Path("entities.consulta")
public class ConsultaFacadeREST extends AbstractFacade<Consulta> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public ConsultaFacadeREST() {
        super(Consulta.class);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(ConsultaCreateDTO newConsultaDto) {
       
        try{
            Paciente paciente = this.em.find(Paciente.class, newConsultaDto.pacienteId);           
            if(paciente == null){
                ResponseMessage res =  new ResponseMessage(400,"El paciente no existe");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            Consulta newConsulta = new Consulta();
            newConsulta.setDescripcion(newConsultaDto.descripcion);
            newConsulta.setPago(newConsultaDto.pago);
            newConsulta.setPaciente(paciente);

            if(newConsultaDto.reservaId != null){
                Reserva reserva = this.em.find(Reserva.class, newConsultaDto.reservaId);           
                 if(reserva == null){
                     ResponseMessage res =  new ResponseMessage(400,"La reserva no existe");
                     return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                 }
                 newConsulta.setReserva(reserva);
            }
            if(newConsultaDto.tratamientoId != null){
                Tratamiento tratamiento = this.em.find(Tratamiento.class, newConsultaDto.tratamientoId);           
                 if(tratamiento == null){
                     ResponseMessage res =  new ResponseMessage(400,"El tratamiento no existe");
                     return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                 }
                 newConsulta.setTratamiento(tratamiento);
            }
            super.create(newConsulta);

            PacienteDTO pacienteDto = new PacienteDTO(paciente.getId(),paciente.getNombre(), paciente.getApellido(), paciente.getTelefono(), paciente.getUsuario().getEmail(), paciente.getDireccion(), paciente.getFechaDeNacimiento(), paciente.getActivo());
            ConsultaDTO  consultaDto = new ConsultaDTO(newConsulta.getId(), newConsulta.getDescripcion(), newConsulta.getPago(), pacienteDto );

            return Response.status(Response.Status.CREATED).entity(consultaDto).build();
            
        }catch(Exception e){
                 ResponseMessage res =  new ResponseMessage(500,"Ha ocurrido un error inesperado");
                 return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
          
  
      
    }

    @PUT
    @Path("{id}")
    @Consumes( MediaType.APPLICATION_JSON)
    @Produces( MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") Long id, ConsultaUpdateDTO updateConsulta) {
        try{
            Consulta consulta = super.find(id);
            if(consulta ==  null){
               ResponseMessage res =  new ResponseMessage(400,"Consulta no existe");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            
            Paciente paciente = this.em.find(Paciente.class, updateConsulta.pacienteId);           
            if(paciente == null){
                ResponseMessage res =  new ResponseMessage(400,"El paciente no existe");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            
            if(updateConsulta.reservaId != null && 
                    (consulta.getReserva() == null || updateConsulta.reservaId != consulta.getReserva().getId()  )){
                Reserva reserva = this.em.find(Reserva.class, updateConsulta.reservaId);           
                 if(reserva == null){
                     ResponseMessage res =  new ResponseMessage(400,"La reserva no existe");
                     return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                 }
                 consulta.setReserva(reserva);
            }
            if(updateConsulta.tratamientoId != null  && 
                    (consulta.getTratamiento()== null || updateConsulta.tratamientoId != consulta.getTratamiento().getId()  )){
                Tratamiento tratamiento = this.em.find(Tratamiento.class, updateConsulta.tratamientoId);           
                 if(tratamiento == null){
                     ResponseMessage res =  new ResponseMessage(400,"El tratamiento no existe");
                     return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                 }
                 consulta.setTratamiento(tratamiento);
            }
            
            consulta.setPaciente(paciente);
            consulta.setDescripcion(updateConsulta.descripcion);
            consulta.setPago(updateConsulta.pago);
            super.edit(consulta);
            ResponseMessage res =  new ResponseMessage(200,"Consulta editada correctamente");
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }catch(Exception e){
             ResponseMessage res =  new ResponseMessage(500,"Ha ocurrido un error inesperado");
             return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
       
      
        
    }

    @DELETE
    @Path("{id}")
    @Consumes( MediaType.APPLICATION_JSON)
    @Produces( MediaType.APPLICATION_JSON)
    public Response remove(@PathParam("id") Long id) {
        Consulta consulta = super.find(id);
        if(consulta ==  null){
           ResponseMessage res =  new ResponseMessage(400,"Consulta no existe");
           return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
        super.remove(consulta);
        ResponseMessage res =  new ResponseMessage(200,"Consulta eliominada correctamente");
        return Response.status(Response.Status.ACCEPTED).entity(res).build();
        
   
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") Long id) {
        Consulta consulta = super.find(id);
        if(consulta ==  null){
           ResponseMessage res =  new ResponseMessage(400,"Consulta no existe");
           return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
        
       Paciente paciente =  consulta.getPaciente();
       PacienteDTO pacienteDto = new PacienteDTO(paciente.getId(),paciente.getNombre(), paciente.getApellido(), paciente.getTelefono(), paciente.getUsuario().getEmail(), paciente.getDireccion(), paciente.getFechaDeNacimiento(), paciente.getActivo());
       ConsultaDTO consultaRes = new ConsultaDTO(consulta.getId(),consulta.getDescripcion(), consulta.getPago(),pacienteDto);
       return Response.status(Response.Status.ACCEPTED).entity(consultaRes).build();
    }

   

    @GET
    @Path("{from}/{to}")
    @Produces( MediaType.APPLICATION_JSON)
    public Response findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        List<Consulta> consultasArr = super.findRange(new int[]{from, to});
        List<ConsultaDTO> result =  new ArrayList<ConsultaDTO>();

        consultasArr.forEach(consulta ->{
            Paciente paciente = consulta.getPaciente();
            PacienteDTO pacienteDto = new PacienteDTO(paciente.getId(),paciente.getNombre(), paciente.getApellido(), paciente.getTelefono(), paciente.getUsuario().getEmail(), paciente.getDireccion(), paciente.getFechaDeNacimiento(), paciente.getActivo());
            result.add(new ConsultaDTO(consulta.getId(),consulta.getDescripcion(), consulta.getPago(),pacienteDto));
        });
         
        return Response.status(Response.Status.ACCEPTED).entity(result).build();
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

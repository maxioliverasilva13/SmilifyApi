/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import ENTITIES.Paciente;
import ENTITIES.Reserva;
import dtos.PacienteDTO;
import dtos.ReservaCreateDTO;
import dtos.ReservaDTO;
import dtos.ResponseMessage;
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
@Path("entities.reserva")
public class ReservaFacadeREST extends AbstractFacade<Reserva> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public ReservaFacadeREST() {
        super(Reserva.class);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevaReserva(ReservaCreateDTO reserva){
        Reserva newReserva = new Reserva();
        newReserva.setEstado(reserva.estado);
        try{
             Date fecha =new SimpleDateFormat("dd/MM/yyyy").parse(reserva.fecha);
             newReserva.setFecha(fecha);
        }catch(Exception e){
            ResponseMessage res =  new ResponseMessage(400,"Formato de fecha incorrecto");
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
        Paciente paciente = this.em.find(Paciente.class, reserva.pacienteId);
        newReserva.setPaciente(paciente);
   
        super.create(newReserva);
        
        Long id = this.lastId();
        
        PacienteDTO pacienteDto = new PacienteDTO(paciente.getId(),paciente.getNombre(), paciente.getApellido(), paciente.getTelefono(), paciente.getDireccion(), paciente.getFechaDeNacimiento());
        ReservaDTO  reservaDto = new ReservaDTO(id, newReserva.getEstado(), newReserva.getFecha(), pacienteDto );
        
        return Response.status(Response.Status.CREATED).entity(reservaDto).build();
        
   
    }
    
       
   

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Reserva entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ReservaDTO getById(@PathParam("id") Long id) {
        Reserva reserva =  super.find(id);
        Paciente pacienteData  =   reserva.getPaciente();
        
      // Long id, String estado, Date fecha ,PacienteDTO paciente
      //Long id, String nombre, String apellido, String telefono,String direccion,Date fechaDeNacimiento
        PacienteDTO pacienteDto = new PacienteDTO(pacienteData.getId(), pacienteData.getNombre(), pacienteData.getApellido(), pacienteData.getTelefono(),pacienteData.getDireccion(), pacienteData.getFechaDeNacimiento());
        
        ReservaDTO result = new ReservaDTO(reserva.getId(),reserva.getEstado(), reserva.getFecha(),pacienteDto);
        return result;  
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReservaDTO> listar() {
        List<Reserva> reservas = super.findAll();
        List<ReservaDTO> result =  new ArrayList<ReservaDTO>();
        
        reservas.forEach(reserva ->{
             Paciente pacienteData  =   reserva.getPaciente();
             PacienteDTO pacienteDto = new PacienteDTO(pacienteData.getId(), pacienteData.getNombre(), pacienteData.getApellido(), pacienteData.getTelefono(),pacienteData.getDireccion(), pacienteData.getFechaDeNacimiento());
             ReservaDTO reservaDto = new ReservaDTO(reserva.getId(),reserva.getEstado(), reserva.getFecha(),pacienteDto);
             result.add(reservaDto);
        });
        return result;
        
    }

    @GET
    @Path("{from}/{to}")
    @Produces( MediaType.APPLICATION_JSON)
    public List<ReservaDTO> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
      
        List<Reserva> reservas = super.findRange(new int[]{from, to});
        List<ReservaDTO> result =  new ArrayList<ReservaDTO>();
        
        reservas.forEach(reserva ->{
             Paciente pacienteData  =   reserva.getPaciente();
             PacienteDTO pacienteDto = new PacienteDTO(pacienteData.getId(), pacienteData.getNombre(), pacienteData.getApellido(), pacienteData.getTelefono(),pacienteData.getDireccion(), pacienteData.getFechaDeNacimiento());
             ReservaDTO reservaDto = new ReservaDTO(reserva.getId(),reserva.getEstado(), reserva.getFecha(),pacienteDto);
             result.add(reservaDto);
        });
        return result;
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
    
    
    private Long lastId(){
         try{
             Long last = (Long)getEntityManager().createNativeQuery("SELECT id FROM reserva ORDER BY id DESC LIMIT 1").getSingleResult();
             return last;
         }catch(Exception e){
             return null;
         }
   
    }
    
    
    
}

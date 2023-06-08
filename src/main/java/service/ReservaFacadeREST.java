/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import ENTITIES.Paciente;
import ENTITIES.Reserva;
import dtos.CambiarEstadoReservaDTO;
import dtos.PacienteCreateDTO;
import dtos.PacienteDTO;
import dtos.ReservaCreateDTO;
import dtos.ReservaDTO;
import dtos.ResponseMessage;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response nuevaReserva(ReservaCreateDTO reserva) {

        Reserva newReserva = new Reserva();
        newReserva.setEstado(reserva.estado);
        try {
            Date fecha = new SimpleDateFormat("yyyy/MM/dd hh:mm").parse(reserva.fecha);
            newReserva.setFecha(fecha);
        } catch (Exception e) {
            ResponseMessage res = new ResponseMessage(400, "Formato de fecha incorrecto");
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
        Paciente paciente = this.em.find(Paciente.class, reserva.pacienteId);
        newReserva.setPaciente(paciente);

        super.create(newReserva);

        Long id = this.lastId();

        PacienteDTO pacienteDto = new PacienteDTO(paciente.getId(), paciente.getNombre(), paciente.getApellido(), paciente.getTelefono(), paciente.getCorreo(), paciente.getDireccion(), paciente.getFechaDeNacimiento(), paciente.getActivo());
        ReservaDTO reservaDto = new ReservaDTO(id, newReserva.getEstado(), newReserva.getFecha(), pacienteDto);
        
        ResponseMessage res = new ResponseMessage(200, "Reserva Creada");
        return Response.status(Response.Status.CREATED).entity(res).build();

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
        Reserva reserva = super.find(id);
        Paciente pacienteData = reserva.getPaciente();

        // Long id, String estado, Date fecha ,PacienteDTO paciente
        //Long id, String nombre, String apellido, String telefono,String direccion,Date fechaDeNacimiento
        PacienteDTO pacienteDto = new PacienteDTO(pacienteData.getId(), pacienteData.getNombre(), pacienteData.getApellido(), pacienteData.getTelefono(), pacienteData.getCorreo(), pacienteData.getDireccion(), pacienteData.getFechaDeNacimiento(), pacienteData.getActivo());

        ReservaDTO result = new ReservaDTO(reserva.getId(), reserva.getEstado(), reserva.getFecha(), pacienteDto);
        return result;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReservaDTO> listar() {
        List<Reserva> reservas = this.em.createNativeQuery("SELECT * FROM `reserva` WHERE estado = 'En espera'", Reserva.class).getResultList();
        List<ReservaDTO> result = new ArrayList<ReservaDTO>();

        reservas.forEach(reserva -> {
            Paciente pacienteData = reserva.obtenerPaciente();
            PacienteDTO pacienteDto = new PacienteDTO(pacienteData.getId(), pacienteData.getNombre(), pacienteData.getApellido(), pacienteData.getTelefono(), pacienteData.getCorreo(), pacienteData.getDireccion(), pacienteData.getFechaDeNacimiento(), pacienteData.getActivo());
            ReservaDTO reservaDto = new ReservaDTO(reserva.getId(), reserva.getEstado(), reserva.getFecha(), pacienteDto);
            result.add(reservaDto);
        });
        return result;

    }
    
    @GET
    @Path("listarHoy")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReservaDTO> listarHoy() {
        List<Reserva> reservas = this.em.createNativeQuery("SELECT * FROM `reserva` WHERE estado = 'aceptada' AND fecha >= CURRENT_DATE AND fecha < DATE_ADD(CURRENT_DATE, INTERVAL 1 DAY)", Reserva.class).getResultList();
        List<ReservaDTO> result =  new ArrayList<ReservaDTO>();
        
        reservas.forEach(reserva ->{
             Paciente pacienteData  = reserva.obtenerPaciente();
             if (pacienteData != null) {
                 PacienteDTO pacienteDto = new PacienteDTO(pacienteData.getId(), pacienteData.getNombre(), pacienteData.getApellido(), pacienteData.getTelefono(),pacienteData.getCorreo(), pacienteData.getDireccion(), pacienteData.getFechaDeNacimiento(), pacienteData.getActivo());
             ReservaDTO reservaDto = new ReservaDTO(reserva.getId(),reserva.getEstado(), reserva.getFecha(),pacienteDto);
             result.add(reservaDto);
             }
             
        });
        return result;
    }
    
    @GET
    @Path("listarMensual/{month}/{year}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReservaDTO> listarMensual(@PathParam("month") Integer month, @PathParam("year") Integer year) {
             System.out.println("numero " + month);
        List<Reserva> reservas = this.em.createNativeQuery("SELECT * FROM `reserva` WHERE estado = 'aceptada' AND MONTH(fecha) = :month AND YEAR(fecha) = :year", Reserva.class).setParameter("month", month).setParameter("year", year).getResultList();
        List<ReservaDTO> result =  new ArrayList<>();
        
        reservas.forEach(reserva ->{
             Paciente pacienteData  = reserva.obtenerPaciente();
             PacienteDTO pacienteDto = new PacienteDTO(pacienteData.getId(), pacienteData.getNombre(), pacienteData.getApellido(), pacienteData.getTelefono(),pacienteData.getCorreo(), pacienteData.getDireccion(), pacienteData.getFechaDeNacimiento(), pacienteData.getActivo());
             ReservaDTO reservaDto = new ReservaDTO(reserva.getId(),reserva.getEstado(), reserva.getFecha(),pacienteDto);
             result.add(reservaDto);
        });
        return result;
    }

    @POST
    @Path("cambiarEstado")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cambiarEstado(CambiarEstadoReservaDTO reserva) {
        try {
            if(reserva.operacion.equals("aceptar")){
                em.createNativeQuery("UPDATE reserva r SET estado = 'aceptada' WHERE r.id = :id").setParameter("id", reserva.id).executeUpdate();
                ResponseMessage res = new ResponseMessage(200, "Reserva con el id: "+ reserva.id +" aceptada exitosamente.");
                return Response.status(Response.Status.CREATED).entity(res).build();
            }else if(reserva.operacion.equals("modificar")){
                em.createNativeQuery("UPDATE reserva r SET fecha = :fecha").setParameter("fecha", reserva.fecha).executeUpdate();
                ResponseMessage res = new ResponseMessage(200, "Reserva con el id: "+ reserva.id +" modificada exitosamente. (Se setteó la fecha: "+ reserva.fecha +")");
                return Response.status(Response.Status.CREATED).entity(res).build();
            }
        } catch (Exception e) {
            ResponseMessage res = new ResponseMessage(500, e.getMessage());
            return Response.status(Response.Status.CREATED).entity(res).build();
        }
        return null;
    }
    
    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReservaDTO> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {

        List<Reserva> reservas = super.findRange(new int[]{from, to});
        List<ReservaDTO> result = new ArrayList<ReservaDTO>();

        reservas.forEach(reserva -> {
            Paciente pacienteData = reserva.getPaciente();
            PacienteDTO pacienteDto = new PacienteDTO(pacienteData.getId(), pacienteData.getNombre(), pacienteData.getApellido(), pacienteData.getTelefono(), pacienteData.getCorreo(), pacienteData.getDireccion(), pacienteData.getFechaDeNacimiento(), pacienteData.getActivo());
            ReservaDTO reservaDto = new ReservaDTO(reserva.getId(), reserva.getEstado(), reserva.getFecha(), pacienteDto);
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

    private Long lastId() {
        try {
            Long last = (Long) getEntityManager().createNativeQuery("SELECT id FROM reserva ORDER BY id DESC LIMIT 1").getSingleResult();
            return last;
        } catch (Exception e) {
            return null;
        }

    }

    @GET
    @Path("/validate/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateReservaByUserId(@PathParam("id") Long id) {
        boolean verifyReserva = reservasByUserId(id);
        //return Response.status(Response.Status.BAD_REQUEST).entity(verifyReserva).build();
        if (verifyReserva == true) {
            ResponseMessage res = new ResponseMessage(400, "Ya existe una reserva para este usuario");
            return Response.status(Response.Status.OK).entity(verifyReserva).build();
        }
        ResponseMessage res = new ResponseMessage(200, "El usuario no tiene una reserva");
        return Response.status(Response.Status.OK).entity(verifyReserva).build();
    }
    
    private boolean reservasByUserId(Long id) {
        boolean tieneResultado;
        try {
            getEntityManager().createNativeQuery("SELECT * FROM reserva WHERE paciente_id = " + id + " and fecha >= current_timestamp order by fecha desc limit 1 ;").getSingleResult();
            // Si se obtiene un resultado, se asigna true a la variable tieneResultado
            tieneResultado = true;
        } catch (NoResultException e) {
            // Si no se obtiene ningún resultado, se asigna false a la variable tieneResultado
            tieneResultado = false;
        }
        return tieneResultado;
    }
    
    @GET
    @Path("/obtenerFechasByFechas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateReservaByUserId(@QueryParam("fecha") String fecha) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
           Date date = dateFormat.parse(fecha);
        } catch (Exception e) {
           ResponseMessage res = new ResponseMessage(400, "Formato de fecha incorrecto");
           return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
        
        
        List<Date> fechas = reservasByFecha(fecha);
        
        if (fechas == null) {
            ResponseMessage res = new ResponseMessage(200, "Todos los horarios estan disponibles");
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
        
        return Response.status(Response.Status.OK).entity(fechas).build();
    
    }
    
    private List<Date> reservasByFecha(String fecha) {
        try {
            List<Date> fechas =(List<Date>) getEntityManager().createNativeQuery("SELECT fecha FROM reserva WHERE fecha like '" + fecha + "%';").getResultList();
            
            return fechas;
        } catch (NoResultException e) {
            // Si no se obtiene ningún resultado, se asigna false a la variable tieneResultado
            return null;
        }
        
    }
}

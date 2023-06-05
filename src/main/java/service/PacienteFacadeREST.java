/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import ENTITIES.Paciente;
import ENTITIES.Usuario;
import dtos.CambiarEstadoDTO;
import dtos.PacienteCreateDTO;
import dtos.PacienteDTO;
import dtos.PacienteInfoDTO;
import dtos.ResponseMessage;
import java.net.http.HttpRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author rodrigo
 */
@Stateless
@Path("entities.paciente")
public class PacienteFacadeREST extends AbstractFacade<Paciente> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public PacienteFacadeREST() {
        super(Paciente.class);
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response crear(PacienteCreateDTO pacienteData, @Context HttpServletRequest request) {

        Usuario user = (Usuario) request.getAttribute("userData");

        Paciente newPaciente = new Paciente();
        newPaciente.setNombre(pacienteData.nombre);
        newPaciente.setApellido(pacienteData.apellido);
        newPaciente.setTelefono(pacienteData.telefono);
        newPaciente.setDireccion(pacienteData.direccion);
        newPaciente.setCorre(pacienteData.correo);

        newPaciente.setUsuario(user);
        try {
            Date fechaNac = new SimpleDateFormat("dd/MM/yyyy").parse(pacienteData.fechaDeNacimiento);
            newPaciente.setFechaDeNacimiento(fechaNac);
        } catch (Exception e) {
            ResponseMessage res = new ResponseMessage(400, "Formato de fecha incorrecto");
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
        super.create(newPaciente);

        Long id = this.lastId();
        PacienteDTO result = new PacienteDTO(id, newPaciente.getNombre(), newPaciente.getApellido(), newPaciente.getTelefono(), newPaciente.getCorreo(), newPaciente.getDireccion(), newPaciente.getFechaDeNacimiento());
        return Response.status(Response.Status.CREATED).entity(result).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Paciente entity) {
        super.edit(entity);
    }
    
    @POST
    @Path("/cambiarEstado")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response cambiarEstado(CambiarEstadoDTO estado) {
        try {
            Paciente paciente = this.em.find(Paciente.class, estado.pacienteId);
            if (paciente == null) {
                throw new Exception("Error, el paciente no existe");
            }
            paciente.setTieneAlta(estado.alta);
            Date fechaAlta = new SimpleDateFormat("dd/MM/yyyy").parse(estado.fechaAlta);
            paciente.setFechaDeAlta(fechaAlta);
            em.merge(paciente);
            return Response.status(Response.Status.ACCEPTED).entity(paciente).build();
        } catch (Exception e) {
            ResponseMessage res = new ResponseMessage(500, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @GET
    @Path("/getPacienteInfo/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response findPacienteById(@PathParam("id") Long id) {
        try {
            Paciente paciente = this.em.find(Paciente.class, id);
            if (paciente == null) {
                throw new Exception("Error, el paciente no existe");
            }
            
            PacienteInfoDTO pacienteInfo = new PacienteInfoDTO();
            pacienteInfo.setPacienteInfo(paciente);
            pacienteInfo.setReservas(paciente.getReservas());
            pacienteInfo.setTratamientos(paciente.getTratamientos());
            pacienteInfo.setArchivos(paciente.getArchivo());
            pacienteInfo.setConsultas(paciente.getConsultas());

            return Response.status(Response.Status.ACCEPTED).entity(pacienteInfo).build();
        } catch (Exception e) {
            ResponseMessage res = new ResponseMessage(500, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PacienteDTO getById(@PathParam("id") Long id) {
        Paciente paciente = super.find(id);
        PacienteDTO result = new PacienteDTO(paciente.getId(), paciente.getNombre(), paciente.getApellido(), paciente.getTelefono(), paciente.getCorreo(), paciente.getDireccion(), paciente.getFechaDeNacimiento());
        return result;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response listar() {
        List<Paciente> pacientes = super.findAll();
        List<PacienteDTO> result = new ArrayList<PacienteDTO>();

        pacientes.forEach(paciente -> {
            result.add(new PacienteDTO(paciente.getId(), paciente.getNombre(), paciente.getApellido(), paciente.getTelefono(), paciente.getCorreo(), paciente.getDireccion(), paciente.getFechaDeNacimiento()));
        });

        return Response.status(201).entity(pacientes).build();

    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<PacienteDTO> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        List<Paciente> pacientes = super.findRange(new int[]{from, to});
        List<PacienteDTO> result = new ArrayList<PacienteDTO>();

        pacientes.forEach(paciente -> {
            result.add(new PacienteDTO(paciente.getId(), paciente.getNombre(), paciente.getApellido(), paciente.getTelefono(), paciente.getCorreo(), paciente.getDireccion(), paciente.getFechaDeNacimiento()));
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
            Long last = (Long) getEntityManager().createNativeQuery("SELECT id FROM paciente ORDER BY id DESC LIMIT 1").getSingleResult();
            return last;
        } catch (Exception e) {
            return null;
        }

    }

}

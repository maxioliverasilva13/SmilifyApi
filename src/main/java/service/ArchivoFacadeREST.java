/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import ENTITIES.Archivo;
import ENTITIES.Paciente;
import dtos.ArchivoCreateDTO;
import dtos.ArchivoDTO;
import dtos.ArchivoViewDTO;
import dtos.PacienteDTO;
import dtos.ResponseMessage;
import java.math.BigInteger;
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
 * @author mandi
 */
@Stateless
@Path("entities.archivo")
public class ArchivoFacadeREST extends AbstractFacade<Archivo> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public ArchivoFacadeREST() {
        super(Archivo.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Archivo entity) {
        super.create(entity);
    }

    @POST
    @Path("createArchivo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createArchivo(ArchivoCreateDTO archivo) {
        Archivo createArchivo = new Archivo();
        createArchivo.setTipo(archivo.tipo);
        createArchivo.setUrl(archivo.url);
        createArchivo.setFileName(archivo.fileName);
        try {
            Paciente paciente = this.em.find(Paciente.class, archivo.paciente_id);
            createArchivo.setPaciente(paciente);
            super.create(createArchivo);

            int id = this.lastId();

            //PacienteDTO pacienteDto = new PacienteDTO(paciente.getId(), paciente.getNombre(), paciente.getApellido(), paciente.getTelefono(), paciente.getUsuario().getEmail(), paciente.getDireccion(), paciente.getFechaDeNacimiento());
            ArchivoViewDTO archivoDto = new ArchivoViewDTO(id, archivo.tipo, archivo.url, archivo.paciente_id);

            return Response.status(Response.Status.CREATED).entity(archivoDto).build();
        } catch (Exception e) {
            ResponseMessage res = new ResponseMessage(500, e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Archivo entity) {
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
    public Archivo find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Archivo> findAll() {
        return super.findAll();
    }

    @GET
    @Path("getArchivosByPacienteId/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArchivosByPacienteId(@PathParam("id") Long id) {
        try {
            //System.err.println("id es " + id);
            List<Archivo> archivos = (List<Archivo>) getEntityManager().createNativeQuery("SELECT * FROM archivo WHERE paciente_id = " + id + "", Archivo.class).getResultList();
            List<ArchivoViewDTO> result =  new ArrayList<>();

            archivos.forEach(archivo ->{
                //Paciente paciente = em.find(Paciente.class, id);
                //PacienteDTO pacienteDto = new PacienteDTO(paciente.getId(),paciente.getNombre(), paciente.getApellido(), paciente.getTelefono(), paciente.getUsuario().getEmail(), paciente.getDireccion(), paciente.getFechaDeNacimiento());
                result.add(new ArchivoViewDTO(Integer.parseInt(archivo.getId().toString()), archivo.getTipo(), archivo.getUrl(), id));
            });
            return Response.status(Response.Status.ACCEPTED).entity(result).build();
        } catch (Exception e) {
            ResponseMessage res = new ResponseMessage(500, e.getMessage());
            return Response.status(500).entity(res).build();
        }
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Archivo> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    private int lastId() {
        try {
            int last = ((BigInteger) getEntityManager().createNativeQuery("SELECT id FROM archivo ORDER BY id DESC LIMIT 1").getSingleResult()).intValue();
            return last;
        } catch (Exception e) {
            System.err.println("Error en ArchivoFacade, contenido: " + e.getMessage());
            return -1;
        }

    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}

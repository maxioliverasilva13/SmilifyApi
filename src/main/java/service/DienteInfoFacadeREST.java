/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import ENTITIES.CategoriaArancel;
import ENTITIES.DienteInfo;
import ENTITIES.Paciente;
import dtos.CreateDienteInfoDTO;
import dtos.DienteInfoDTO;
import dtos.GetDienteInfoDTO;
import dtos.ResponseMessage;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author maximilianoolivera
 */
@Stateless
@Path("entities.dienteinfo")
public class DienteInfoFacadeREST extends AbstractFacade<DienteInfo> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public DienteInfoFacadeREST() {
        super(DienteInfo.class);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(CreateDienteInfoDTO info) {
        try {
            String type = info.getType();
            if (!type.equals("Arriba") && !type.equals("Abajo") && !type.equals("Izquierda") && !type.equals("Derecha") && !type.equals("Centro")) {
                throw new Exception("Type invalido");
            }
            int pacienteId = info.getPacienteId();
            Paciente paciente = super.findPaciente(new Long(pacienteId));
            if (paciente == null) {
                throw new Exception("Paciente invalido");
            }

            DienteInfo df = new DienteInfo();
            df.setDescripcion(info.getDescripcion());
            df.setDienteId(info.getDienteId());
            df.setFecha(info.getFecha());
            df.setPacienteInfo(paciente);
            df.setType(info.getType());
            this.em.persist(df);

//           super.create(entity);
            ResponseMessage res = new ResponseMessage(200, "DienteInfo Creado correctamente");
            return Response.status(201).entity(res).build();

        } catch (Exception e) {
            ResponseMessage res = new ResponseMessage(403, e.getMessage());
            return Response.status(201).entity(res).build();
        }
    }

    @GET
    @Path("/infoDientesByPaciente")
    @Produces({MediaType.APPLICATION_JSON})
    public Response obtenerInfoDientesByPaciente(@QueryParam("pacienteId") Long pacienteId) {
        try {
            if (pacienteId == null) {
                throw new Exception("pacienteId invalido");
            }
            Paciente paciente = super.findPaciente(new Long(pacienteId));
            if (paciente == null) {
                throw new Exception("Paciente invalido");
            }
            Set<DienteInfo> dientesInfo = paciente.obtenerDientesInfo();

            return Response.status(201).entity(dientesInfo).build();

        } catch (Exception e) {
            ResponseMessage res = new ResponseMessage(403, e.getMessage());
            return Response.status(201).entity(res).build();
        }
    }

    
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, DienteInfo entity) {
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
    public DienteInfo find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @POST
    @Path("/getInfoDiente")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getInfoDiente(GetDienteInfoDTO info) {
        try {
            String type = info.getType();
            if (!type.equals("Arriba") && !type.equals("Abajo") && !type.equals("Izquierda") && !type.equals("Derecha") && !type.equals("Centro")) {
                throw new Exception("Type invalido");
            }
            int pacienteId = info.getPacienteId();
            Paciente paciente = super.findPaciente(new Long(pacienteId));
            if (paciente == null) {
                throw new Exception("Paciente invalido");
            }

            List<DienteInfo> dientesInfo = this.em.createNativeQuery("SELECT * FROM `DienteInfo` WHERE dienteId='" + info.getDienteId() + "' and pacienteInfo_id='" + info.getPacienteId() + "' and type='" + info.getType() + "'", DienteInfo.class).getResultList();
            List<DienteInfoDTO> dientes = new ArrayList<>();
            if (dientesInfo.size() > 0) {
                dientesInfo.forEach((diente) -> {
                    dientes.add(diente.getDTO());
                });
            }

//           super.create(entity);
            return Response.status(201).entity(dientes).build();

        } catch (Exception e) {
            ResponseMessage res = new ResponseMessage(403, e.getMessage());
            return Response.status(400).entity(res).build();
        }
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<DienteInfo> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

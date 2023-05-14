/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dtos.ResponseMessage;
import java.io.IOException;
import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import util.JWT;

/**
 *
 * @author rodrigo
 */
@Provider
public class CorsInterceptor implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, 
      ContainerResponseContext responseContext) throws IOException {
          responseContext.getHeaders().add(
            "Access-Control-Allow-Origin", "*");
          responseContext.getHeaders().add(
            "Access-Control-Allow-Credentials", "true");
          responseContext.getHeaders().add(
           "Access-Control-Allow-Headers",
           "origin, content-type, accept, authorization");
          responseContext.getHeaders().add(
            "Access-Control-Allow-Methods", 
            "GET, POST, PUT, DELETE, OPTIONS, HEAD");

    }
    
}

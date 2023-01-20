package org.example;


import org.glassfish.grizzly.http.HttpContext;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

@Path("inbox")
public class Upload {
    @Context
    private HttpContext httpCtx;

    @Context
    private ContainerRequestContext context;
    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadImage(@FormDataParam("file") InputStream uploadedInputStream,
                                @FormDataParam("file") FormDataContentDisposition fileDetails) {

        return Response.ok("File name = " + fileDetails.getFileName() + ", Response = " + context.getProperty("response")).build();
    }

}
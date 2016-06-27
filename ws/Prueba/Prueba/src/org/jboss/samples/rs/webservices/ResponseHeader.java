package org.jboss.samples.rs.webservices;

import javax.ws.rs.core.Response;

public class ResponseHeader
{      
    public static Response addHeader(String obj,String token)
    {
        return Response.status(200).entity(obj)
       .header("Access-Control-Allow-Origin", "*")
       .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
       .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
       .header(ResponseManager.cookieName,token)
       .build();
    }

    public static Response addHeader(String obj, String origin, String methods, String headers)
    {                
       return Response.status(200).entity(obj).header("Access-Control-Allow-Origin", origin)
               .header("Access-Control-Allow-Methods", methods)
               .header("Access-Control-Allow-Headers", headers)
               .build();
    }    
    
 }


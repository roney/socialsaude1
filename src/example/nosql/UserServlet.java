package example.nosql;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.cloudant.client.api.Database;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import model.User;

@Path("/users")
public class UserServlet {

    public UserServlet() {

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("id") String id) throws Exception {

        Database db = null;
        try {
            db = getDB();
        } catch (Exception re) {
            String message = "Cannot connect to database: " + re.getMessage();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
        }

        JsonObject resultObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();

        // Se id não é especificado, retorna todos os usuários existentes
        if (id == null) {
            try {
                List<HashMap> allDocs = db.getAllDocsRequestBuilder().includeDocs(true).build().getResponse()
                        .getDocsAs(HashMap.class);

                for (HashMap doc : allDocs) {
                    // Se existe chave username, é representação de um usuário
                    if (doc.get("username") != null) {
                        Gson g = new Gson();
                        jsonArray.add(g.toJsonTree(doc));
                    }
                }
            } catch (Exception io) {
                String message = "Exception thrown : " + io.getMessage();
                System.out.println(message);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
            }

            resultObject.addProperty("id", "users");
            resultObject.add("body", jsonArray);

            return Response.ok(resultObject.toString()).build();
        }

        // Procura usuário com id passado por parâmetro
        User obj = db.find(User.class, id + "");
        if (obj != null) {
            Gson g = new Gson();
            String json = g.toJson(obj);
            return Response.ok(json).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.TEXT_PLAIN })
    public Response create(User user) throws Exception {

        Database db = null;
        try {
            db = getDB();
        } catch (Exception re) {
            re.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        com.cloudant.client.api.model.Response response = create(db, user);

        if (response.getError() == null) {
            return Response.status(Response.Status.OK).entity(response.getId()).build();
        } else {
            return Response.status(Status.BAD_REQUEST).entity(response.getError()).build();
        }
    }

    private Database getDB() {
        return CloudantClientMgr.getDB();
    }

    protected com.cloudant.client.api.model.Response create(Database db, User reqUser) throws IOException {
        String id = null;

        // Verifica se o usuário já existe
        Database resp = db;
        User existingUser = (reqUser.getId() == null) ? null : resp.find(User.class, reqUser.getId());
        com.cloudant.client.api.model.Response response = null;

        if (existingUser == null) {
            id = String.valueOf(new Date().getTime());
            System.out.println("Criando novo usuário com id: " + id);
            reqUser.setId(id);
            response = db.save(reqUser);

        } else {
            id = existingUser.getId();
            existingUser.setUsername(reqUser.getUsername());
            existingUser.setPassword(reqUser.getPassword());
            response = db.update(existingUser);
        }

        return response;

    }
}

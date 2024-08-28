package controller;

import entity.User;
import dto.ResponseError;
import dto.UserRequest;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.UserRepository;

import java.util.Set;


@Path("/users")
public class UserController {

    private final UserRepository repository;
    private final Validator validator;

    @Inject
    public UserController(UserRepository repository, Validator validator) {

        this.repository = repository;
        this.validator = validator;
    }

    @GET
    public Response getAll() {

        PanacheQuery<User> query = repository.findAll();
        return Response.ok(query.list()).build();

    }

    @POST
    @Transactional
    public Response createUser(UserRequest userRequest) {

        Set<ConstraintViolation<UserRequest>> validate = validator.validate(userRequest);

        if (!validate.isEmpty()) {

            return ResponseError.createFromValidation(validate).withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);

        }

        User user = new User();
        user.setName(userRequest.getName());
        user.setAge(userRequest.getAge());

        repository.persist(user);

        return Response.status(Response.Status.CREATED.getStatusCode()).entity(user).build();

    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, UserRequest userRequest) {

        try {
            User user = repository.findById(id);
            if (user != null) {
                user.setName(userRequest.getName());
                user.setAge(userRequest.getAge());
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuario nao encontrado.")
                        .build();
            }
        } catch (Exception e) {

            System.err.println("Erro ao atualizar usuario: " + e.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Ocorreu um erro inesperado.")
                    .build();
        }
    }


    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long id) {

        User user = repository.findById(id);

        if (user == null) {

            return Response.status(Response.Status.NOT_FOUND).entity("Usuario nao encontrado.").build();
        }

        try {

           repository.delete(user);

            return Response.ok().build();
        } catch (Exception e) {

            return Response.serverError()
                    .entity("Erro ao tentar excluir o usuario: " + e.getMessage())
                    .build();
        }
    }



}

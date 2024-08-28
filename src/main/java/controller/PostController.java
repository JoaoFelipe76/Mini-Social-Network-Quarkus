package controller;

import dto.PostRequest;
import dto.PostResponse;
import entity.Post;
import entity.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import repository.FollowerRepository;
import repository.PostRepository;
import repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Path("/users/{userId}/posts")
public class PostController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FollowerRepository followerRepository;

    @Inject
    public PostController(UserRepository userRepository, PostRepository postRepository, FollowerRepository followerRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.followerRepository = followerRepository;
    }

    @GET
    public Response listPosts(@PathParam("userId") Long id, @HeaderParam("followerId") Long followerId) {

        if (followerId == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User user = userRepository.findById(id);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        User follower = userRepository.findById(followerId);

        if (follower == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        boolean follows = followerRepository.follows(follower, user);

        if (!follows) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        PanacheQuery<Post> query = postRepository.find("user", Sort.by("dateTime", Sort.Direction.Descending), user);
        List<Post> list = query.list();
        List<PostResponse> listPostResponse = list.stream().map(PostResponse::fromEntity).toList();
        return Response.ok(listPostResponse).build();
    }


    @POST
    @Transactional
    public Response savePost(@PathParam("userId") Long id, PostRequest postRequest) {

        User userId = userRepository.findById(id);

        if (userId == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Post post = new Post();
        post.setText(postRequest.getText());
        post.setUser(userId);
        post.setDateTime(LocalDateTime.now());
        postRepository.persist(post);

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response editPost(@PathParam("userId") Long userId, @PathParam("id") Long postId, PostRequest postRequest) {

        User user = userRepository.findById(userId);
        Post post = postRepository.findById(postId);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (post == null || !post.getUser().equals(user)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        post.setText(postRequest.getText());
        postRepository.persist(post);

        return Response.ok(PostResponse.fromEntity(post)).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deletePost(@PathParam("userId") Long userId, @PathParam("id") Long postId) {

        User user = userRepository.findById(userId);
        Post post = postRepository.findById(postId);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (post == null || !post.getUser().equals(user)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        postRepository.delete(post);
        return Response.ok().build();
    }
}

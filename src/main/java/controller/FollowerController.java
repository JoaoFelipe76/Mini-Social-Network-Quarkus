package controller;

import dto.FollowRequest;
import dto.FollowerResponse;
import dto.FollowersPerUserResponse;
import entity.Follower;
import entity.User;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import repository.FollowerRepository;
import repository.UserRepository;

import java.util.List;

@Path("/users/{userId}/followers")
public class FollowerController {

    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;

    @Inject
    public FollowerController(FollowerRepository followerRepository, UserRepository userRepository) {

        this.followerRepository = followerRepository;
        this.userRepository = userRepository;

    }

    @GET
    public Response listFollowers(@PathParam("userId") Long userId) {

        User user = userRepository.findById(userId);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var list = followerRepository.findFollowers(userId);
        FollowersPerUserResponse response = new FollowersPerUserResponse();
        response.setFollowersCount(list.size());

        List<FollowerResponse> followersList = list.stream().map(FollowerResponse::new).toList();

        response.setContent(followersList);
        return Response.ok(response).build();

    }

    @PUT
    @Transactional
    public Response followUser(@PathParam("userId") Long userId,FollowRequest request) {

        User user = userRepository.findById(userId);

        if (userId.equals(request.getFollowerId())){

            return Response.status(Response.Status.CONFLICT).build();

        }

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var follower = userRepository.findById(request.getFollowerId());

        boolean follows = followerRepository.follows(follower, user);

        if (!follows){

            var entity = new Follower();
            entity.setUser(user);
            entity.setFollower(follower);
            followerRepository.persist(entity);

        }

        return Response.status(Response.Status.NO_CONTENT).build();

    }

    @DELETE
    @Transactional
    public Response unfollowUser(@PathParam("userId") Long userId, @QueryParam("followerId") Long followerId) {

        User user = userRepository.findById(userId);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        followerRepository.deleteByFollowerAndUser(followerId,  userId);

        return Response.status(Response.Status.NO_CONTENT).build();

    }
}

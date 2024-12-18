package com.liamfrager.connect.service;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liamfrager.connect.entity.Follow;
import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.exception.*;
import com.liamfrager.connect.repository.FollowRepository;
import com.liamfrager.connect.repository.UserRepository;

/**
 * A service for handling the <code>User</code> business logic.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    /**
     * Constructor for the user service.
     */
    public UserService(UserRepository userRepository, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }

    /**
     * Get data for the given user.
     * @param userID The ID of the user whose data to return.
     * @return The friends of the user.
     * @throws InvalidUserException User with id <code>userID</code> does not exist.
     */
    public User getUser(long userID) throws InvalidUserException {
        User user = userRepository.findById(userID).orElseThrow(() -> new InvalidUserException(userID));
        user.setPassword(null);
        return user;
    }

    /**
     * Get the friends of a given user.
     * @param userID The ID of the user whose friends to return.
     * @return The friends of the user.
     * @throws InvalidUserException User with id <code>userID</code> does not exist.
     */
    public Set<User> getAllFollowingByUserID(long userID) throws InvalidUserException {
        return followRepository.findFollowing(userID).orElseThrow(() -> new InvalidUserException(userID));
    }

    /**
     * Get the followers of a given user.
     * @param userID The ID of the user whose followers to return.
     * @return The followers of the user.
     * @throws InvalidUserException User with id <code>userID</code> does not exist.
     */
    public Set<User> getAllFollowersByUserID(long userID) throws InvalidUserException {
        return followRepository.findFollowers(userID).orElseThrow(() -> new InvalidUserException(userID));
    }

    @Transactional
    public void followUser(long followerUserID, long followeeUserID) throws InvalidUserException, InvalidFollowException {
        if (followerUserID == followeeUserID || followRepository.existsByFollowerAndFollowee(followerUserID, followeeUserID)) {
            throw new InvalidFollowException(followeeUserID);
        }

        User follower = userRepository.findById(followerUserID)
            .orElseThrow(() -> new InvalidUserException(followerUserID));
        User followee = userRepository.findById(followeeUserID)
            .orElseThrow(() -> new InvalidUserException(followeeUserID));

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowee(followee);

        followRepository.save(follow);
    }

    @Transactional
    public void unfollowUser(long followerUserID, long followeeUserID) throws InvalidUserException, InvalidFollowException {
        if (followerUserID == followeeUserID || !followRepository.existsByFollowerAndFollowee(followerUserID, followeeUserID)) {
            throw new InvalidFollowException(followeeUserID);
        }

        userRepository.findById(followerUserID).orElseThrow(() -> new InvalidUserException(followerUserID));
        userRepository.findById(followeeUserID).orElseThrow(() -> new InvalidUserException(followeeUserID));

        followRepository.deleteFollowByFollowerAndFollowee(followerUserID, followeeUserID);
    }

    public Boolean isFollowing(long followerUserID, long followeeUserID){
        return followRepository.existsByFollowerAndFollowee(followerUserID, followeeUserID);
    }
}

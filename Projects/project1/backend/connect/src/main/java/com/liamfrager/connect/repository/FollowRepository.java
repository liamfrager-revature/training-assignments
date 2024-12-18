package com.liamfrager.connect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.liamfrager.connect.entity.Follow;
import com.liamfrager.connect.entity.User;

/**
 * A JPA repository for likes.
 */
public interface FollowRepository extends JpaRepository<Follow, Long> {
    /**
     * Delete a follow with the given ID.
     * @param followID The ID of the follow to delete.
     * @return The number of entries updated.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Follow f WHERE f.follower.id = :followerID AND f.followee.id = :followeeID")
    public int deleteFollowByFollowerAndFollowee(@Param("followerID") long followerID, @Param("followeeID") long followeeID);

    @Query("SELECT f.follower FROM Follow f WHERE f.followee.id = :followeeID")
    public Optional<List<User>> findFollowers(@Param("followeeID") long followeeID);

    @Query("SELECT f.followee FROM Follow f WHERE f.follower.id = :followerID")
    public Optional<List<User>> findFollowing(@Param("followerID") long followerID);

    @Query("SELECT COUNT(*) > 0 FROM Follow f WHERE f.follower.id = :followerID AND f.followee.id = :followeeID")
    public Boolean existsByFollowerAndFollowee(@Param("followerID") long followerID, @Param("followeeID") long followeeID);
}

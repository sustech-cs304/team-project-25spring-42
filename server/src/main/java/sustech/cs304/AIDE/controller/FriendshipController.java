package sustech.cs304.AIDE.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sustech.cs304.AIDE.model.Friendship;
import sustech.cs304.AIDE.model.User;
import sustech.cs304.AIDE.repository.FriendshipRepository;
import sustech.cs304.AIDE.repository.UserRepository;

@RestController
@RequestMapping("/friendship")
public class FriendshipController {
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public FriendshipController(
        FriendshipRepository friendshipRepository,
        UserRepository userRepository
    ) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/apply")
    @Transactional
    public ResponseEntity<String> applyFriendship(@RequestParam String applicantId, @RequestParam String targetId) {
        Friendship friendship = new Friendship(applicantId, targetId, "pending");
        friendshipRepository.save(friendship);
        return ResponseEntity.ok("Friendship request sent from " + applicantId + " to " + targetId);
    }

    @GetMapping("/accept")
    @Transactional
    public ResponseEntity<String> acceptFriendship(@RequestParam String applicantId, @RequestParam String targetId) {
        Friendship friendship = friendshipRepository.findByApplicantIdAndTargetId(applicantId, targetId);
        if (friendship != null) {
            friendship.setStatus("accepted");
            friendshipRepository.save(friendship);
            return ResponseEntity.ok("Friendship request accepted between " + applicantId + " and " + targetId);
        } else {
            return ResponseEntity.status(404).body("Friendship request not found");
        }
    } 

    @GetMapping("/reject")
    @Transactional
    public ResponseEntity<String> rejectFriendship(@RequestParam String applicantId, @RequestParam String targetId) {
        Friendship friendship = friendshipRepository.findByApplicantIdAndTargetId(applicantId, targetId);
        if (friendship != null) {
            friendshipRepository.delete(friendship);
            return ResponseEntity.ok("Friendship request rejected between " + applicantId + " and " + targetId);
        } else {
            return ResponseEntity.status(404).body("Friendship request not found");
        }
    }

    @GetMapping("/delete")
    @Transactional
    public ResponseEntity<String> deleteFriendship(@RequestParam String userId1, @RequestParam String userId2) {
        Friendship friendship1 = friendshipRepository.findByApplicantIdAndTargetId(userId1, userId2);
        Friendship friendship2 = friendshipRepository.findByApplicantIdAndTargetId(userId2, userId1);

        if (friendship1 != null) {
            friendshipRepository.delete(friendship1);
            return ResponseEntity.ok("Friendship deleted between " + userId1 + " and " + userId2);
        } else if (friendship2 != null) {
            friendshipRepository.delete(friendship2);
            return ResponseEntity.ok("Friendship deleted between " + userId2 + " and " + userId1);
        } else {
            return ResponseEntity.status(404).body("Friendship not found");
        }
    }

    @GetMapping("/getFriendList")
    @Transactional
    public ResponseEntity<List<User>> getFriendList(@RequestParam String userId) {
        List<Friendship> friendships = friendshipRepository.findByUserId(userId);
        List<User> friends = new ArrayList<>();
        for (Friendship friendship : friendships) {
            if (friendship.getStatus().equals("accepted")) {
                String friendId = friendship.getUserId1().equals(userId) ? friendship.getUserId2() : friendship.getUserId1();
                User friend = userRepository.findByPlatformId(friendId).orElse(null);
                if (friend != null) {
                    friends.add(friend);
                }
            }
        }
        return ResponseEntity.ok(friends);
    }

    @GetMapping("/getFriendRequestList")
    @Transactional
    public ResponseEntity<List<User>> getFriendRequestList(@RequestParam String userId) {
        List<Friendship> friendships = friendshipRepository.findByUserId(userId);
        List<User> friendRequests = new ArrayList<>();
        for (Friendship friendship : friendships) {
            if (friendship.getStatus().equals("pending")) {
                String friendId = friendship.getUserId1().equals(userId) ? friendship.getUserId2() : friendship.getUserId1();
                User friend = userRepository.findByPlatformId(friendId).orElse(null);
                if (friend != null) {
                    friendRequests.add(friend);
                }
            }
        }
        return ResponseEntity.ok(friendRequests);
    }
}

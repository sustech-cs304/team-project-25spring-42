package sustech.cs304.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubmissionTest {

    @Test
    void testDefaultConstructor() {
        Submission submission = new Submission();
        assertNull(submission.getId());
        assertNull(submission.getAssignmentId());
        assertNull(submission.getUserId());
        assertNull(submission.getAddress());
    }

    @Test
    void testParameterizedConstructor() {
        Long assignmentId = 100L;
        String userId = "user001";
        String address = "http://fileserver.com/submit/100";
        Submission submission = new Submission(assignmentId, userId, address);

        assertNull(submission.getId()); // id is not set by constructor
        assertEquals(assignmentId, submission.getAssignmentId());
        assertEquals(userId, submission.getUserId());
        assertEquals(address, submission.getAddress());
    }

    @Test
    void testSettersAndGetters() {
        Submission submission = new Submission();
        submission.setAssignmentId(123L);
        submission.setUserId("userABC");
        submission.setAddress("http://abc.com/file.pdf");

        assertEquals(123L, submission.getAssignmentId());
        assertEquals("userABC", submission.getUserId());
        assertEquals("http://abc.com/file.pdf", submission.getAddress());
    }

    @Test
    void testNullValue() {
        Submission submission = new Submission(null, null, null);

        assertNull(submission.getAssignmentId());
        assertNull(submission.getUserId());
        assertNull(submission.getAddress());
        assertNull(submission.getId());
    }
}
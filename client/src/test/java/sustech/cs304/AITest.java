
// package sustech.cs304;
//
// import junit.framework.TestCase;
// import junit.framework.TestSuite;
// import sustech.cs304.ai.ChatController;
//
// public class AITest extends TestCase {
//
//     private ChatController chatController;
//
//     public AITest(String testName) {
//         super(testName);
//     }
//
//     public static TestSuite suite() {
//         return new TestSuite(AITest.class);
//     }
//
//     @Override
//     protected void setUp() throws Exception {
//         super.setUp();
//         chatController = new ChatController();
//     }
//
//     public void testGetResponse_withOpenaiModel() {
//         String model = "gpt-3.5-turbo";
//         String prompt = "Hello, OpenAI! Who are you?";
//         String response = chatController.getResponse(model, prompt);
//
//         System.out.println(response);
//         assertNotNull(response);
//        // assertTrue("Response should contain 'ChatGPT'", response.contains("ChatGPT"));
//     }
//
//     public void testGetResponse_withDeepseekModel() {
//         String model = "deepseek-chat";
//         String prompt = "Hello, Deepseek! Who are you?";
//         String response = chatController.getResponse(model, prompt);
//
//         System.out.println(response);
//         assertNotNull(response);
//
//         // assertTrue("Response should contain 'Deepseek'", response.contains("Deepseek"));
//     }
//
//     public void testGetResponse_withGeminiModel() {
//         String model = "gemini-2.0-flash";
//         String prompt = "Hello, Gemini! Who are You?";
//         String response = chatController.getResponse(model, prompt);
//
//         System.out.println(response);
//         assertNotNull(response);
//
//         // assertTrue("Response should contain 'Gemini'", response.contains("Gemini"));
//     }
//
//
//     public void testGetResponse_withInvalidModel() {
//         String model = "invalid-model";
//         String prompt = "Hello!";
//         String response = chatController.getResponse(model, prompt);
//
//         assertEquals("Invalid model", response);
//     }
//
//     @Override
//     protected void tearDown() throws Exception {
//         super.tearDown();
//     }
// }

package sustech.cs304;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import sustech.cs304.ai.ChatController;

public class AITest extends TestCase {

    private ChatController chatController;

    public AITest(String testName) {
        super(testName);
    }

    public static TestSuite suite() {
        return new TestSuite(AITest.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        chatController = new ChatController();
    }

    public void testGetResponse_withOpenaiModel() {
        String model = "gpt-3.5-turbo";
        String prompt = "Hello, OpenAI! Who are you?";
        String response = chatController.getResponse(model, prompt);

        System.out.println(response);
        assertNotNull(response);
       // assertTrue("Response should contain 'ChatGPT'", response.contains("ChatGPT"));
    }

    public void testGetResponse_withDeepseekModel() {
        String model = "deepseek-chat";
        String prompt = "Hello, Deepseek! Who are you?";
        String response = chatController.getResponse(model, prompt);

        System.out.println(response);
        assertNotNull(response);

        // assertTrue("Response should contain 'Deepseek'", response.contains("Deepseek"));
    }

    public void testGetResponse_withGeminiModel() {
        String model = "gemini-2.0-flash";
        String prompt = "Hello, Gemini! Who are You?";
        String response = chatController.getResponse(model, prompt);

        System.out.println(response);
        assertNotNull(response);

        // assertTrue("Response should contain 'Gemini'", response.contains("Gemini"));
    }


    public void testGetResponse_withInvalidModel() {
        String model = "invalid-model";
        String prompt = "Hello!";
        String response = chatController.getResponse(model, prompt);

        assertEquals("Invalid model", response);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}


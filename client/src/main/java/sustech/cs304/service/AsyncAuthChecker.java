package sustech.cs304.service;

import okhttp3.*;
import sustech.cs304.utils.FileUtils;
import sustech.cs304.utils.ServerConfig;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileWriter;

/**
 * AsyncAuthChecker is a class that checks the authentication status of a user asynchronously.
 * It sends a request to the server to check if the user is logged in and handles the response accordingly.
 * If the user is logged in, it saves the user ID to a file.
 */
public class AsyncAuthChecker {
    private final OkHttpClient client;
    private final String url = ServerConfig.SERVER_URL + "/auth/callback/loginStatus";
    private volatile boolean result = false;
    private final int state;

    /**
     * Constructor for AsyncAuthChecker.
     * Initializes the OkHttpClient with a connection and read timeout.
     *
     * @param state The state to be used in the authentication check.
     */
    public AsyncAuthChecker(int state) {
        this.client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build();
        this.state = state;
    }

    /**
     * Checks the authentication status of the user.
     * It sends a request to the server and waits for a response.
     * If the user is logged in, it saves the user ID to a file.
     *
     * @return true if the user is logged in, false otherwise.
     * @throws InterruptedException if the thread is interrupted while waiting.
     */
    public boolean checkAuth() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        long startTime = System.currentTimeMillis();
        long timeoutMillis = 5 * 60 * 1000;
        Runnable pollingTask = new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                    .url(url + "/" + state)
                    .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println("Request failed: " + e.getMessage());
                        latch.countDown();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            // System.out.println("Response: " + response.body().string());
                            String[] parts = response.body().string().split(" ");
                            if ("1".equals(parts[0])){
                                System.out.println("Logged in successfully");
                                result = true;
                                String userId = parts[1];
                                System.out.println("User ID: " + userId);
                                // clear the content of the file ../../userhome/savedUserId.txt and save the userId
                                try {
                                    String projectRoot = FileUtils.getAppDataPath().toString();
                                    String filePath = projectRoot + File.separator + "savedUserId.txt";
                                    System.err.println("File path: " + filePath);
                                    FileWriter writer = new FileWriter(filePath, false);
                                    writer.write(userId);  // Write the new userId
                                    writer.close();        // Always close the writer
                                } catch (IOException e) {
                                    System.err.println("Error writing to savedUserId.txt: " + e.getMessage());
                                }
                                latch.countDown();
                            } else {
                                System.out.println("Not logged in yet, retrying...");
                                scheduleNext();
                            }
                        } else {
                            System.out.println("Unexpected code " + response);
                            scheduleNext();
                        }
                        response.close();
                        
                    }

                    public void scheduleNext() {
                        if (System.currentTimeMillis() - startTime < timeoutMillis && !result) {
                            try {
                                Thread.sleep(1000);
                                run();
                            } catch (InterruptedException e) {
                                System.out.println("Interrupted");
                                Thread.currentThread().interrupt();
                            }
                        } else if (!result) {
                            scheduleNext();
                        }
                    }
                });
            }
        };
        new Thread(pollingTask).start();
        latch.await(5,TimeUnit.MINUTES);
        return result;
    }

}

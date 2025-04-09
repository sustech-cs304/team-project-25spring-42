package sustech.cs304.login.Elements;

import okhttp3.*;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.io.FileWriter;

public class AsyncAuthChecker {
    private final OkHttpClient client;
    private final String url = "http://JingqiSUN.christmas:8080/auth/callback/loginStatus";
    private volatile boolean result = false;
    private final int state;
    
    public AsyncAuthChecker(int state) {
        this.client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build();
        this.state = state;
    }

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
                        latch.countDown();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String[] parts = response.body().string().split(" ");
                            if ("1".equals(parts[0])){
                                System.out.println("Logged in successfully");
                                result = true;
                                String userId = parts[1];
                                System.out.println("User ID: " + userId);
                                // clear the content of the file ../../userhome/savedUserId.txt and save the userId
                                try {
                                    String projectRoot = System.getProperty("user.dir");
                                    String filePath = projectRoot + "/src/main/resources/txt/savedUserId.txt";
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

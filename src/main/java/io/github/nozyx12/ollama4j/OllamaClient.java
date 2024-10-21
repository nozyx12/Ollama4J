package io.github.nozyx12.ollama4j;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Client for interacting with the Ollama API, allowing for chat exchanges with models.
 */
public class OllamaClient {
    private final String hostIP;
    private final int hostPort;

    /**
     * Constructs a client with the default host IP (127.0.0.1) and port (11434).
     */
    public OllamaClient() {
        this.hostIP = "127.0.0.1";
        this.hostPort = 11434;
    }

    /**
     * Constructs a client with a specified host IP and port.
     *
     * @param hostIP   the IP address of the host
     * @param hostPort the port number of the host
     */
    public OllamaClient(String hostIP, int hostPort) {
        this.hostIP = hostIP;
        this.hostPort = hostPort;
    }

    /**
     * Sends a chat request to the Ollama API and returns the response.
     *
     * @param model    the name of the model to be used for the chat
     * @param messages the list of messages to send to the model
     * @return the response message from the API
     * @throws Exception if the API request fails
     */
    public Message chat(String model, List<Message> messages) throws Exception {
        return this.chat(model, messages, 0.7f);
    }

    /**
     * Sends a chat request to the Ollama API with a specified temperature and returns the response.
     *
     * @param model       the name of the model to be used for the chat
     * @param messages    the list of messages to send to the model
     * @param temperature the temperature setting for the model (controls response creativity)
     * @return the response message from the API
     * @throws Exception if the API request fails
     */
    public Message chat(String model, List<Message> messages, float temperature) throws Exception {
        URL url = new URL("http://" + this.hostIP + ":" + this.hostPort + "/api/chat");
        OkHttpClient client = new OkHttpClient();

        JSONObject obj = new JSONObject();
        obj.put("model", model);

        JSONArray messagesArray = new JSONArray();

        for (Message message : messages) {
            JSONObject messageObj = new JSONObject();
            messageObj.put("role", message.getRole());
            messageObj.put("content", message.getContent());

            if (!message.getImages().isEmpty()) {
                JSONArray imagesArray = new JSONArray();

                for (BufferedImage image : message.getImages()) {
                    byte[] imageBytes;
                    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                        ImageIO.write(image, "png", baos);

                        baos.flush();
                        imageBytes = baos.toByteArray();
                    }

                    imagesArray.put(Base64.getEncoder().encodeToString(imageBytes));
                }

                messageObj.put("images", imagesArray);
            }

            messagesArray.put(messageObj);
        }

        obj.put("messages", messagesArray);
        obj.put("stream", false);

        JSONObject optionsObj = new JSONObject();
        optionsObj.put("temperature", temperature);

        obj.put("options", optionsObj);

        String jsonMessage = obj.toString();

        RequestBody body = RequestBody.create(jsonMessage, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (okhttp3.Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                JSONObject object = new JSONObject(response.body().string());

                List<BufferedImage> images = new ArrayList<>();

                try {
                    object.getJSONObject("message").getJSONArray("images");

                    for (Object o : object.getJSONObject("message").getJSONArray("images")) {
                        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(o.toString()));
                        BufferedImage image = ImageIO.read(bais);

                        images.add(image);
                    }
                } catch (JSONException ignored) {}

                return new Message("assistant", object.getJSONObject("message").getString("content"), images);
            } else throw new Exception("Ollama API request failed with code: " + response.code());
        }
    }
}

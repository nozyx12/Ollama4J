package io.github.nozyx12.ollama4j;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a message exchanged between a user and the API.
 * The message contains the role, content, and optionally, a list of images.
 */
public class Message {
    /**
     * The role of the message sender (e.g., "user" or "assistant").
     */
    private final String role;

    /**
     * The content of the message.
     */
    private final String content;

    /**
     * The list of images associated with the message.
     */
    private final List<BufferedImage> images;

    /**
     * Constructs a message with a specified role and content, with no images.
     *
     * @param role    the role of the message sender
     * @param content the content of the message
     */
    public Message(String role, String content) {
        this.role = role;
        this.content = content;
        this.images = new ArrayList<>();
    }

    /**
     * Constructs a message with a specified role, content, and a list of images.
     *
     * @param role    the role of the message sender
     * @param content the content of the message
     * @param images  the list of images associated with the message
     */
    public Message(String role, String content, List<BufferedImage> images) {
        this.role = role;
        this.content = content;
        this.images = images;
    }

    /**
     * Gets the role of the message sender.
     *
     * @return the role of the message sender
     */
    public String getRole() {
        return this.role;
    }

    /**
     * Gets the content of the message.
     *
     * @return the content of the message
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Gets the list of images associated with the message.
     *
     * @return the list of images
     */
    public List<BufferedImage> getImages() {
        return this.images;
    }
}

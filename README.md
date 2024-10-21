# Ollama4J

**Ollama4J** is a Java client library for interacting with the [Ollama API](https://ollama.ai). It allows you to easily send chat messages to various AI models hosted on an Ollama server and receive responses, including support for images.

## Features

- Send and receive chat messages with AI models.
- Support for sending and receiving images in messages.
- Configurable API host and port settings.
- Customizable temperature for model responses (controls creativity).

## Installation

For Maven:
```xml
<dependency>
    <groupId>io.github.nozyx12</groupId>
    <artifactId>ollama4j</artifactId>
    <version>1.0</version> <!-- Update with the latest version -->
</dependency>
```

For Gradle (Groovy DSL):
```groovy
implementation "io.github.nozyx12:ollama4j:1.0" // Update with the latest version
```

For Gradle (Kotlin DSL):
```kotlin
implementation("io.github.nozyx12:ollama4j:1.0") // Update with the latest version
```

# Usage
1. Basic Chat Example

### Here’s an example of how to use Ollama4J to send a basic text message to an AI model:

```java
import io.github.nozyx12.ollama4j.Message;
import io.github.nozyx12.ollama4j.OllamaClient;

import java.util.ArrayList;
import java.util.List;

public class Example {
    public static void main(String[] args) throws Exception {
        // Initialize Ollama client with default settings (localhost:11434)
        OllamaClient client = new OllamaClient();

        // Create a list of messages to send
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("user", "Hello, how are you?"));

        // Send chat request and get the response
        Message response = client.chat("llama3.2", messages);

        // Output the response content
        System.out.println(response.getContent());
    }
}
```

### Sending Images

Ollama4J also supports sending and receiving images in your messages. Here’s an example:

```java
import io.github.nozyx12.ollama4j.Message;
import io.github.nozyx12.ollama4j.OllamaClient;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageExample {
    public static void main(String[] args) throws Exception {
        // Initialize Ollama client
        OllamaClient client = new OllamaClient();

        // Load an image from file
        BufferedImage image = ImageIO.read(new File("example.png"));

        // Create a list of messages including the image
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("user", "Can you analyze this image?", List.of(image)));

        // Send the chat request
        Message response = client.chat("llava", messages);

        // Output the response content
        System.out.println(response.getContent());
    }
}
```

### Setting a system prompt

Ollama API supports setting a system prompt, here's an example with Ollama4J:

```java
import io.github.nozyx12.ollama4j.Message;
import io.github.nozyx12.ollama4j.OllamaClient;

import java.util.ArrayList;
import java.util.List;

public class Example {
    public static void main(String[] args) throws Exception {
        // Initialize Ollama client with default settings (localhost:11434)
        OllamaClient client = new OllamaClient();

        // Create a list of messages to send
        List<Message> messages = new ArrayList<>();
        
        messages.add(new Message("system", "You're an helpful assistant.")); // Add the system prompt message
        messages.add(new Message("user", "Hello, how are you?"));

        // Send chat request and get the response
        Message response = client.chat("llama3.2", messages);

        // Output the response content
        System.out.println(response.getContent());
    }
}
```

### Customizing Host and Port

By default, Ollama4J connects to localhost on port 11434. You can specify a different host and port as follows:

```java
OllamaClient client = new OllamaClient("192.168.1.100", 12345);
```
### Adjusting Temperature

The temperature parameter controls the creativity of the model’s responses. A higher temperature results in more creative responses, while a lower temperature makes the model more focused and deterministic. The default temperature is 0.7.

To set a custom temperature:

```java
Message response = client.chat("llama3.2", messages, 0.5f);
```

## Requirements

- Java 8 or higher
- [OkHttp](https://central.sonatype.com/artifact/com.squareup.okhttp3/okhttp/) for HTTP requests.
- [JSON](https://central.sonatype.com/artifact/org.json/json/) for handling JSON serialization.

## Contributing

Contributions are welcome! If you’d like to contribute to this project, feel free to submit a pull request or report an issue.
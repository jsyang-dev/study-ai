package me.study.ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/ai")
    public String generation(@RequestParam(defaultValue = "Tell me a joke") String message,
                             @RequestParam(defaultValue = "Pirate") String voice) {
        return chatClient.prompt()
                .system(sp -> sp.param("voice", voice))
                .user(message)
                .call()
                .content();
    }

    @GetMapping("/entity")
    public ActorFilms entity() {
        return chatClient.prompt()
                .user("Generate the filmography for a random actor.")
                .call()
                .entity(ActorFilms.class);
    }

    @GetMapping("/entities")
    public List<ActorFilms> entities() {
        return chatClient.prompt()
                .user("Generate the filmography of 5 movies for Tom Hanks and Bill Murray.")
                .call()
                .entity(new ParameterizedTypeReference<>() {});
    }

    @GetMapping("/stream")
    public List<ActorFilms> stream() {
        BeanOutputConverter<List<ActorFilms>> converter = new BeanOutputConverter<>(new ParameterizedTypeReference<>() {});

        Flux<String> flux = chatClient.prompt()
                .user(u -> u.text("""
                        Generate the filmography for a random 2 actors.
                        {format}
                      """)
                        .param("format", converter.getFormat()))
                .stream()
                .content();

        String content = flux.collectList().block().stream().collect(Collectors.joining());

        return converter.convert(content);
    }
}

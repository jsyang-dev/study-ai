package me.study.ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/ai")
    public String simpleUserMessage(@RequestParam String input) {
        return chatClient.prompt()
                .user(input)
                .call()
                .content();
    }

    @GetMapping("/capital1")
    public String withSystem() {
        return chatClient.prompt()
                .system("You are a helpful AI")
                .user("What is the capital of France?")
                .call()
                .content();
    }
}

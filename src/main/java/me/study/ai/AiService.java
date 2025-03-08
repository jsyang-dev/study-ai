package me.study.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

@Service
public class AiService {
	private final ChatModel chatModel;

	public AiService(ChatModel chatModel) {
		this.chatModel = chatModel;
	}

	public ActorsFilms getActorsFilms() {
		return ChatClient.create(chatModel).prompt()
			.user(u -> u.text("Generate the filmography of 5 movies for {actor}.")
				.param("actor", "Tom Hanks"))
			.call()
			.entity(ActorsFilms.class);
	}
}
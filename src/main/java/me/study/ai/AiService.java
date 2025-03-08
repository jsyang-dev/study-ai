package me.study.ai;

import java.util.Map;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

@Service
public class AiService {
	private final ChatModel chatModel;

	public AiService(ChatModel chatModel) {
		this.chatModel = chatModel;
	}

	public ActorsFilms getActorsFilms() {
		BeanOutputConverter<ActorsFilms> beanOutputConverter = new BeanOutputConverter<>(ActorsFilms.class);

		String format = beanOutputConverter.getFormat();
		String actor = "Tom Hanks";
		String template = """
			Generate the filmography of 5 movies for {actor}.
			{format}
			""";

		Prompt prompt = new PromptTemplate(template, Map.of("actor", actor, "format", format)).create();
		Generation generation = chatModel.call(prompt).getResult();
		return beanOutputConverter.convert(generation.getOutput().getText());
	}
}
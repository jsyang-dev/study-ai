package me.study.ai;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AiController {
	private final AiService aiService;

	public AiController(AiService aiService) {
		this.aiService = aiService;
	}

	@GetMapping
	public ResponseEntity<ActorsFilms> getActorsFilms() {
		return ResponseEntity.ok(aiService.getActorsFilms());
	}
}
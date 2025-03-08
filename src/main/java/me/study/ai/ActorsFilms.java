package me.study.ai;

import java.util.List;
import java.util.Objects;

public class ActorsFilms {
	private final String actor;
	private final List<String> movies;

	public ActorsFilms(String actor, List<String> movies) {
		this.actor = actor;
		this.movies = movies;
	}

	public String getActor() {
		return actor;
	}

	public List<String> getMovies() {
		return movies;
	}

	@Override
	public final boolean equals(Object o) {
		if (!(o instanceof ActorsFilms that)) {
			return false;
		}

		return Objects.equals(actor, that.actor) && Objects.equals(movies, that.movies);
	}

	@Override
	public int hashCode() {
		int result = Objects.hashCode(actor);
		result = 31 * result + Objects.hashCode(movies);
		return result;
	}
}
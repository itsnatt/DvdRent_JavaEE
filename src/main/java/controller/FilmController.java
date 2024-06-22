package controller;

import model.Film;
import service.FilmService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named("filmController")
@RequestScoped
public class FilmController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private FilmService filmService;

    public List<Film> getFilmsByIds(List<Integer> filmIds) {
        return filmIds.stream()
                .map(filmService::findById)
                .collect(Collectors.toList());
    }

    public String getFilmTitleById(int filmId) {
        Film film = filmService.findById(filmId);
        return film != null ? film.getTitle() : "Unknown";
    }

    // Method to retrieve all films
    public List<Film> getAllFilms() {
        return filmService.findAll();
    }

    public FilmService getFilmService() {
        return filmService;
    }

    public void setFilmService(FilmService filmService) {
        this.filmService = filmService;
    }
}

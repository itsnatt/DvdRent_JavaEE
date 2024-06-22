package controller;

import model.Actor;
import model.Film;
import model.FilmActor;
import model.FilmActorId;
import service.ActorService;
import service.FilmService;
import service.FilmActorService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named("filmActorController")
@RequestScoped
public class FilmActorController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private FilmActorService filmActorService;
    @Inject
    private ActorService actorService;
    @Inject
    private FilmService filmService;

    private List<FilmActor> filmActors;
    private Integer selectedActorId;
    private Integer selectedFilmId;

    // Asumsi bahwa ActorService dan FilmService menggunakan Integer untuk ID
//    public void addFilmActor() {
//        // Mengubah pencarian berdasarkan Integer
//        Actor actor = actorService.findById(selectedActorId);
//        Film film = filmService.findById(selectedFilmId);
//
//        FilmActorId filmActorId = new FilmActorId(selectedActorId, selectedFilmId);
//        Date lastUpdate = new Date();
//        FilmActor newFilmActor = new FilmActor(filmActorId, actor, film, lastUpdate);
//
//        filmActorService.addFilmActor(newFilmActor);
//    }

    // Implementasi getFilmIdsForActor yang disesuaikan dengan tipe data yang benar
    public List<Short> getFilmIdsForActor(Integer actorId) {
        return filmActorService.findFilmIdsByActorId(actorId.shortValue());
    }

    // Getter dan Setter
    public Integer getSelectedActorId() {
        return selectedActorId;
    }

    public void setSelectedActorId(Integer selectedActorId) {
        this.selectedActorId = selectedActorId;
    }

    public Integer getSelectedFilmId() {
        return selectedFilmId;
    }

    public void setSelectedFilmId(Integer selectedFilmId) {
        this.selectedFilmId = selectedFilmId;
    }

    // Metode lainnya jika diperlukan...
}

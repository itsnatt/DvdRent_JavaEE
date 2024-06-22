package controller;

import model.Actor;
import service.ActorService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("actorController")
@ViewScoped
public class ActorController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ActorService actorService;

    private Actor newActor = new Actor();
    private List<Actor> actors;
    private Actor selectedActor;

    @PostConstruct
    public void init() {
        actors = actorService.findAll();
    }

    public List<Actor> getActors() {
        return actors;
    }

    public Actor getSelectedActor() {
        return selectedActor;
    }

    public Actor getNewActor() {
        return newActor;
    }

    public void setNewActor(Actor newActor) {
        this.newActor = newActor;
    }

    public void setSelectedActor(Actor selectedActor) {
        this.selectedActor = selectedActor;
        System.out.println("Selected Actor ID: " + (selectedActor != null ? selectedActor.getActorId() : "null"));
    }

    public void deleteActor(int actorId) {
        System.out.println("Deleting actor with ID: " + actorId);
        try {
            actorService.delete(actorId);
            actors = actorService.findAll(); // Refresh list
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Actor Deleted");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Actor could not be deleted: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            e.printStackTrace();
        }
    }

    public void prepareEdit(Actor actor) {
        this.selectedActor = actor;
        System.out.println("Preparing edit for actor: " + actor.getActorId() + " - " + actor.getFirstName() + " " + actor.getLastName());
    }

    public void prepareDelete(Actor actor) {
        this.selectedActor = actor;
        System.out.println("Preparing to delete actor with ID: " + actor.getActorId());
    }

     public void addNewActor() {
        try {
            actorService.saveOrUpdate(newActor);
            actors = actorService.findAll(); // Refresh the list
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Actor Added Successfully"));
            newActor = new Actor(); // Reset for the next entry
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error adding actor: " + e.getMessage()));
            e.printStackTrace();
        }
    }

    // Method to update an existing actor
    public void saveActor() {
        try {
            actorService.saveOrUpdate(selectedActor);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Actor Updated Successfully"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error updating actor: " + e.getMessage()));
            e.printStackTrace();
        }
    }   
    
}

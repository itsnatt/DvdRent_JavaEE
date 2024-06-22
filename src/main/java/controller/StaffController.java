package controller;

import model.Staff;
import service.StaffService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("staffController")
@SessionScoped
public class StaffController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private StaffService staffService;

    private Staff newStaff = new Staff();
    private List<Staff> staffList;
    private Staff selectedStaff;

    @PostConstruct
    public void init() {
        staffList = staffService.findAll();
    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    public Staff getNewStaff() {
        return newStaff;
    }

    public void setNewStaff(Staff newStaff) {
        this.newStaff = newStaff;
    }

    public Staff getSelectedStaff() {
        return selectedStaff;
    }

    public void setSelectedStaff(Staff selectedStaff) {
        this.selectedStaff = selectedStaff;
    }

    public void addNewStaff() {
        try {
            staffService.saveOrUpdate(newStaff);
            staffList = staffService.findAll(); // Refresh the list
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Staff Added Successfully"));
            newStaff = new Staff(); // Reset for the next entry
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error adding staff: " + e.getMessage()));
            e.printStackTrace();
        }
    }

    public void deleteStaff() {
        try {
            staffService.delete(selectedStaff.getStaffId());
            staffList = staffService.findAll(); // Refresh the list
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Staff Deleted Successfully"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error deleting staff: " + e.getMessage()));
            e.printStackTrace();
        }
    }

    public void saveStaff() {
        try {
            staffService.saveOrUpdate(selectedStaff);
            staffList = staffService.findAll(); // Refresh the list
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Staff Updated Successfully"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error updating staff: " + e.getMessage()));
            e.printStackTrace();
        }
    }

    public void prepareEdit(Staff staff) {
        this.selectedStaff = staff;
    }

    public void prepareDelete(Staff staff) {
        this.selectedStaff = staff;
    }
}

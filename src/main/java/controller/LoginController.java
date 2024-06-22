package controller;

import model.Customer;
import model.Staff;
import service.AuthenticationService;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.IOException;
import java.io.Serializable;

@Named("loginController")
@RequestScoped
public class LoginController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private AuthenticationService authService;

    private String email;
    private String password;
    private Object loggedInUser;
    private String loginame;


    public String login() {
    	loggedInUser = authService.authenticate(email, password);
    	FacesContext facesContext = FacesContext.getCurrentInstance();
    	Flash flash = facesContext.getExternalContext().getFlash();
    	flash.setKeepMessages(true);

    	if (loggedInUser != null) {
    	    String welcomeMessage;
    	    if (loggedInUser instanceof Customer) {
    	        Customer customer = (Customer) loggedInUser;
    	        setloginname(customer.getFirstName());
    	        welcomeMessage = "Selamat Datang, " + customer.getFirstName();
    	        facesContext.getExternalContext().getSessionMap().put("loggedInUser", customer);
    	    } else {
    	        Staff staff = (Staff) loggedInUser;
    	        setloginname(staff.getFirstName());
    	        welcomeMessage = "Selamat Datang, Admin " + staff.getFirstName();
    	        facesContext.getExternalContext().getSessionMap().put("loggedInUser", staff);
    	    }
    	    flash.put("loginMessage", welcomeMessage);
    	    return loggedInUser instanceof Customer ? "home?faces-redirect=true" : "staffHome?faces-redirect=true";
    	} else {
    	    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Failed", "Invalid email or password."));
    	    return "login?faces-redirect=true";
    	}
   }


   public void displayFlashMessage() {
    Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    String message = (String) flash.get("loginMessage");
    if (message != null) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }
}
  
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("loggedInUser");
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index?faces-redirect=true";
    }
    
    public void checkLogin() throws IOException {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInUser") == null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml"); // Ganti "login.xhtml" dengan path halaman login yang benar
        }
    }
    

    public void showMustLoginMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
    
        // Cek apakah sudah ada pesan di queue.
        boolean messageAlreadyQueued = context.getMessages(null).hasNext();
        
        if (!messageAlreadyQueued && context.getExternalContext().getSessionMap().get("loggedInUser") == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Akses Ditolak", "Silahkan login terlebih dulu!"));
        }
    }
       

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Object loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    
    public void setloginname(String loginame) {
        this.loginame = loginame;
    }
    
    // Getters and Setters
    public String getloginname() {
        return loginame;
    }
}

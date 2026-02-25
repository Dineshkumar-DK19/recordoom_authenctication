package com.recordroom.recordroom;

import com.recordroom.recordroom.dashbord.DashboardService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private DashboardService dashboardService;

    // Displays the login page and handles error messages passed via URL parameters
    @GetMapping("/homepage")
    public String homepage(@RequestParam(value = "error", required = false) String error, Model model) {
        if ("invalid_credentials".equals(error)) {
            model.addAttribute("errorMessage", "Incorrect username or password.");
        }
        return "layout/outsidelayout";
    }

    // Protected Dashboard Route: Redirects to login if no "user" attribute exists in the session
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("com/recordroom/recordroom/user") == null) {
            return "redirect:/homepage";
        }
        model.addAttribute("stats", dashboardService.getDashboardStats());
        return "layout/insidelayout";
    }



    // Logout Logic: Terminates the session and redirects to login
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) throws ServletException {
        session.invalidate(); // Destroys the current user session

        HttpHeaders headers = new HttpHeaders();
        headers.set("HX-Redirect", "/record/homepage");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid Username or Password"); // Used in login.html
        }
        return "pages/index";
    }

    @GetMapping("/profile")
    public String profileFragment(Model model) {
        return "fragments/table :: content";
    }

    // Example of another protected fragment route
    @GetMapping("/summaryreport")
    public String summaryreport(HttpSession session, Model model) {
        if (session.getAttribute("com/recordroom/recordroom/user") == null) return "redirect:/homepage";
        model.addAttribute("stats", dashboardService.getDashboardStats());
        return "fragments/summary_report :: tabler";
    }

    @GetMapping("/outWardReport")
    public String showReport(HttpSession session, Model model) {
        if (session.getAttribute("com/recordroom/recordroom/user") == null) return "redirect:/homepage";
        return "fragments/closed/out_entry_report :: tabler";
    }
}
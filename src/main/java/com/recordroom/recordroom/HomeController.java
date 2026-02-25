package com.recordroom.recordroom;

import com.recordroom.recordroom.dashbord.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "logout", required = false) String logout, Model model) {
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out successfully.");
        }
        return "layout/outsidelayout";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("stats", dashboardService.getDashboardStats());
        return "layout/insidelayout";
    }
    @GetMapping("/profile")
    public String profileFragment(Model model) {
        return "fragments/table :: content";
    }

    @GetMapping("/summaryreport")
    public String summaryreport(Model model) {
        model.addAttribute("stats", dashboardService.getDashboardStats());
        return "fragments/summary_report :: tabler";
    }

    @GetMapping("/outWardReport")
    public String showReport(Model model) {
        return "fragments/closed/out_entry_report :: tabler";
    }
}
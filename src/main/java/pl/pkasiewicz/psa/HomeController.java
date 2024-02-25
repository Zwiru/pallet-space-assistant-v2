package pl.pkasiewicz.psa;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private final CalculateService calculateService;

    public HomeController(CalculateService calculateService) {
        this.calculateService = calculateService;
    }

    @GetMapping("/")
    String home() {
        return "index";
    }

    @PostMapping("/calculate")
    String calculate(@RequestParam(required = false) double euro,
                     @RequestParam(required = false) double j7,
                     @RequestParam(required = false) double hg5,
                     @RequestParam(required = false) double gm6,
                     Model model) {
        model.addAttribute("leftSpaceForPallets", calculateService.calculateRemainingSpaceForSpecificPallets(euro, j7, hg5, gm6));
        return "index";
    }
}

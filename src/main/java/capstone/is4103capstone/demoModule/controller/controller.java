package capstone.is4103capstone.demoModule.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


//restful webservice template
@RestController
public class controller {
  @GetMapping("/greeting")
  public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
    model.addAttribute("name", name);
    return "greeting";
  }

  @Value("${test.fetch.property}")
  private String dbUrl;
  public String getDbUrl(){
    return dbUrl;
  }

}

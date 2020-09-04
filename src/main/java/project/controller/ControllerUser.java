package main.java.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import main.java.project.entities.Account;
import main.java.project.entities.Entity;
import main.java.project.entities.MyUser;
import main.java.project.service.MyUserServiceImp;

import java.util.ArrayList;
import java.util.List;
@Controller
public class ControllerUser {
    private MyUserServiceImp myUserService;

    @Autowired
    public ControllerUser(MyUserServiceImp myUserService) {

        this.myUserService = myUserService;
    }

    @GetMapping("/admin/userShow")
    public String getEntity(Model model) {
        List<Entity> myUserList = myUserService.showAllEntity();
        model.addAttribute("myUsers",myUserList);
        return "accountShow";
    }

    @PostMapping("/admin/addUser")
    public String addEntity(@RequestParam(value = "name") String name,
                            @RequestParam(value = "lastName") String lastName,
                            @RequestParam(value = "phone") String phone,
                            @RequestParam(value = "login") String login,
                            @RequestParam(value = "password") String password,
                            Model model) {
        MyUser myUser = new MyUser();
        myUser.setName(name);
        myUser.setLastName(lastName);
        myUser.setPhone(phone);
        Account account = new Account();
        account.setName(login);
        account.setPassword(password);
        myUser.setAccount(account);
        myUserService.setMyUser(myUser);
        myUserService.addEntity();
        List<Entity> myUserList = myUserService.showAllEntity();
        model.addAttribute("myUsers",myUserList);
        return "accountShow";
    }
    /**
     *Метод удаления и редактирования администраторов
     */
    @GetMapping("/admin/User/{action}/{id}")
    public String delCategories(@PathVariable(value= "id") int id,
                                @PathVariable(value = "action") String action,
                                Model model){
        MyUser myUser;
        List<Entity> myUsersList = new ArrayList<>();
        if(id != 1) { //ограничение на удаление первого администратора
            if (action.equals("del")) {
                myUserService.deleteEntity(id);
                myUsersList = myUserService.showAllEntity();
                model.addAttribute("myUsers", myUsersList);
            }
            if (action.equals("upDate")) {
                myUser = (MyUser) myUserService.getEntityID(id);
                myUsersList.add(myUser);
                model.addAttribute("myUsers", myUsersList);
                model.addAttribute("upDate", true);
            }
        }
        else{
            myUsersList = myUserService.showAllEntity();
            model.addAttribute("myUsers", myUsersList);
        }
        return "accountShow";
    }

    @PostMapping("/admin/updateUser/{id}")
    public String updateCategory(@PathVariable(value = "id")int id,
                                 @RequestParam(value = "name", defaultValue = "") String name,
                                 @RequestParam(value = "lastName", defaultValue = "") String lastName,
                                 @RequestParam(value = "phone", defaultValue = "") String phone,
                                 @RequestParam(value = "login", defaultValue = "") String login,
                                 @RequestParam(value = "password", defaultValue = "") String password,
                                 Model model){
        MyUser myUser;
        MyUser myUserUpDate = new MyUser();
        myUser  = (MyUser) myUserService.getEntityID(id);
        Account accountUpDate = new Account();
        if (name.equals("")){
            myUserUpDate.setName(myUser.getName());
        }
        else {
            myUserUpDate.setName(name);
        }
        if (lastName.equals("")){
            myUserUpDate.setLastName(myUser.getLastName());
        }
        else {
            myUserUpDate.setLastName(lastName);
        }
        if (phone.equals("")) {
            myUserUpDate.setPhone(myUser.getPhone());
        }
        else {
            myUserUpDate.setPhone(phone);
        }
        if (login.equals("")){
            accountUpDate.setName(myUser.getAccount().getName());
        }
        else {
            accountUpDate.setName(login);
        }
        if(password.equals("")){
            accountUpDate.setPassword(myUser.getAccount().getPassword());
        }
        else accountUpDate.setPassword(password);
        myUserUpDate.setAccount(accountUpDate);
        myUserService.setMyUser(myUserUpDate);
        myUserService.upDateEntity(id);
        List<Entity> res = myUserService.showAllEntity();
        model.addAttribute("myUsers",res);
        return "accountShow";
    }
}

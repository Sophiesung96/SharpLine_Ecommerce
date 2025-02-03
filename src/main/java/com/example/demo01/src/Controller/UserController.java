package com.example.demo01.src.Controller;

import com.example.demo01.src.Configuration.Exporter.UserCsvExporter;
import com.example.demo01.src.Configuration.Exporter.UserExcelExporter;
import com.example.demo01.src.Configuration.Exporter.UserPDFExporter;
import com.example.demo01.src.Configuration.Utils.FileUploadUtil;
import com.example.demo01.src.Pojo.AmazonS3Util;
import com.example.demo01.src.Pojo.Role;
import com.example.demo01.src.Pojo.Users;
import com.example.demo01.src.Pojo.UsersRole;
import com.example.demo01.src.Service.RoleService;
import com.example.demo01.src.Service.UserRoleService;
import com.example.demo01.src.Service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Log
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    AmazonS3Util amazonS3Util;



    @GetMapping("/users/{pageno}")
    public String listAllUser(@PathVariable(name = "pageno") int pageno, Model model, HttpSession session) throws IOException {
        List<Users> list = new ArrayList<>();
        //get the full list which is sorted by pagination
        list = userService.getUserByPagination(pageno);
        List<Integer> mylist = new ArrayList<>();
        //get the total page number for listing all users
        mylist = userService.getCountByUser();
        model.addAttribute("currentPage", pageno);
        model.addAttribute("total", mylist);
        List<UsersRole> title = new ArrayList<>();
        title = userService.getUserRole();
        for (int i = 0; i < title.size(); i++) {
            String description = title.get(i).getName();
            for (int j = 0; j < list.size(); j++) {
                if (i == j) {
                    Users user = list.get(i);
                    user.setUsersRole(description);


                }
                model.addAttribute("listuser", list);
            }

        }


        return ("userlist");
    }

    // Create new user
    @GetMapping("/user/new")
    public String createUser(HttpSession session) {
        List<Role> list = new ArrayList<>();
        //get roleList
        list = userService.getAllRole();
        session.setAttribute("rolelist", list);
        return "UserForm";
    }

    //Direct to create new user form page
    @PostMapping("/user/save")
    public String saveUser(@ModelAttribute Users user, HttpSession session, @RequestParam("image") MultipartFile multipartFile) {
        if (user.getEnabled() == 0) {
            user.setEnabled(0);
        }
        // Create new user
        userService.CreateUser(user);

        Users newuser = new Users();
        /** After creating a new user, one needs to get the Users object, which previously created
         * by calling and putting user's first_name
         * as a parameter into
         * userService's getUserIdByName() and
         * Then get userid by calling User's getId().
         */
        newuser = userService.getUserIdByName(user.getFirst_name());
        newuser.getId();
        // Get RoleId
        String userRole = user.getUsersRole();
        // Create a String array by using coma as a split point
        // in the String userRole
        String[] userRoleArray = userRole.split(",");
        for (String role : userRoleArray) {
            int roleId = Integer.parseInt(role);
            userRoleService.insertUserIdnRoleId(newuser.getId(), roleId);
        }

        //Get user's photo and store it in local file in numerical order
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhoto(fileName);
            String uploadDir = "user-pics" + File.separator + newuser.getId();
            try {
                amazonS3Util.removeFolder(uploadDir);
                amazonS3Util.uploadFile(uploadDir,fileName,multipartFile.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String message = "The user has been saved successfully!";

        session.setAttribute("message", message);
        return "redirect:/users/1";
    }

    //Edit user info by userid
    @RequestMapping("/users/edit/{id}")
    public String editUser(@PathVariable Integer id, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        try {
            Users user = userService.ReadByUid(id);
            model.addAttribute("user", user);
            List<Role> list = new ArrayList<>();
            //get roleList
            list = userService.getAllRole();
            session.setAttribute("rolelist", list);

            return "EditUser";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("message", e.getMessage());
            return "redirect:/users/1";
        }
    }


    //update user info that has been edited
    @RequestMapping("/users/edit")
    public String updateUser(@ModelAttribute Users user) {

        userService.UpdateUser(user);
        return "redirect:/users/1";
    }

    @RequestMapping("/delete/{id}")
    public String DeleteUser(@PathVariable Integer id, RedirectAttributes redirectAttributes) {

        userService.DeleteUserById(id);
        String userPhotoDir="user-photos/"+id;
        amazonS3Util.removeFolder(userPhotoDir);
        redirectAttributes.addFlashAttribute("message","The user ID"+id+"has been deleted successfully");

        return "redirect:/users";
    }

    @RequestMapping("/update/Enabledstatus/{id}/{enabledStatus}")
    public String UpdateEnabledStatus(@PathVariable Integer id, @PathVariable String enabledStatus) {
        int enabled = 0;
        if (enabledStatus.equals("true")) {
            enabled=0;
            userService.UpdateEnabledStatus(id, enabled);
        } else {
            enabled = 1;
            userService.UpdateEnabledStatus(id, enabled);

        }


        return "redirect:/users/1";
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }

    @GetMapping("users/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<Users> list = new ArrayList<>();
        list = userService.listAll();
        List<UsersRole> title = new ArrayList<>();
        title = userService.getUserRole();
        for (int i = 0; i < title.size(); i++) {
            String description = title.get(i).getName();
            for (int j = 0; j < list.size(); j++) {
                if (i == j) {
                    Users user = list.get(i);
                    user.setUsersRole(description);
                }
            }
        }
        UserCsvExporter userCsvExporter = new UserCsvExporter();
        userCsvExporter.export(list, response);


    }

    @GetMapping("users/export/excel")
    public void exportToExcel(HttpServletResponse response) {
        List<Users> list = new ArrayList<>();
        list = userService.listAll();
        List<UsersRole> title = new ArrayList<>();
        title = userService.getUserRole();
        for (int i = 0; i < title.size(); i++) {
            String description = title.get(i).getName();
            for (int j = 0; j < list.size(); j++) {
                if (i == j) {
                    Users user = list.get(i);
                    user.setUsersRole(description);


                }
            }
        }
        UserExcelExporter userExcelExporter = new UserExcelExporter();
        try {
            userExcelExporter.export(list, response);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @GetMapping("users/export/pdf")
    public void exportToPdf(HttpServletResponse response) throws IOException {
        List<Users> list = new ArrayList<>();
        list = userService.listAll();
        List<UsersRole> title = new ArrayList<>();
        title = userService.getUserRole();
        for (int i = 0; i < title.size(); i++) {
            String description = title.get(i).getName();
            for (int j = 0; j < list.size(); j++) {
                if (i == j) {
                    Users user = list.get(i);
                    user.setUsersRole(description);


                }
            }
        }
        UserPDFExporter userPDFExporter = new UserPDFExporter();
        userPDFExporter.export(list, response);

    }
}

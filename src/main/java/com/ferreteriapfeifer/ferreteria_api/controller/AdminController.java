package com.ferreteriapfeifer.ferreteria_api.controller;

import com.ferreteriapfeifer.ferreteria_api.model.Admin;
import com.ferreteriapfeifer.ferreteria_api.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public String register(@RequestBody Admin admin) throws ExecutionException, InterruptedException {
        adminService.register(admin);
        return "Admin registrado.";
    }

    @GetMapping
    public List<Admin> getAll() throws ExecutionException, InterruptedException {
        List<Admin> admins = adminService.getAll();
        return admins;
    }

    @GetMapping("/{id}")
    public Admin get(@PathVariable String id) throws ExecutionException, InterruptedException {
        Admin admin = adminService.get(id);
        return admin;
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) throws ExecutionException, InterruptedException {
        adminService.delete(id);
        return "Admin eliminado.";
    }


}

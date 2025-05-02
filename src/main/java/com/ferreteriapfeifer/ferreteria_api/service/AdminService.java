package com.ferreteriapfeifer.ferreteria_api.service;


import com.ferreteriapfeifer.ferreteria_api.model.Admin;
import com.ferreteriapfeifer.ferreteria_api.model.Persona;
import com.ferreteriapfeifer.ferreteria_api.model.Producto;
import com.ferreteriapfeifer.ferreteria_api.repository.AdminRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class AdminService {

    private final AdminRepository adminRepository;


    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void register(Admin admin) throws ExecutionException, InterruptedException {
        admin.setIdAdmin(UUID.randomUUID().toString());
        adminRepository.register(admin);
    }

    public Admin get(String adminId) throws ExecutionException, InterruptedException {
        return adminRepository.get(adminId);
    }

    public List<Admin> getAll() throws ExecutionException, InterruptedException {
        return adminRepository.getAll();
    }

    public void delete(String clientId) throws ExecutionException, InterruptedException {
        adminRepository.delete(clientId);
    }


    public void modificarStockSiEsAdmin(Persona usuario, Producto producto, int cantidad) {
        if (usuario instanceof Admin) {
            producto.modificarStock(cantidad);
        } else {
            throw new SecurityException("Solo los administradores pueden modificar el stock.");
        }
    }
}

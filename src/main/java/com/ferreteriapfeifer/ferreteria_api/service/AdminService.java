package com.ferreteriapfeifer.ferreteria_api.service;


import com.ferreteriapfeifer.ferreteria_api.dto.AdminDTO;
import com.ferreteriapfeifer.ferreteria_api.model.*;
import com.ferreteriapfeifer.ferreteria_api.repository.AdminRepository;
import com.ferreteriapfeifer.ferreteria_api.util.PasswordUtil;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordUtil passwordUtil;

    public AdminService(AdminRepository adminRepository, PasswordUtil passwordUtil) {
        this.adminRepository = adminRepository;
        this.passwordUtil = passwordUtil;
    }



    public void registrarAdmin(Admin admin) throws ExecutionException, InterruptedException {
        admin.setIdAdmin(UUID.randomUUID().toString());
        if (existePorEmail(admin.getEmail())) {
            throw new IllegalArgumentException(" Ya existe un admin registrado con este correo: " + admin.getEmail());
        }
        admin.setContrasena(passwordUtil.encode(admin.getContrasena()));
        admin.setRol("ADMIN");
        adminRepository.registrarAdmin(admin);
    }

    public Admin obtenerIdAdmin(String idAdmin) throws ExecutionException, InterruptedException {
        return adminRepository.obtenerIdAdmin(idAdmin);
    }

    public List<Admin> obtenerAdmins() throws ExecutionException, InterruptedException {
        return adminRepository.obtenerAdmins();
    }

    public void eliminarAdmin(String idAdmin) throws ExecutionException, InterruptedException {
        adminRepository.eliminarAdmin(idAdmin);
    }


    public void modificarStockSiEsAdmin(Persona usuario, Producto producto, int cantidadProductos) {
        if (usuario instanceof Admin) {
            producto.modificarStock(cantidadProductos);
        } else {
            throw new SecurityException("Solo los administradores pueden modificar el stock.");
        }
    }

    public AdminDTO toDTO(Admin admin) {
        AdminDTO dto = new AdminDTO();
        dto.setIdAdmin(admin.getIdAdmin());
        dto.setNombre(admin.getNombre());
        dto.setEmail(admin.getEmail());
        dto.setTelefono(admin.getTelefono());
        return dto;
    }

    public boolean existePorEmail(String email) throws ExecutionException, InterruptedException {
        return obtenerAdmins().stream().anyMatch(admin -> admin.getEmail().equalsIgnoreCase(email));
    }





}

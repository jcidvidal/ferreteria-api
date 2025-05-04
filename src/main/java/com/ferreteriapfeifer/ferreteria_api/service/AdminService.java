package com.ferreteriapfeifer.ferreteria_api.service;


import com.ferreteriapfeifer.ferreteria_api.dto.AdminDto;
import com.ferreteriapfeifer.ferreteria_api.dto.ClienteDto;
import com.ferreteriapfeifer.ferreteria_api.model.Admin;
import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.ferreteriapfeifer.ferreteria_api.model.Persona;
import com.ferreteriapfeifer.ferreteria_api.model.Producto;
import com.ferreteriapfeifer.ferreteria_api.repository.AdminRepository;
import com.ferreteriapfeifer.ferreteria_api.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PasswordUtil passwordUtil;

    public void registrarAdmin(Admin admin) throws ExecutionException, InterruptedException {
        admin.setIdAdmin(UUID.randomUUID().toString());
        admin.setContrasena(passwordUtil.encode(admin.getContrasena()));
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

    public AdminDto toDTO(Admin admin) {
        AdminDto dto = new AdminDto();
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

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

    public void registrarAdmin(Admin admin) throws ExecutionException, InterruptedException {
        admin.setIdAdmin(UUID.randomUUID().toString());
        //TODO: Hashear contraseña
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
}

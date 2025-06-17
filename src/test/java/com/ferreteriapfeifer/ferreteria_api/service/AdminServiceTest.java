package com.ferreteriapfeifer.ferreteria_api.service;

import com.ferreteriapfeifer.ferreteria_api.model.Admin;
import com.ferreteriapfeifer.ferreteria_api.model.Persona;
import com.ferreteriapfeifer.ferreteria_api.model.Producto;
import com.ferreteriapfeifer.ferreteria_api.repository.AdminRepository;
import com.ferreteriapfeifer.ferreteria_api.util.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    private AdminService adminService;
    private AdminRepository adminRepository;
    private PasswordUtil passwordUtil;





    @BeforeEach
    void setUp() {
        adminRepository = mock(AdminRepository.class);
        passwordUtil = mock(PasswordUtil.class);
        adminService = new AdminService(adminRepository, passwordUtil);
    }

    @Test
    void testExistePorEmail_shouldReturnTrue() throws ExecutionException, InterruptedException {
        Admin admin1 = new Admin();
        admin1.setEmail("admin1@email.com");
        Admin admin2 = new Admin();
        admin2.setEmail("admin2@email.com");

        when(adminRepository.obtenerAdmins()).thenReturn(List.of(admin1, admin2));

        boolean exists = adminService.existePorEmail("admin2@email.com");

        assertTrue(exists);
        verify(adminRepository, times(1)).obtenerAdmins();
    }

    @Test
    void testModificarStockSiEsAdmin_shouldModifyStock() {
        Admin admin = new Admin();
        Producto producto = new Producto();
        producto.setStock(10);

        adminService.modificarStockSiEsAdmin(admin, producto, 5);

        assertEquals(15, producto.getStock());
    }

    @Test
    void testModificarStock_shouldThrowExceptionIfNotAdmin() {
        Persona persona = new Persona() {};
        Producto producto = new Producto();

        assertThrows(SecurityException.class, () ->
                adminService.modificarStockSiEsAdmin(persona, producto, 5));
    }
}

package com.ferreteriapfeifer.ferreteria_api.service;

import com.ferreteriapfeifer.ferreteria_api.model.Boleta;
import com.ferreteriapfeifer.ferreteria_api.model.DetalleProducto;
import com.ferreteriapfeifer.ferreteria_api.repository.BoletaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class BoletaService {

    private final BoletaRepository boletaRepository;

    public BoletaService(BoletaRepository boletaRepository) {
        this.boletaRepository = boletaRepository;
    }

    public void register(Boleta boleta) throws ExecutionException, InterruptedException {
        boletaRepository.register(boleta);
    }

    public Boleta get(String id) throws ExecutionException, InterruptedException {
        return boletaRepository.get(id);
    }

    public List<Boleta> getAll() throws ExecutionException, InterruptedException {
        return boletaRepository.getAll();
    }

    public void delete(String id) throws ExecutionException, InterruptedException {
        boletaRepository.delete(id);
    }

    public void calcularTotal(String idBoleta) throws ExecutionException, InterruptedException {
        Boleta boleta = boletaRepository.get(idBoleta);
        if (boleta != null && boleta.getEstado().equalsIgnoreCase("abierta")) {
            boleta.calcularTotal(); // usa los detalles y subtotales
            boletaRepository.register(boleta); // actualiza Firestore
        } else {
            throw new IllegalStateException("No se puede calcular total: boleta cerrada o inexistente.");
        }
    }


    public void cerrarBoleta(String idBoleta) throws ExecutionException, InterruptedException {
        Boleta boleta = boletaRepository.get(idBoleta);
        if (boleta != null && boleta.getEstado().equalsIgnoreCase("abierta")) {
            boleta.cerrarBoleta();
            boletaRepository.register(boleta); // Reescribe la boleta con estado cerrado
        } else {
            throw new IllegalStateException("No se puede cerrar: boleta ya está cerrada o no existe.");
        }
    }

    public void agregarDetalle(String idBoleta, DetalleProducto detalle) throws ExecutionException, InterruptedException {
        Boleta boleta = boletaRepository.get(idBoleta);
        if (boleta == null) {
            throw new IllegalArgumentException("Boleta no encontrada.");
        }
        if (!boleta.getEstado().equalsIgnoreCase("abierta")) {
            throw new IllegalStateException("No se puede modificar una boleta cerrada.");
        }

        // Calcular el subtotal del nuevo detalle
        detalle.calcularSubtotal();

        // Agregar el detalle a la lista
        boleta.getDetalles().add(detalle);

        // Recalcular total
        boleta.calcularTotal();

        // Guardar los cambios
        boletaRepository.register(boleta);
    }

}

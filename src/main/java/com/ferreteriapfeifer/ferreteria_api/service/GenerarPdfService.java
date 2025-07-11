package com.ferreteriapfeifer.ferreteria_api.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;
import com.ferreteriapfeifer.ferreteria_api.model.Compra;

@Service
public class GenerarPdfService {

    public byte[] generarComprobantePDF(Compra compra) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        Paragraph titulo = new Paragraph("Comprobante de Compra", font);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("ID Compra: " + compra.getIdCompra()));
        document.add(new Paragraph("Fecha: " + compra.getFechaPago()));
        document.add(new Paragraph("Cliente: " + compra.getCliente().getNombre()));
        document.add(new Paragraph("Email: " + compra.getCliente().getEmail()));
        document.add(new Paragraph("Teléfono: " + compra.getCliente().getTelefono()));
        document.add(new Paragraph("Método de Pago: " + compra.getMetodoPago()));
        document.add(new Paragraph("Estado: " + compra.getEstadoPago()));
        document.add(new Paragraph("Monto Pagado: $" + compra.getMontoPagado()));

        document.close();
        return out.toByteArray();
    }
}
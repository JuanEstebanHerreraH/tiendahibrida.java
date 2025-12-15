package com.mycompany.tiendaadmindesktop.DAO;

import com.mycompany.tiendaadmindesktop.Modelo.Log;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.mycompany.tiendaadmindesktop.util.ConexionDB;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class LogDAO {

    public static void registrar(String usuario, String accion) {

        String sql = "INSERT INTO logs (usuario, accion) VALUES (?, ?)";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, accion);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<Log> listar() {

    List<Log> logs = new ArrayList<>();

    String sql = """
        SELECT id, usuario, accion, fecha
        FROM logs
        ORDER BY fecha DESC
    """;

    try (Connection con = ConexionDB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Log log = new Log();
            log.setId(rs.getInt("id"));
            log.setUsuario(rs.getString("usuario"));
            log.setAccion(rs.getString("accion"));
            log.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
            logs.add(log);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return logs;
}
    
    public static void exportarExcel(String ruta) {

    try (Workbook workbook = new XSSFWorkbook()) {

        Sheet sheet = workbook.createSheet("Logs");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Usuario");
        header.createCell(2).setCellValue("Acción");
        header.createCell(3).setCellValue("Fecha");

        int fila = 1;
        for (Log log : listar()) {
            Row row = sheet.createRow(fila++);
            row.createCell(0).setCellValue(log.getId());
            row.createCell(1).setCellValue(log.getUsuario());
            row.createCell(2).setCellValue(log.getAccion());
            row.createCell(3).setCellValue(log.getFecha().toString());
        }

        try (FileOutputStream out = new FileOutputStream(ruta)) {
            workbook.write(out);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    public static void exportarPDF(String ruta) {

    try {
        PdfWriter writer = new PdfWriter(ruta);
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);

        doc.add(new Paragraph("Auditoría del Sistema\n\n"));

         com.itextpdf.layout.element.Table table =
        new com.itextpdf.layout.element.Table(4);

        table.addCell("ID");
        table.addCell("Usuario");
        table.addCell("Acción");
        table.addCell("Fecha");


        for (Log log : listar()) {
            table.addCell(String.valueOf(log.getId()));
            table.addCell(log.getUsuario());
            table.addCell(log.getAccion());
            table.addCell(log.getFecha().toString());
        }

        doc.add(table);
        doc.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}

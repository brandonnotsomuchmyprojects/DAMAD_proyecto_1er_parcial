package com.example.damad_proyecto_1er_parcial;

import android.content.Context;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ArchivoTxt {

    private static final String NOMBRE_ARCHIVO = "datos.txt";

    // Guardar lista de personas en el archivo
    public static void guardar(Context context, ArrayList<Persona> listaPersonas) {
        try {
            FileOutputStream fos = context.openFileOutput(NOMBRE_ARCHIVO, Context.MODE_PRIVATE);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            for (Persona p : listaPersonas) {
                bw.write(p.getNombre());
                bw.newLine();
                bw.write(String.valueOf(p.getEdad()));
                bw.newLine();
                bw.write(p.getCorreo());
                bw.newLine();
            }

            bw.close();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cargar lista de personas desde el archivo
    public static ArrayList<Persona> cargar(Context context) {
        ArrayList<Persona> listaPersonas = new ArrayList<>();

        try {
            FileInputStream fis = context.openFileInput(NOMBRE_ARCHIVO);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String nombre;
            while ((nombre = br.readLine()) != null) {
                String edadStr = br.readLine();
                String correo = br.readLine();

                if (edadStr != null && correo != null) {
                    int edad = Integer.parseInt(edadStr);
                    listaPersonas.add(new Persona(nombre, edad, correo));
                } else {
                    break; // Datos incompletos
                }
            }

            br.close();
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
            // Si no existe el archivo, simplemente retorna lista vacía
        }

        return listaPersonas;
    }
}

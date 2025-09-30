package com.example.damad_proyecto_1er_parcial;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private EditText etNombre, etEdad, etCorreo;
    private Button btnNuevo, btnEditar, btnGrabar, btnEliminar;

    // Lista en memoria
    private ArrayList<Persona> listaPersonas = new ArrayList<>();

    // Para saber qué persona está seleccionada
    private int indiceSeleccionado = -1;
    private boolean modoNuevo = false;
    private boolean modoEditar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = findViewById(R.id.tableLayout);
        etNombre = findViewById(R.id.etNombre);
        etEdad = findViewById(R.id.etEdad);
        etCorreo = findViewById(R.id.etCorreo);

        btnNuevo = findViewById(R.id.btnNuevo);
        btnEditar = findViewById(R.id.btnEditar);
        btnGrabar = findViewById(R.id.btnGrabar);
        btnEliminar = findViewById(R.id.btnEliminar);

        // Inicialmente los campos están bloqueados
        setEditTextsEnabled(false);

        // Cargar datos desde archivo (luego se implementa con ArchivoTxt)
        // listaPersonas = ArchivoTxt.cargar(getApplicationContext());
        listaPersonas = ArchivoTxt.cargar(getApplicationContext());


        // Refrescar tabla
        refrescarTabla();

        // BOTONES
        btnNuevo.setOnClickListener(v -> {
            limpiarCampos();
            setEditTextsEnabled(true);
            modoNuevo = true;
            modoEditar = false;
            indiceSeleccionado = -1;
        });

        btnEditar.setOnClickListener(v -> {
            if (indiceSeleccionado != -1) {
                setEditTextsEnabled(true);
                modoNuevo = false;
                modoEditar = true;
            } else {
                Toast.makeText(this, "Selecciona un registro primero", Toast.LENGTH_SHORT).show();
            }
        });

        btnGrabar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String edadStr = etEdad.getText().toString().trim();
            String correo = etCorreo.getText().toString().trim();

            if (nombre.isEmpty() || edadStr.isEmpty() || correo.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int edad = Integer.parseInt(edadStr);

            if (modoNuevo) {
                listaPersonas.add(new Persona(nombre, edad, correo));
            } else if (modoEditar && indiceSeleccionado != -1) {
                Persona p = listaPersonas.get(indiceSeleccionado);
                p.setNombre(nombre);
                p.setEdad(edad);
                p.setCorreo(correo);
            }

            // Guardar en archivo (luego con ArchivoTxt)
            // ArchivoTxt.guardar(getApplicationContext(), listaPersonas);
            ArchivoTxt.guardar(getApplicationContext(), listaPersonas);

            setEditTextsEnabled(false);
            limpiarCampos();
            refrescarTabla();
            modoNuevo = false;
            modoEditar = false;
        });

        btnEliminar.setOnClickListener(v -> {
            if (indiceSeleccionado != -1) {
                listaPersonas.remove(indiceSeleccionado);

                // Guardar en archivo
                // ArchivoTxt.guardar(getApplicationContext(), listaPersonas);
                ArchivoTxt.guardar(getApplicationContext(), listaPersonas);


                limpiarCampos();
                refrescarTabla();
                indiceSeleccionado = -1;
            } else {
                Toast.makeText(this, "Selecciona un registro primero", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Refrescar la tabla desde listaPersonas
    private void refrescarTabla() {
        // Borrar todas las filas excepto el encabezado
        tableLayout.removeViews(1, Math.max(0, tableLayout.getChildCount() - 1));

        for (int i = 0; i < listaPersonas.size(); i++) {
            Persona p = listaPersonas.get(i);

            TableRow fila = new TableRow(this);

            TextView tvNombre = new TextView(this);
            tvNombre.setText(p.getNombre());
            tvNombre.setPadding(16, 16, 16, 16);

            TextView tvEdad = new TextView(this);
            tvEdad.setText(String.valueOf(p.getEdad()));
            tvEdad.setPadding(16, 16, 16, 16);

            TextView tvCorreo = new TextView(this);
            tvCorreo.setText(p.getCorreo());
            tvCorreo.setPadding(16, 16, 16, 16);

            fila.addView(tvNombre);
            fila.addView(tvEdad);
            fila.addView(tvCorreo);

            int finalI = i;
            fila.setOnClickListener(v -> {
                indiceSeleccionado = finalI;
                Persona seleccionada = listaPersonas.get(finalI);
                etNombre.setText(seleccionada.getNombre());
                etEdad.setText(String.valueOf(seleccionada.getEdad()));
                etCorreo.setText(seleccionada.getCorreo());
                setEditTextsEnabled(false);
            });

            tableLayout.addView(fila);
        }
    }

    // Limpiar los EditText
    private void limpiarCampos() {
        etNombre.setText("");
        etEdad.setText("");
        etCorreo.setText("");
    }

    // Habilitar o deshabilitar los EditText
    private void setEditTextsEnabled(boolean enabled) {
        etNombre.setEnabled(enabled);
        etEdad.setEnabled(enabled);
        etCorreo.setEnabled(enabled);
    }
}

package isur.exa.dam2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmacionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmacion)

        val tvDatos = findViewById<TextView>(R.id.tvDatos)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        val nombre = intent.getStringExtra("nombre")
        val correo = intent.getStringExtra("correo")
        val edad = intent.getIntExtra("edad", 0)
        val genero = intent.getStringExtra("genero")

        tvDatos.text = """
            Nombre: $nombre
            Correo: $correo
            Edad: $edad
            Género: $genero
        """.trimIndent()

        btnVolver.setOnClickListener {
            finish()
        }
    }
}

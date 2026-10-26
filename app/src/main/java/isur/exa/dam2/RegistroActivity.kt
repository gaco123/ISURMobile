package isur.exa.dam2

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmar = findViewById<EditText>(R.id.etConfirmar)
        val etEdad = findViewById<EditText>(R.id.etEdad)
        val rgGenero = findViewById<RadioGroup>(R.id.rgGenero)
        val rbMasculino = findViewById<RadioButton>(R.id.rbMasculino)
        val rbFemenino = findViewById<RadioButton>(R.id.rbFemenino)
        val rbOtro = findViewById<RadioButton>(R.id.rbOtro)
        val cbTerminos = findViewById<CheckBox>(R.id.cbTerminos)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        fun esNombreValido(): Boolean {
            val nombre = etNombre.text.toString().trim()
            return nombre.isNotEmpty() && nombre.length >= 3
        }

        fun esCorreoValido(): Boolean {
            val correo = etCorreo.text.toString().trim()
            return correo.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(correo).matches()
        }

        fun esPasswordValida(): Boolean {
            val pass = etPassword.text.toString()
            return pass.length >= 6
        }

        fun esConfirmarValido(): Boolean {
            val pass = etPassword.text.toString()
            val conf = etConfirmar.text.toString()
            return conf.isNotEmpty() && conf == pass
        }

        fun esEdadValida(): Boolean {
            val edadTexto = etEdad.text.toString()
            val edad = edadTexto.toIntOrNull()
            return edad != null && edad >= 18
        }

        fun esGeneroValido(): Boolean {
            return rgGenero.checkedRadioButtonId != -1
        }

        fun esTerminosValido(): Boolean {
            return cbTerminos.isChecked
        }

        fun setRadioGroupEnabled(enabled: Boolean) {
            rbMasculino.isEnabled = enabled
            rbFemenino.isEnabled = enabled
            rbOtro.isEnabled = enabled
        }

        fun actualizarEstados() {
            val nombreOk = esNombreValido()
            etCorreo.isEnabled = nombreOk

            val correoOk = nombreOk && esCorreoValido()
            etPassword.isEnabled = correoOk

            val passOk = correoOk && esPasswordValida()
            etConfirmar.isEnabled = passOk

            val confOk = passOk && esConfirmarValido()
            etEdad.isEnabled = confOk

            val edadOk = confOk && esEdadValida()
            setRadioGroupEnabled(edadOk)

            val generoOk = edadOk && esGeneroValido()
            cbTerminos.isEnabled = generoOk

            val terminosOk = generoOk && esTerminosValido()
            btnRegistrar.isEnabled = terminosOk
        }

        // Estado inicial
        etCorreo.isEnabled = false
        etPassword.isEnabled = false
        etConfirmar.isEnabled = false
        etEdad.isEnabled = false
        setRadioGroupEnabled(false)
        cbTerminos.isEnabled = false
        btnRegistrar.isEnabled = false

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                actualizarEstados()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        etNombre.addTextChangedListener(textWatcher)
        etCorreo.addTextChangedListener(textWatcher)
        etPassword.addTextChangedListener(textWatcher)
        etConfirmar.addTextChangedListener(textWatcher)
        etEdad.addTextChangedListener(textWatcher)

        etNombre.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && !esNombreValido()) {
                etNombre.error = "Ingrese un nombre válido (mínimo 3 caracteres)"
            }
        }

        etCorreo.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && etCorreo.isEnabled && !esCorreoValido()) {
                etCorreo.error = "Correo electrónico inválido"
            }
        }

        etPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && etPassword.isEnabled && !esPasswordValida()) {
                etPassword.error = "La contraseña debe tener mínimo 6 caracteres"
            }
        }

        etConfirmar.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && etConfirmar.isEnabled && !esConfirmarValido()) {
                etConfirmar.error = "Las contraseñas no coinciden"
            }
        }

        etEdad.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && etEdad.isEnabled && !esEdadValida()) {
                etEdad.error = "Debe ser mayor de edad (mínimo 18 años)"
            }
        }

        rgGenero.setOnCheckedChangeListener { _, _ ->
            actualizarEstados()
        }

        cbTerminos.setOnCheckedChangeListener { _, _ ->
            actualizarEstados()
        }

        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val edad = etEdad.text.toString().toInt()
            val genero = when (rgGenero.checkedRadioButtonId) {
                R.id.rbMasculino -> "Masculino"
                R.id.rbFemenino -> "Femenino"
                else -> "Otro"
            }

            val intent = Intent(this, ConfirmacionActivity::class.java)
            intent.putExtra("nombre", nombre)
            intent.putExtra("correo", correo)
            intent.putExtra("edad", edad)
            intent.putExtra("genero", genero)
            startActivity(intent)
        }
    }
}

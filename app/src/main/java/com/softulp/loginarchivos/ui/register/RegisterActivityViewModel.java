package com.softulp.loginarchivos.ui.register;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softulp.loginarchivos.request.ApiClient;
import com.softulp.loginarchivos.utils.EmailValidator;
import com.softulp.loginarchivos.utils.InputError;
import com.softulp.loginarchivos.model.Usuario;

public class RegisterActivityViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<InputError> inputErrors;
    private MutableLiveData<Usuario> usuarioM;

    public RegisterActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<InputError> getInputErrors(){
        if (inputErrors == null){
            inputErrors = new MutableLiveData<>();
        }
        return inputErrors;
    }

    public LiveData<Usuario> getUsuarioM(){
        if (usuarioM == null){
            usuarioM = new MutableLiveData<>();
        }
        return usuarioM;
    }

    public void guardarDatos(String nombre, String apellido, String dni, String email, String password){

        if (validarInputs(nombre, apellido, dni, email, password)){
            Usuario usuario = new Usuario(nombre, password, Long.parseLong(dni), email, apellido);
            ApiClient.guardar(context, usuario);
            Toast.makeText(context, "¡Su usuario se ha guardado con éxito!", Toast.LENGTH_LONG).show();
        }

    }

    private boolean validarInputs(String nombre, String apellido, String dni, String email, String password){
        boolean valido = true;
        InputError errores = new InputError();

        if (nombre.isBlank()){
            errores.setNombreError("¡El nombre no puede estar vacio!");
            valido = false;
        }

        if (apellido.isBlank()){
            errores.setApellidoError("¡El apellido no puede estar vacio!");
            valido = false;
        }

        if (dni.isBlank()){
            errores.setDniError("¡El DNI no puede estar vacio!");
            valido = false;
        } else if (!dni.matches("\\d+")) {
            errores.setDniError("El DNI solo puede contener números.");
            valido = false;
        }

        if (email.isBlank()){
            errores.setEmailError("¡El email no puede estar vacio!");
            valido = false;
        } else if (!EmailValidator.isValidEmail(email)){
            errores.setEmailError("El email debe tener un formato valido(ej: 'suEmail@gmail.com').");
            valido = false;
        }

        if (password.isBlank()){
            errores.setPasswordError("¡La contraseña no puede estar vacia!");
            valido = false;
        }

        inputErrors.setValue(errores);

        return valido;
    }

    public void getDatos(Intent intent){
        if (intent.getBooleanExtra("existingUser", false)){
            Usuario usuario = ApiClient.leer(context);
            if (usuario != null) {
                usuarioM.setValue(usuario);
            } else {
                Toast.makeText(context, "Oops: ¡Ocurrio un error inesperado al recuperar su usuario!", Toast.LENGTH_LONG).show();
            }
        }
    }

}

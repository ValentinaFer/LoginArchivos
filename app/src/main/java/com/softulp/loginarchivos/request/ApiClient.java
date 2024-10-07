package com.softulp.loginarchivos.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.softulp.loginarchivos.model.Usuario;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ApiClient {

    private static File archivoUsuario;

    private static File conectar(Context context){
        if (archivoUsuario == null){
            archivoUsuario = new File(context.getFilesDir(), "Usuario.obj");
        }
        return archivoUsuario;
    }

    public static void guardar(Context context, Usuario usuario){

        archivoUsuario = conectar(context);

        //try-with-resources: FileOutputStream y ObjectOutputStream implementan
        //las interfaces AutoCloseable, por lo que utilizando un try-with-resources
        //aunque suceda una exception, el 'try-catch' se encargará de cerrarlo
        try (FileOutputStream fos = new FileOutputStream(archivoUsuario, false);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(usuario);
            oos.flush();
        } catch (FileNotFoundException ex){
            Toast.makeText(context, "¡Error de archivo!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, "¡Error de entrada/salida!", Toast.LENGTH_SHORT).show();
        }

    }

    public static Usuario leer(Context context){

        Usuario usuario = null;
        archivoUsuario = conectar(context);

        try (FileInputStream fis = new FileInputStream(archivoUsuario);
            ObjectInputStream ois = new ObjectInputStream(fis)){

            usuario = (Usuario) ois.readObject();

        } catch (FileNotFoundException e) {
            Toast.makeText(context, "¡Error de archivo!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, "¡Error de entrada/salida!", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            Toast.makeText(context, "Error inesperado!", Toast.LENGTH_SHORT).show();
        }

        return usuario;
    }

    public static Usuario login(Context context, String email, String password){
        Usuario usuario = null;
        usuario = leer(context); //recupero usuario y luego comparo con email y password.
        if (!email.equals(usuario.getEmail()) || !password.equals(usuario.getPassword())){
            usuario = null; //null si no coinciden los datos o directamente no es el mismo user
        }
        return usuario; //else return el usuario encontrado
    }

}

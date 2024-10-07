package com.softulp.loginarchivos.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.softulp.loginarchivos.databinding.ActivityMainBinding;
import com.softulp.loginarchivos.ui.register.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);
        setContentView(binding.getRoot());

        vm.getMEmailError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.textInputLayoutEmail.setError(s);
            }
        });
        vm.getMPasswordError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.textInputLayoutPassword.setError(s);
            }
        });

        binding.btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textInputLayoutPassword.setError(null);
                binding.textInputLayoutEmail.setError(null);
                vm.ingresar(binding.emailEditText.getText().toString(), binding.passwordEditText.getText().toString());
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}
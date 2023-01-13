package br.com.buscandoenderecocepretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import br.com.buscandoenderecocepretrofit.api.DataService;
import br.com.buscandoenderecocepretrofit.databinding.ActivityMainBinding;
import br.com.buscandoenderecocepretrofit.model.CEP;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        binding.btnBuscar.setOnClickListener(view -> {

            if (!binding.editCep.getText().toString().isEmpty()){

                binding.linearDados.setVisibility(View.VISIBLE);
                String cep = binding.editCep.getText().toString();
                recuperarCepRetrofit(cep);

            }else{

                Toast.makeText(getApplicationContext(), "Preencga o campo sem traco e sem espaço. E.:0010100", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void recuperarCepRetrofit(String cep) {

        DataService service = retrofit.create(DataService.class);
        Call<CEP> call = service.recuperarCEP(cep);

        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {

                if (response.isSuccessful()){

                    CEP cep1 = response.body();

                    binding.editRua.setText(cep1.getLogradouro());
                    binding.editComplemento.setText(cep1.getComplemento());
                    binding.editBairro.setText(cep1.getBairro());
                    binding.editCidade.setText(cep1.getLocalidade());
                    binding.editEstado.setText(cep1.getUf());

                }
            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {

            }
        });
    }
}
package br.com.buscandoenderecocepretrofit.api;

import br.com.buscandoenderecocepretrofit.model.CEP;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DataService {

    @GET("{cep}/json/")
    Call<CEP> recuperarCEP(@Path("cep") String cep);
}

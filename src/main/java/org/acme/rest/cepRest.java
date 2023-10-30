package org.acme.rest;

import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.service.CepService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Path("/JSON CEP")
public class cepRest {

    @RestClient
    CepService cepService;

    @GET
    @Path("/{cep}")
    @Produces(MediaType.APPLICATION_JSON)
    public String obterCep(@PathParam("cep") String cep) throws IOException {
        String resposta = cepService.buscarCep(cep);
        gerarArquivo(cep,resposta);
        return resposta;
    }

    private void gerarArquivo(String cep, String resposta) {
        String nomeArquivo = cep + ".txt";
        String caminho = "/home/facebookson/Área de Trabalho/JsonCep/" + nomeArquivo;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            bw.write(resposta);
            Log.info("Arquivo gerado com sucesso");
        } catch (IOException e){
            e.printStackTrace();
            Log.info("Erro ao gerar arquivo");
        }
    }
}

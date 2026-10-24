package com.uniamerica.sgidebrito;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CrudIntegrationTest {
    private static ConfigurableApplicationContext context;
    private static MockMvc mvc;
    private static JdbcTemplate jdbc;

    // Inicia uma vez, usando o banco H2 do perfil test e uma porta livre.
    @BeforeAll
    static void iniciarAplicacaoDeTeste() {
        context = new SpringApplicationBuilder(SgidebritoApplication.class)
                .profiles("test").run("--server.port=0");
        mvc = MockMvcBuilders.webAppContextSetup((WebApplicationContext) context).build();
        jdbc = context.getBean(JdbcTemplate.class);
    }

    // Limpa apenas o banco em memoria antes de cada cenario.
    @BeforeEach
    void limparBancoDeTeste() {
        jdbc.update("delete from funcionario");
        jdbc.update("delete from cliente");
        jdbc.update("delete from usuario");
    }

    @AfterAll
    static void encerrarAplicacao() {
        if (context != null) {
            context.close();
        }
    }

    // Monta o JSON enviado para cada modulo.
    private String dados(String modulo, String valor) {
        return switch (modulo) {
            case "usuarios" -> """
                    {"login":"%s","senha":"teste","papel":"ADMIN"}
                    """.formatted(valor);
            case "funcionarios" -> """
                    {"nome":"%s","cpf":"00000000000","email":"teste@example.invalid",
                     "telefone":"4500000000","cargo":"Instrutor","categoriaCnh":"B"}
                    """.formatted(valor);
            default -> """
                    {"nome":"%s","cpf":"00000000000","email":"teste@example.invalid","telefone":"4500000000"}
                    """.formatted(valor);
        };
    }

    // Executa este cenario uma vez para cada modulo indicado abaixo.
    @ParameterizedTest
    @ValueSource(strings = {"clientes", "funcionarios", "usuarios"})
    void crudCompletoComPersistenciaReal(String modulo) throws Exception {
        String url = "/" + modulo;
        String campo = modulo.equals("usuarios") ? "login" : "nome";
        String tabela = modulo.substring(0, modulo.length() - 1);
        // Cadastra e guarda o ID retornado pelo backend.
        String resposta = mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(dados(modulo, "Teste")))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.id").isNumber())
                .andExpect(content().json(dados(modulo, "Teste")))
                .andReturn().getResponse().getContentAsString();
        Number id = JsonPath.read(resposta, "$.id");

        // Confere a gravacao no banco e as consultas pelos endpoints.
        assertEquals("Teste", jdbc.queryForObject("select " + campo + " from " + tabela + " where id = ?", String.class, id));
        mvc.perform(get(url)).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id));
        mvc.perform(get(url + "/" + id)).andExpect(status().isOk()).andExpect(jsonPath("$." + campo).value("Teste"));
        // Atualiza e confere se a alteracao foi persistida.
        mvc.perform(put(url + "/" + id).contentType(MediaType.APPLICATION_JSON).content(dados(modulo, "Alterado")))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$." + campo).value("Alterado"))
                .andExpect(content().json(dados(modulo, "Alterado")));
        assertEquals("Alterado", jdbc.queryForObject("select " + campo + " from " + tabela + " where id = ?", String.class, id));
        // Exclui e confirma que o registro deixou de existir.
        mvc.perform(delete(url + "/" + id)).andExpect(status().isNoContent()).andExpect(content().string(""));
        assertEquals(0, jdbc.queryForObject("select count(*) from " + tabela + " where id = ?", Integer.class, id));
        mvc.perform(get(url + "/" + id)).andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @ValueSource(strings = {"clientes", "funcionarios", "usuarios"})
    void idsInexistentesRetornam404(String modulo) throws Exception {
        String url = "/" + modulo + "/999999";
        mvc.perform(get(url)).andExpect(status().isNotFound());
        mvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(dados(modulo, "Teste")))
                .andExpect(status().isNotFound());
        mvc.perform(delete(url)).andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @ValueSource(strings = {"clientes", "funcionarios", "usuarios"})
    void jsonMalformadoNaoCriaRegistro(String modulo) throws Exception {
        mvc.perform(post("/" + modulo).contentType(MediaType.APPLICATION_JSON).content("{"))
                .andExpect(status().isBadRequest());
        mvc.perform(get("/" + modulo)).andExpect(status().isOk()).andExpect(content().json("[]"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"clientes", "funcionarios", "usuarios"})
    void idNaoNumericoRetorna400(String modulo) throws Exception {
        mvc.perform(get("/" + modulo + "/invalido")).andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"clientes", "funcionarios", "usuarios"})
    void corsPermiteFrontend(String modulo) throws Exception {
        mvc.perform(options("/" + modulo).header("Origin", "http://localhost:4200")
                .header("Access-Control-Request-Method", "POST"))
                .andExpect(status().isOk()).andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:4200"));
    }
    @ParameterizedTest
    @ValueSource(strings = {"clientes", "funcionarios", "usuarios"})
    void idEnviadoNoCorpoNaoAlteraIdentidade(String modulo) throws Exception {
        String url = "/" + modulo;
        String cadastro = dados(modulo, "Teste").replace("{", "{\"id\":999999,");
        String resposta = mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(cadastro))
                .andExpect(status().isCreated())
                .andExpect(content().json(dados(modulo, "Teste")))
                .andReturn().getResponse().getContentAsString();
        Number id = JsonPath.read(resposta, "$.id");
        assertNotEquals(999999L, id.longValue());

        String alteracao = dados(modulo, "Alterado").replace("{", "{\"id\":888888,");
        mvc.perform(put(url + "/" + id).contentType(MediaType.APPLICATION_JSON).content(alteracao))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(content().json(dados(modulo, "Alterado")));
        mvc.perform(get(url + "/888888")).andExpect(status().isNotFound());
    }
}

package br.edu.fema;

import io.javalin.Javalin;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        var app = Javalin.create().start(8080);

        app.get("/", ctx -> ctx.result(System.in));

        // AQUI ENTRA O REFLECTION:
        // O "Motor" que você vai desenvolver deve rodar aqui
        registrarRotasDinamicas(app);

    }
    private static void registrarRotasDinamicas(Javalin app) {
        // 1. Você usará Reflection para buscar classes no seu pacote
        // 2. Para cada classe com @C, você cria um app.post(...)
        // 3. Para cada classe com @R, você cria um app.get(...)

        System.out.println("Escaneando anotações para mapear endpoints...");
    }
}
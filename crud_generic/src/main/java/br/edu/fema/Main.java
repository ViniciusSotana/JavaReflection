package br.edu.fema;

import br.edu.fema.reflection.annotations.Id;
import io.javalin.Javalin;

import java.lang.reflect.Field;
import java.util.Set;

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

    private static void registrarCreate(Javalin app, Set<Class<?>> classes) {
        for (Class<?> classe : classes) {
            String path = classe.getSimpleName().toLowerCase();

            app.post(path, ctx -> {
                Object entity = ctx.bodyAsClass(classe);

                String idValue = extrairIdViaReflection(classe, entity);

                if (idValue == null) {
                    idValue = UUID.randomUUID().toString(); // Gera ID se não veio no JSON
                    injetarIdViaReflection(classe, entity, idValue);
                }

                MockDatabase.data.get(classe).put(idValue, entity);

                ctx.status(201).json(entity);
                System.out.println("Criado objeto em " + path + ": " + idValue);
            });
        }
    }

    private static String extrairIdViaReflection(Class<?> clazz, Object entity) throws IllegalAccessException {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                Object value = field.get(entity);
                return value != null ? value.toString() : null;
            }
        }
        throw new RuntimeException("A classe " + clazz.getSimpleName() + " não possui um campo anotado com @Id.");
    }

    private static void injetarIdViaReflection(Class<?> clazz, Object entity, Integer novoId) throws IllegalAccessException {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                field.set(entity, novoId);
                return;
            }
        }
    }
}
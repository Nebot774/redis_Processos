package org.example;
import redis.clients.jedis.Jedis;
import java.util.Random;

public class URLacortadorServicio {
    public static void main(String[] args) {
        // Nos conectamos al servidor Redis local
        Jedis jedis;
        try {
            jedis = new Jedis("127.0.0.1", 6379);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }



        while (true) {
            // Obtenemos la siguiente URL pendiente de la lista
            String url = jedis.rpop("ADRIAN:URLS_TO_SHORT");

            if (url != null) {
                // Generar una clave aleatoria de 8 caracteres
                String shortKey = generarContraseña(8);

                // Almacenar la URL original en el hash con la clave aleatoria
                jedis.hset("ADRIAN:SHORTED_URLS", shortKey, url);

                // Simulación de lo que sería la URL acortada (no es parte del servicio real)
                System.out.println("URL acortada: www.midominio.com/" + shortKey);
            }

            // Esperar un momento antes de revisar de nuevo
            try {
                Thread.sleep(1000); // Espera de 1 segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Función para generar una cadena aleatoria
    private static String generarContraseña(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }
}


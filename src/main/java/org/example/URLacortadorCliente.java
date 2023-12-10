package org.example;

import redis.clients.jedis.Jedis;
import java.util.Scanner;

public class URLacortadorCliente {
    public static void main(String[] args) {
        // Nos coenctamos al servidor redis local
        Jedis jedis;
        try {
            jedis = new Jedis("127.0.0.1", 6379);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }


        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Introduze un comando:");
            String command = scanner.nextLine();
            String[] parts = command.split(" ");

            // Revisar el comando ingresado
            switch (parts[0]) {
                case "shorten":
                    // Obtenemos la URL a acortar
                    String url = parts[1];
                    // añadimos la url al lsitado de redis para ser processada
                    jedis.lpush("ADRIAN:URLS_TO_SHORT", url);
                    break;
                case "url":
                    // Obtenemos la URL acortada
                    String shortedUrl = parts[1];
                    // Buscamos la URL original en el hash de Redis y mostrarla
                    String originalUrl = jedis.hget("ADRIAN:SHORTED_URLS", shortedUrl);
                    System.out.println("URL Original: " + originalUrl);
                    break;
                case "exit":
                    // Cerrar la conexión con Redis y salir de la aplicación
                    jedis.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Comando desconocido ");
            }
        }
    }
}


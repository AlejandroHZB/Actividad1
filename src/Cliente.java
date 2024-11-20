import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 18080);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Menú Consulta de libros:");
                System.out.println("1. Consultar libro por ISBN");
                System.out.println("2. Consultar libro por título");
                System.out.println("3. Consultar libro por autor");
                System.out.println("4. Añadir libro");
                System.out.println("5. Salir del menú");
                System.out.print("Seleccione una opción: ");
                String opcion = scanner.nextLine();
                output.writeObject(opcion);


                switch (opcion) {
                    case "1":

                        System.out.print("Introduzca el ISBN del libro: ");
                        String isbn = scanner.nextLine();
                        output.writeObject(isbn);
                        System.out.println(input.readObject());
                        break;
                    case "2":

                        System.out.print("Introduzca el título del libro: ");
                        String titulo = scanner.nextLine();

                        output.writeObject(titulo);
                        System.out.println(input.readObject());
                        break;
                    case "3":

                        System.out.print("Introduzca el autor que le interese: ");
                        String autor = scanner.nextLine();
                        output.writeObject(autor);
                        System.out.println(input.readObject());
                        break;
                    case "4":

                        System.out.print("Introduzca el ISBN: ");
                        isbn = scanner.nextLine();
                        System.out.print("Introduzca el título: ");
                        titulo = scanner.nextLine();
                        System.out.print("Introduzca el autor: ");
                        autor = scanner.nextLine();
                        System.out.print("Introduzca el precio: ");
                        double precio = Double.parseDouble(scanner.nextLine());
                        Libro nuevoLibro = new Libro(isbn, titulo, autor, precio);
                        output.writeObject(nuevoLibro);
                        System.out.println(input.readObject());
                        break;
                    case "5":

                        System.out.println(input.readObject());
                        return;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

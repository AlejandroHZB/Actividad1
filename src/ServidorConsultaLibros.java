import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorConsultaLibros {
    private static List<Libro> libros = new ArrayList<>();

    public static void main(String[] args) {
        cargarLibros();

        try (ServerSocket serverSocket = new ServerSocket(18080)) {
            System.out.println("El servidor se ha iniciado en el puerto 8080...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("El cliente conectado se ha conectado al servidor.");

            try (ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
                 ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())) {

                while (true) {
                    String opcion = (String) input.readObject();

                    switch (opcion) {
                        case "1":
                            String isbn = (String) input.readObject();
                            Libro libroIsbn = buscarPorISBN(isbn);
                            if (libroIsbn != null) {
                                output.writeObject(libroIsbn);
                            } else {
                                output.writeObject("No hay libros con ese ISBN.");
                            }
                            break;
                        case "2":
                            String titulo = (String) input.readObject();
                            Libro libroTitulo = buscarPorTitulo(titulo);
                            if (libroTitulo != null) {
                                output.writeObject(libroTitulo);
                            } else {
                                output.writeObject("No hay libros con ese titulo");
                            }

                        case "3":
                            String autor = (String) input.readObject();
                            List<Libro> librosAutor = buscarPorAutor(autor);
                            if (!librosAutor.isEmpty()) {
                                output.writeObject(librosAutor);
                            } else {
                                output.writeObject("No hay libros de ese Autor.");
                            }
                            break;
                        case "4":
                            Libro nuevoLibro = (Libro) input.readObject();
                            agregarLibro(nuevoLibro);
                            output.writeObject("Libro añadido con exito.");
                            break;
                        case "5":
                            output.writeObject("Fin de programa.");
                            return;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Repositorio de libros existentes en el Servidor
    private static void cargarLibros() {
        libros.add(new Libro("ES123", "Nacidos de la Bruma", "Brandon Sanderson", 25));
        libros.add(new Libro("ES234", "Elantris", "Brandon Sanderson", 24.90));
        libros.add(new Libro("ES345", "Harry Potter", "J.K Rowling", 15.00));
        libros.add(new Libro("ES456", "Los juegos del hambre", "Suzzane Collins", 19.99));
        libros.add(new Libro("ES567", "El camino de los reyes", "Brandon Sanderson", 20.00));
    }

    private static void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    private static Libro buscarPorISBN(String isbn) {
        for (Libro libro : libros) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }


    private static Libro buscarPorTitulo(String titulo) {
        for (Libro libro : libros) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                return libro;
            }
        }
        return null; //
    }

    private static List<Libro> buscarPorAutor(String autor) {
        List<Libro> librosPorAutor = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.getAutor().equalsIgnoreCase(autor)) {
                librosPorAutor.add(libro);
            }
        }
        return librosPorAutor;
    }
}



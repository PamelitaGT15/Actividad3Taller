package com.thermoaire.agenda;

import java.util.Scanner;
import java.util.List;

/**
 * Aplicacion principal de la Agenda Empresarial.
 * Version sencilla para fines academicos.
 */
public class AgendaApp {

    private static final Scanner scanner = new Scanner(System.in);

    private static final ClienteRepository clienteRepo = new ClienteRepository();
    private static final ProveedorRepository proveedorRepo = new ProveedorRepository();

    public static void main(String[] args) {

        // Cargar datos desde CSV al iniciar (RF-10, EXT-04)
        clienteRepo.cargarDesdeArchivo();
        proveedorRepo.cargarDesdeArchivo();

        boolean salir = false;

        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = leerEntero("Selecciona una opcion: ");

            switch (opcion) {
                case 1:
                    menuClientes();
                    break;
                case 2:
                    menuProveedores();
                    break;
                case 0:
                    System.out.println("Saliendo de la aplicacion...");
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion no valida, intenta de nuevo.");
            }
        }

        System.out.println("Gracias por usar la Agenda Empresarial.");
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("===================================");
        System.out.println("      AGENDA EMPRESARIAL v1.0      ");
        System.out.println("===================================");
        System.out.println("1. Gestionar clientes");
        System.out.println("2. Gestionar proveedores");
        System.out.println("0. Salir");
        System.out.println("===================================");
    }

    // ----- Submenu clientes -----

    private static void menuClientes() {
        boolean regresar = false;

        while (!regresar) {
            System.out.println("---- MODULO CLIENTES ----");
            System.out.println("1. Alta de cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Buscar clientes");
            System.out.println("4. Editar cliente");
            System.out.println("5. Eliminar o marcar inactivo");
            System.out.println("0. Regresar");
            int opcion = leerEntero("Selecciona una opcion: ");

            switch (opcion) {
                case 1:
                    altaCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    buscarClientes();
                    break;
                case 4:
                    editarCliente();
                    break;
                case 5:
                    eliminarOInactivarCliente();
                    break;
                case 0:
                    regresar = true;
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private static void altaCliente() {
        System.out.println("[Alta de cliente]");
        String nombre = leerTextoObligatorio("Nombre del cliente: ");
        String empresa = leerTexto("Empresa (opcional): ");
        String telefono = leerTelefono("Telefono: ");
        String correo = leerTexto("Correo (opcional): ");
        String notas = leerTexto("Notas (opcional): ");

        Cliente cliente = new Cliente(nombre, empresa, telefono, correo, notas, "ACTIVO");
        clienteRepo.agregar(cliente);
        System.out.println("Cliente guardado correctamente."); // RF-12
    }

    private static void listarClientes() {
        System.out.println("[Listado de clientes]");
        List<Cliente> clientes = clienteRepo.obtenerTodos();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        int i = 1;
        for (Cliente c : clientes) {
            System.out.println(i + ". " + c.toLineaLista());
            i++;
        }
    }

    private static void buscarClientes() {
        String termino = leerTextoObligatorio("Buscar por nombre, empresa o telefono: ");
        List<Cliente> resultados = clienteRepo.buscar(termino);
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron clientes con ese criterio.");
        } else {
            System.out.println("Resultados:");
            int i = 1;
            for (Cliente c : resultados) {
                System.out.println(i + ". " + c.toLineaLista());
                i++;
            }
        }
    }

    private static void editarCliente() {
        listarClientes();
        List<Cliente> clientes = clienteRepo.obtenerTodos();
        if (clientes.isEmpty()) {
            return;
        }
        int indice = leerEntero("Numero de cliente a editar: ");
        if (indice < 1 || indice > clientes.size()) {
            System.out.println("Indice no valido.");
            return;
        }
        Cliente actual = clientes.get(indice - 1);

        System.out.println("Deja vacio para mantener el valor actual.");
        String nombre = leerTextoOpcionalConActual("Nombre", actual.getNombre());
        String empresa = leerTextoOpcionalConActual("Empresa", actual.getEmpresa());
        String telefono = leerTelefonoOpcionalConActual("Telefono", actual.getTelefono());
        String correo = leerTextoOpcionalConActual("Correo", actual.getCorreo());
        String notas = leerTextoOpcionalConActual("Notas", actual.getNotas());
        String estatus = leerTextoOpcionalConActual("Estatus (ACTIVO/INACTIVO)", actual.getEstatus());

        Cliente editado = new Cliente(nombre, empresa, telefono, correo, notas, estatus);
        clienteRepo.actualizar(indice - 1, editado);
        System.out.println("Cliente actualizado correctamente.");
    }

    private static void eliminarOInactivarCliente() {
        listarClientes();
        List<Cliente> clientes = clienteRepo.obtenerTodos();
        if (clientes.isEmpty()) {
            return;
        }
        int indice = leerEntero("Numero de cliente a eliminar o inactivar: ");
        if (indice < 1 || indice > clientes.size()) {
            System.out.println("Indice no valido.");
            return;
        }

        Cliente actual = clientes.get(indice - 1);
        System.out.println("Seleccionado: " + actual.toLineaLista());
        System.out.println("1. Marcar como INACTIVO");
        System.out.println("2. Eliminar definitivamente");
        int opcion = leerEntero("Elige una opcion: ");

        if (opcion == 1) {
            // S-02 confirmacion
            if (confirmar("Confirmas marcar como INACTIVO? (s/n): ")) {
                actual.setEstatus("INACTIVO");
                clienteRepo.actualizar(indice - 1, actual);
                System.out.println("Cliente marcado como inactivo.");
            }
        } else if (opcion == 2) {
            if (confirmar("Confirmas eliminar definitivamente? (s/n): ")) {
                clienteRepo.eliminar(indice - 1);
                System.out.println("Cliente eliminado.");
            }
        } else {
            System.out.println("Opcion no valida.");
        }
    }

    // ----- Submenu proveedores -----

    private static void menuProveedores() {
        boolean regresar = false;

        while (!regresar) {
            System.out.println("---- MODULO PROVEEDORES ----");
            System.out.println("1. Alta de proveedor");
            System.out.println("2. Listar proveedores");
            System.out.println("3. Buscar proveedores");
            System.out.println("4. Editar proveedor");
            System.out.println("5. Clasificar activo/inactivo");
            System.out.println("0. Regresar");
            int opcion = leerEntero("Selecciona una opcion: ");

            switch (opcion) {
                case 1:
                    altaProveedor();
                    break;
                case 2:
                    listarProveedores();
                    break;
                case 3:
                    buscarProveedores();
                    break;
                case 4:
                    editarProveedor();
                    break;
                case 5:
                    clasificarProveedor();
                    break;
                case 0:
                    regresar = true;
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private static void altaProveedor() {
        System.out.println("[Alta de proveedor]");
        String nombre = leerTextoObligatorio("Nombre del proveedor: ");
        String empresa = leerTexto("Empresa (opcional): ");
        String giro = leerTexto("Giro (opcional): ");
        String telefono = leerTelefono("Telefono: ");
        String correo = leerTexto("Correo (opcional): ");
        String notas = leerTexto("Notas (opcional): ");

        Proveedor proveedor = new Proveedor(nombre, empresa, giro, telefono, correo, notas, "ACTIVO");
        proveedorRepo.agregar(proveedor);
        System.out.println("Proveedor guardado correctamente.");
    }

    private static void listarProveedores() {
        System.out.println("[Listado de proveedores]");
        List<Proveedor> proveedores = proveedorRepo.obtenerTodos();
        if (proveedores.isEmpty()) {
            System.out.println("No hay proveedores registrados.");
            return;
        }
        int i = 1;
        for (Proveedor p : proveedores) {
            System.out.println(i + ". " + p.toLineaLista());
            i++;
        }
    }

    private static void buscarProveedores() {
        String termino = leerTextoObligatorio("Buscar por nombre, empresa o tipo de servicio: ");
        List<Proveedor> resultados = proveedorRepo.buscar(termino);
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron proveedores con ese criterio.");
        } else {
            System.out.println("Resultados:");
            int i = 1;
            for (Proveedor p : resultados) {
                System.out.println(i + ". " + p.toLineaLista());
                i++;
            }
        }
    }

    private static void editarProveedor() {
        listarProveedores();
        List<Proveedor> proveedores = proveedorRepo.obtenerTodos();
        if (proveedores.isEmpty()) {
            return;
        }
        int indice = leerEntero("Numero de proveedor a editar: ");
        if (indice < 1 || indice > proveedores.size()) {
            System.out.println("Indice no valido.");
            return;
        }
        Proveedor actual = proveedores.get(indice - 1);

        System.out.println("Deja vacio para mantener el valor actual.");
        String nombre = leerTextoOpcionalConActual("Nombre", actual.getNombre());
        String empresa = leerTextoOpcionalConActual("Empresa", actual.getEmpresa());
        String giro = leerTextoOpcionalConActual("Giro", actual.getGiro());
        String telefono = leerTelefonoOpcionalConActual("Telefono", actual.getTelefono());
        String correo = leerTextoOpcionalConActual("Correo", actual.getCorreo());
        String notas = leerTextoOpcionalConActual("Notas", actual.getNotas());
        String estatus = leerTextoOpcionalConActual("Estatus (ACTIVO/INACTIVO)", actual.getEstatus());

        Proveedor editado = new Proveedor(nombre, empresa, giro, telefono, correo, notas, estatus);
        proveedorRepo.actualizar(indice - 1, editado);
        System.out.println("Proveedor actualizado correctamente.");
    }

    private static void clasificarProveedor() {
        listarProveedores();
        List<Proveedor> proveedores = proveedorRepo.obtenerTodos();
        if (proveedores.isEmpty()) {
            return;
        }
        int indice = leerEntero("Numero de proveedor a actualizar estatus: ");
        if (indice < 1 || indice > proveedores.size()) {
            System.out.println("Indice no valido.");
            return;
        }

        Proveedor actual = proveedores.get(indice - 1);
        System.out.println("Seleccionado: " + actual.toLineaLista());
        String estatus = leerTextoObligatorio("Nuevo estatus (ACTIVO/INACTIVO): ").toUpperCase();
        actual.setEstatus(estatus);
        proveedorRepo.actualizar(indice - 1, actual);
        System.out.println("Estatus actualizado.");
    }

    // ----- Lectura y validacion basica -----

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.println("Ingresa un numero valido.");
            scanner.next();
            System.out.print(mensaje);
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer
        return valor;
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static String leerTextoObligatorio(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("Este campo es obligatorio.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    private static String leerTelefono(String mensaje) {
        String tel;
        boolean valido = false;
        do {
            System.out.print(mensaje);
            tel = scanner.nextLine().trim();
            if (tel.isEmpty()) {
                System.out.println("El telefono es obligatorio.");
                continue;
            }
            if (!tel.matches("\\d+")) {
                System.out.println("El telefono debe contener solo numeros.");
            } else {
                valido = true;
            }
        } while (!valido);
        return tel;
    }

    private static String leerTextoOpcionalConActual(String label, String actual) {
        System.out.print(label + " [" + actual + "]: ");
        String texto = scanner.nextLine().trim();
        if (texto.isEmpty()) {
            return actual;
        }
        return texto;
    }

    private static String leerTelefonoOpcionalConActual(String label, String actual) {
        System.out.print(label + " [" + actual + "]: ");
        String tel = scanner.nextLine().trim();
        if (tel.isEmpty()) {
            return actual;
        }
        if (!tel.matches("\\d+")) {
            System.out.println("Formato no valido, se mantiene el valor anterior.");
            return actual;
        }
        return tel;
    }

    private static boolean confirmar(String mensaje) {
        System.out.print(mensaje);
        String r = scanner.nextLine().trim().toLowerCase();
        return r.startsWith("s");
    }
}

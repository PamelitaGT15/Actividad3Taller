# Sistema de Agenda Empresarial – Thermo Aire y Refrigeración del Golfo

## Resumen Ejecutivo

### Descripción del Proyecto
El Sistema de Agenda Empresarial es una aplicación desarrollada en Java cuyo objetivo es digitalizar y centralizar la información de **clientes** y **proveedores** de la empresa Thermo Aire y Refrigeración del Golfo.  
La solución permite registrar, consultar, editar y depurar información mediante archivos CSV, optimizando la gestión administrativa y asegurando la continuidad operativa de los datos.

### Problema Identificado
Actualmente, la empresa administra sus contactos mediante libretas físicas, notas de WhatsApp y contactos dispersos en teléfonos celulares.  
Esto provoca:

- Pérdida o desactualización de la información  
- Falta de un respaldo confiable  
- Dificultad para identificar clientes frecuentes  
- Problemas para contactar proveedores activos  
- Riesgos operativos en la programación de servicios  

Esta problemática fue identificada directamente con el personal administrativo y técnico de la empresa.  

### Solución Propuesta
Se implementa un sistema local que permite:

- Registrar, consultar, editar y eliminar clientes y proveedores  
- Clasificar proveedores como activos/inactivos  
- Guardar y cargar datos desde archivos CSV  
- Mantener una agenda digital unificada y respaldable  
- Mejorar la precisión y acceso a la información  

La aplicación funciona en cualquier equipo con Java instalado y no requiere conexión a internet.  

### Arquitectura del Sistema
El sistema utiliza una arquitectura sencilla de **tres capas**:

1. **Capa de Presentación (UI por consola)**  
   - `AgendaApp.java`  
   - Maneja menús, interacción con el usuario y flujo principal.

2. **Capa de Modelo (Lógica de negocio)**  
   - `Cliente.java`  
   - `Proveedor.java`  
   Representan las entidades principales y sus atributos.

3. **Capa de Datos (Persistencia CSV)**  
   - Lectura y escritura en `clientes.csv` y `proveedores.csv`  
   - Uso de FileReader, FileWriter, BufferedReader, etc.

Esta arquitectura garantiza mantenimiento sencillo, modularidad y separación de responsabilidades.  

---

## Tabla de Contenidos

- [Resumen Ejecutivo](#resumen-ejecutivo)
  - [Descripción del Proyecto](#descripción-del-proyecto)
  - [Problema Identificado](#problema-identificado)
  - [Solución Propuesta](#solución-propuesta)
  - [Arquitectura del Sistema](#arquitectura-del-sistema)
- [Requerimientos](#requerimientos)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Uso](#uso)
- [Contribución](#contribución)
- [Roadmap](#roadmap)
- [Créditos](#créditos)

---


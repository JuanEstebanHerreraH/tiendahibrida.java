🛒 Tienda Web Híbrida (Java EE + JSP + Servlets + SQL Server)

Aplicación web de tienda desarrollada con Java (Jakarta EE 8), JSP, Servlets, Maven, Tomcat y SQL Server.
Incluye backend modular por capas (DAO/Modelo), frontend en JSP y conexión a base de datos mediante JDBC.

Este repositorio contiene:

Proyecto web (Maven)

Conexión con SQL Server

DAO funcionales

Servlet funcional con vista JSP

Estructura lista para ampliar catálogo, carrito, API de monedas, panel admin, etc.

🚀 Tecnologías Utilizadas
Backend

Java 22 (Se paso a Java 17)

Jakarta EE 8 (Servlet API)

Maven

Tomcat 10

JDBC

SQL Server 2019

Frontend

JSP

HTML / CSS

JSTL (opcional para futuras mejoras)

Base de Datos

SQL Server

Tablas: productos, usuarios, ventas

📂 Estructura del Proyecto
TiendaWeb/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com.tienda.modelo.entidades/
│   │   │   │   ├── Producto.java
│   │   │   │   ├── Usuario.java
│   │   │   │   └── Venta.java
│   │   │   ├── com.tienda.modelo.dao/
│   │   │   │   ├── ConexionDB.java
│   │   │   │   └── ProductoDAO.java
│   │   │   └── com.tiendawweb.controladores/
│   │   │       └── ProductoServlet.java
│   │   ├── webapp/
│   │   │   ├── index.jsp
│   │   │   └── productos.jsp
│   │   └── resources/
│   │
│   ├── test/
│
├── pom.xml
└── README.md

🗄️ Base de Datos

Tablas necesarias:

CREATE TABLE productos (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100),
    precio_usd FLOAT,
    stock INT,
    categoria VARCHAR(50),
    imagen_url VARCHAR(300)
);

CREATE TABLE usuarios (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100),
    moneda_preferida VARCHAR(5)
);

CREATE TABLE ventas (
    id INT PRIMARY KEY IDENTITY(1,1),
    id_usuario INT,
    id_producto INT,
    cantidad INT,
    fecha DATETIME,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_producto) REFERENCES productos(id)
);

🔌 Conexión con SQL Server (ConexionDB.java)
private static final String URL =
"jdbc:sqlserver://localhost:1433;databaseName=tienda_db;encrypt=false;trustServerCertificate=true;";

public static Connection getConexion() {
    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(URL);
    } catch (Exception e) {
        System.out.println("Error al conectar: " + e.getMessage());
        return null;
    }
}


El proyecto usa autenticación de Windows, por lo que no requiere usuario ni contraseña.

🌐 Servlet Principal (ProductoServlet)

Servlet encargado de consultar productos desde la BD y enviarlos a la vista JSP.

@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {

    private ProductoDAO dao = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Producto> productos = dao.listar();
        req.setAttribute("lista", productos);

        req.getRequestDispatcher("productos.jsp").forward(req, resp);
    }
}

🎨 Vista productos.jsp
<h1>Productos</h1>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Precio (USD)</th>
        <th>Stock</th>
        <th>Categoría</th>
        <th>Imagen</th>
    </tr>

    <c:forEach var="p" items="${lista}">
        <tr>
            <td>${p.id}</td>
            <td>${p.nombre}</td>
            <td>${p.precioUSD}</td>
            <td>${p.stock}</td>
            <td>${p.categoria}</td>
            <td><img src="${p.imagenURL}" width="80"></td>
        </tr>
    </c:forEach>
</table>

▶️ Cómo Ejecutar el Proyecto
1. Importar en NetBeans o IntelliJ

Abrir como proyecto Maven.

2. Instalar dependencias

Maven descarga todo automáticamente.

3. Configurar Tomcat 10

Agregar servidor → seleccionar Tomcat → iniciar.

4. Ejecutar el proyecto

En NetBeans → Run Project
o
mvn clean install → desplegar en Tomcat manualmente.

5. Abrir en navegador:
http://localhost:8080/TiendaWeb/
http://localhost:8080/TiendaWeb/productos

📌 Estado Actual

✔ Proyecto web funcionando
✔ Tomcat corriendo
✔ JSP cargado
✔ Servlet cargando productos
✔ Base SQL lista
✔ Arquitectura por capas funcional

Próximas etapas:

Carrito de compras

Login de usuario

API de monedas

Panel Admin en Swing

Seguridad y sesiones

📜 Licencia
-Juan Esteban Herrera Herrera
Este proyecto es libre para estudio y uso educativo.



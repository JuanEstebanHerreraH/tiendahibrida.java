🛒 Tienda Web Híbrida — Java EE + JSP + Servlets + SQL Server

Aplicación web de tienda construida con Java 17/22, Jakarta EE, JSP, Servlets, Maven, Tomcat 10 y SQL Server.
Incluye arquitectura en capas, autenticación, CRUD, vistas JSP, integración con base de datos y un módulo de administrador hecho con Swing (JFrame).

✔️ Proyecto sólido, moderno, estable y listo para expandir.

🚀 Tecnologías Utilizadas
🧩 Backend

Java 17 / 22

Jakarta EE (Servlet API)

JSP + JSTL

JDBC

Maven

Tomcat 10.1.x (actualizado desde Tomcat 9 para soporte Jakarta)

SQL Server 2019

🎨 Frontend

JSP

HTML5

CSS3

JSTL / Expression Language

🗄 Base de Datos

SQL Server

JDBC Driver: mssql-jdbc-13.x.jre11.jar

Windows Authentication habilitada mediante:

integratedSecurity=true

mssql-jdbc_auth-x64.dll dentro de /System32

📂 Estructura del Proyecto

Organizada y visual para GitHub:

TiendaWeb/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com.tienda.modelo.entidades/
│   │   │   │   ├── Producto.java
│   │   │   │   ├── Usuario.java
│   │   │   │   └── Venta.java
│   │   │   │
│   │   │   ├── com.tienda.modelo.dao/
│   │   │   │   ├── ConexionDB.java
│   │   │   │   ├── ProductoDAO.java
│   │   │   │   └── UsuarioDAO.java
│   │   │   │
│   │   │   ├── com.tiendawweb.controladores/
│   │   │   │   ├── Login.java
│   │   │   │   └── ProductoServlet.java
│   │   │
│   │   ├── webapp/
│   │   │   ├── index.jsp
│   │   │   ├── login.jsp
│   │   │   └── productos.jsp
│   │   │
│   │   └── resources/
│   │
│   ├── test/
│
├── pom.xml
└── README.md

🗄️ Script de Base de Datos (SQL Server)
🛍️ Tabla productos
CREATE TABLE productos (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100),
    precio_usd FLOAT,
    stock INT,
    categoria VARCHAR(50),
    imagen_url VARCHAR(300)
);

👤 Tabla usuarios
CREATE TABLE usuarios (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100),
    moneda_preferida VARCHAR(5)
);

🧾 Tabla ventas
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

Autenticación de Windows habilitada:

private static final String URL =
    "jdbc:sqlserver://localhost:1433;" +
    "databaseName=tienda_db;" +
    "encrypt=false;" +
    "trustServerCertificate=true;" +
    "integratedSecurity=true;";

public static Connection getConexion() {
    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(URL);
    } catch (Exception e) {
        System.out.println("Error al conectar: " + e.getMessage());
        return null;
    }
}

✔ Requisitos:

Agregar mssql-jdbc-13.x.jre11.jar a:

Apache Tomcat 10.1.x / lib


Agregar mssql-jdbc_auth-x64.dll a:

C:\Windows\System32

🌐 Controladores (Servlets)
✨ LoginServlet (Jakarta EE)
@WebServlet("/login")
public class Login extends HttpServlet {

    private final UsuarioDAO dao = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Usuario u = dao.login(email, password);

        if (u != null) {
            request.getSession().setAttribute("usuario", u);
            response.sendRedirect("listarProductos");
        } else {
            request.setAttribute("error", "Correo o contraseña incorrectos");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}

🎨 Vista JSP – productos.jsp
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

⚙️ Cambio de Tomcat (Muy Importante)

Este proyecto se migró de:

❌ Tomcat 9 (usa javax)
⬇️
✅ Tomcat 10.1.x (usa jakarta)

Por eso todo el proyecto ahora funciona con:

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


Y no con javax.

▶️ Cómo Ejecutar el Proyecto
1️⃣ Importar el proyecto

Abrir en NetBeans o IntelliJ como proyecto Maven.

2️⃣ Instalar dependencias

Maven las descarga automáticamente.

3️⃣ Configurar Tomcat 10

En NetBeans → Services → Servers
Agregar Tomcat 10.1.x
Configurar este proyecto ahí.

4️⃣ Ejecutar
mvn clean install

5️⃣ Abrir en navegador
http://localhost:8080/TiendaWeb/
http://localhost:8080/TiendaWeb/productos
http://localhost:8080/TiendaWeb/login

📌 Estado Actual del Proyecto

✔ Arquitectura en capas
✔ JSP + Servlets funcionando
✔ CRUD de productos
✔ Login (en corrección final)
✔ Conexión SQL Server
✔ Tomcat actualizado
✔ Proyecto estable y expandible

🧩 Próximos Módulos

🔐 Login 100% funcional
🧺 Carrito de compras
💱 API de moneda (USD → COP)
📊 Panel admin Swing
🛡 Seguridad
🧾 Módulo de ventas

📜 Licencia

Juan Esteban Herrera Herrera
Libre para estudio, aprendizaje y uso educativo.

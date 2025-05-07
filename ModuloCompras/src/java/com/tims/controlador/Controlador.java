package com.tims.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;
import com.tims.modelo.Producto;
import com.tims.modeloDAO.ProductoDAO;
import com.tims.modelo.Carrito;
import com.tims.modelo.Cliente;
import com.tims.modeloDAO.ClienteDao;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpSession;

public class Controlador extends HttpServlet {
    
    ProductoDAO pdao = new ProductoDAO();
    Producto p = new Producto();
    List<Producto> productos = new ArrayList<>();
    List<Carrito> listaCarrito = new ArrayList<>();
    ClienteDao cdao = new ClienteDao();
    Cliente cliente = new Cliente();
    
    int item;
    double totalPagar = 0.0;
    int cantidad = 1;
    
    int idp;
    Carrito car;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        productos = pdao.listar();
        switch (accion) {
            case "Comprar":
                totalPagar = 0.0;
                idp = Integer.parseInt(request.getParameter("id"));
                p = pdao.listarId(idp);
                item = item+1;
                car = new Carrito();
                car.setItem(item);
                car.setIdProducto(p.getId());
                car.setNombres(p.getNombres());
                car.setDescripcion(p.getDescripcion());
                car.setPrecioCompra(p.getPrecio());
                car.setCantidad(cantidad);
                car.setSubTotal(cantidad*p.getPrecio());
                listaCarrito.add(car);
                for (int i = 0; i < listaCarrito.size(); i++){
                    totalPagar = totalPagar + listaCarrito.get(i).getSubTotal();
                }
                request.setAttribute("totalPagar", totalPagar);
                request.setAttribute("carrito", listaCarrito);
                request.setAttribute("contador", listaCarrito.size());
                request.getRequestDispatcher("carrito.jsp").forward(request,response);
                break;
            case "AgregarCarrito":
                int pos = 0;
                cantidad = 1;
                idp = Integer.parseInt(request.getParameter("id"));
                p = pdao.listarId(idp);
                if (listaCarrito.size()>0){
                    for(int i = 0; i < listaCarrito.size(); i++){
                        if(idp == listaCarrito.get(i).getIdProducto()){
                            pos=i;
                        }
                    }
                    if(idp == listaCarrito.get(pos).getIdProducto()){
                        cantidad = listaCarrito.get(pos).getCantidad()+cantidad;
                        double subtotal = listaCarrito.get(pos).getPrecioCompra()*cantidad;
                        listaCarrito.get(pos).setCantidad(cantidad);
                        listaCarrito.get(pos).setSubTotal(subtotal);
                    } else {
                        item = item+1;
                        car = new Carrito();
                        car.setItem(item);
                        car.setIdProducto(p.getId());
                        car.setNombres(p.getNombres());
                        car.setDescripcion(p.getDescripcion());
                        car.setPrecioCompra(p.getPrecio());
                        car.setCantidad(cantidad);
                        car.setSubTotal(cantidad*p.getPrecio());
                        listaCarrito.add(car);
                    }
                } else {
                    item = item+1;
                    car = new Carrito();
                    car.setItem(item);
                    car.setIdProducto(p.getId());
                    car.setNombres(p.getNombres());
                    car.setDescripcion(p.getDescripcion());
                    car.setPrecioCompra(p.getPrecio());
                    car.setCantidad(cantidad);
                    car.setSubTotal(cantidad*p.getPrecio());
                    listaCarrito.add(car);
                }
                request.setAttribute("contador", listaCarrito.size());
                request.getRequestDispatcher("Controlador?accion=home").forward(request, response);
                break;
            case "Delete":
                int idproducto = Integer.parseInt(request.getParameter("idp"));
                for (int i = 0; i < listaCarrito.size(); i++){
                    if(listaCarrito.get(i).getIdProducto()==idproducto){
                        listaCarrito.remove(i);
                    }
                }
                break;
            case "ActualizarCantidad":
                int idpro = Integer.parseInt(request.getParameter("idp"));
                int cant = Integer.parseInt(request.getParameter("Cantidad"));
                for(int i = 0; i < listaCarrito.size(); i++){
                    if(listaCarrito.get(i).getIdProducto()==idpro){
                        listaCarrito.get(i).setCantidad(cant);
                        double st = listaCarrito.get(i).getPrecioCompra()*cant;
                        listaCarrito.get(i).setSubTotal(st);
                    }
                }
                break;
            case "Carrito":
                totalPagar = 0.0;
                request.setAttribute("carrito", listaCarrito);
                for (int i = 0; i < listaCarrito.size(); i++){
                    totalPagar = totalPagar + listaCarrito.get(i).getSubTotal();
                }
                request.setAttribute("totalPagar", totalPagar);
                request.getRequestDispatcher("carrito.jsp").forward(request, response);
                break;
            case "Login":
                String email = request.getParameter("txtEmail");
                String password = request.getParameter("txtPassword");

                cliente = cdao.validar(email, password);

                if (cliente != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("cliente", cliente);
                    request.setAttribute("productos", productos);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Correo o contraseña incorrectos.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
                break;
            case "Registrar":
                cliente.setDni(request.getParameter("txtDni"));
                cliente.setNombre(request.getParameter("txtNombres"));
                cliente.setDireccion(request.getParameter("txtDireccion"));
                cliente.setCorreo(request.getParameter("txtEmail"));
                cliente.setPassword(request.getParameter("txtPassword"));

                boolean registrado = cdao.registrar(cliente);
                if (registrado) {
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "No se pudo registrar. Inténtalo de nuevo.");
                    request.getRequestDispatcher("registro.jsp").forward(request, response);
                }
                break;
            case "Logout":
                request.getSession().invalidate();
                response.sendRedirect("index.jsp");
                break;
            case "Buscar":
                String nombre = request.getParameter("txtBuscar");
                List<Producto> productosBuscados = pdao.buscar(nombre);
                request.setAttribute("productos", productosBuscados);
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
            default:
                request.setAttribute("productos", productos);
                request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}

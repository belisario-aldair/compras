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
import com.tims.modelo.ProductoDAO;
import com.tims.modelo.Carrito;

public class Controlador extends HttpServlet {
    
    ProductoDAO pdao = new ProductoDAO();
    Producto p = new Producto();
    List<Producto> productos = new ArrayList<>();
    List<Carrito> listaCarrito = new ArrayList<>();
    
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
            case "Carrito":
                totalPagar = 0.0;
                request.setAttribute("carrito", listaCarrito);
                for (int i = 0; i < listaCarrito.size(); i++){
                    totalPagar = totalPagar + listaCarrito.get(i).getSubTotal();
                }
                request.setAttribute("totalPagar", totalPagar);
                request.getRequestDispatcher("carrito.jsp").forward(request, response);
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

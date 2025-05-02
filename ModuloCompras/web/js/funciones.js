$(document).ready(function(){
    $("tr #btnDelete").click(function(e){
        e.preventDefault(); // Evita redireccionamiento por <a href="#">
        var idp = $(this).closest("tr").find("#idp").val(); // busca dentro del <tr>

        Swal.fire({
            title: "¿Estás seguro?",
            text: "¡Esto eliminará el producto del carrito!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Sí, eliminar"
        }).then((result) => {
            if (result.isConfirmed) {
                eliminar(idp); // 👈 aquí sí llamamos a la función
            }
        });
    });

    function eliminar(idp){
        $.ajax({
            type: 'POST',
            url: "Controlador?accion=Delete",
            data: { idp: idp },
            success: function (data) {
                Swal.fire("¡Eliminado!", "El producto fue eliminado.", "success")
                .then(() => {
                    location.reload(); // recarga para reflejar los cambios
                });
            },
            error: function(){
                Swal.fire("Error", "No se pudo eliminar el producto", "error");
            }
        });
    }
});

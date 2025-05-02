$(document).ready(function(){
    $("tr #btnDelete").click(function(e){
        e.preventDefault();
        var idp = $(this).closest("tr").find("#idp").val(); 

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
                eliminar(idp);
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
                    location.reload();
                });
            },
            error: function(){
                Swal.fire("Error", "No se pudo eliminar el producto", "error");
            }
        });
    }
});

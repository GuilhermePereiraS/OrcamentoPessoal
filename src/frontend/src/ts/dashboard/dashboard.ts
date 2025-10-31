import '../main.ts';
import Swal from 'sweetalert2';



export const Dashboard = {
    showSuccess(msg: string) {
        Swal.fire({
            title: 'Sucesso!',
            text: msg,
            icon: 'success',
            confirmButtonText: 'Ok'
        });
    },

    showError(msg: string) {
        Swal.fire({
            title: 'Erro!',
            text: msg,
            icon: 'error',
            confirmButtonText: 'Ok'
        });
    }
};

// torna global
(window as any).Dashboard = Dashboard;

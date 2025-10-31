// function validaForm(form) {
//     let nome = form["nome"].value
//     let login = form["login"].value
//     let dt_nascimento = form["dt_nascimento"].value
//     let senha = form["senha"].value
//
//
//     if (estaVazioOuNulo(nome)) {
//         Swal.fire({
//             title: 'Atenção!',
//             text: "Deve escrever o nome",
//             icon: 'error',
//             confirmButtonText: 'Ok'
//         })
//
//         return false;
//     }
//     if (estaVazioOuNulo(login)) {
//         Swal.fire({
//             title: 'Atenção!',
//             text: "Deve escrever o login",
//             icon: 'error',
//             confirmButtonText: 'Ok'
//         })
//         return false;
//     }if (estaVazioOuNulo(dt_nascimento)) {
//         Swal.fire({
//             title: 'Atenção!',
//             text: "Deve escrever a data de nascimento",
//             icon: 'error',
//             confirmButtonText: 'Ok'
//         })
//         return false;
//     }
//     if (estaVazioOuNulo(senha)) {
//         Swal.fire({
//             title: 'Atenção!',
//             text: "Deve escrever a senha",
//             icon: 'error',
//             confirmButtonText: 'Ok'
//         })
//         return false;
//     }
//
//     if (nome.length <= 3) {
//         Swal.fire({
//             title: 'Atenção!',
//             text: "Nome deve ter pelo menos quatro caracteres",
//             icon: 'error',
//             confirmButtonText: 'Ok'
//         })
//         return false;
//     }
//     if (senha.length <= 4) {
//         Swal.fire({
//             title: 'Atenção!',
//             text: "Senha deve ter pelo menos 5 caracteres",
//             icon: 'error',
//             confirmButtonText: 'Ok'
//         })
//         return false;
//     }
// }
//
// function estaVazioOuNulo(string) {
//     if (string === "" || string == null) {
//         return true
//     }
// }


interface FormElement {
    value: string;
}

interface UserForm extends HTMLFormElement {
    nome: FormElement;
    login: FormElement;
    dt_nascimento: FormElement;
    senha: FormElement;
}

function estaVazioOuNulo(str: string | null | undefined): boolean {
    if (str === "" || str === null || str === undefined || (typeof str === 'string' && str.trim() === "")) {
        return true;
    }
    return false;
}

function validaForm(form: UserForm): boolean {
    const nome: string = form["nome"].value;
    const login: string = form["login"].value;
    const dt_nascimento: string = form["dt_nascimento"].value;
    const senha: string = form["senha"].value;

    if (estaVazioOuNulo(nome)) {
        // Swal.fire({
        //     title: 'Atenção!',
        //     text: "Deve escrever o nome",
        //     icon: 'error',
        //     confirmButtonText: 'Ok'
        // });
        // return false;
        window.Dashboard.showError("O nome é obrigatório!");
        return false;
    }

    // if (estaVazioOuNulo(login)) {
    //     Swal.fire({
    //         title: 'Atenção!',
    //         text: "Deve escrever o login",
    //         icon: 'error',
    //         confirmButtonText: 'Ok'
    //     });
    //     return false;
    // }
    //
    // if (estaVazioOuNulo(dt_nascimento)) {
    //     Swal.fire({
    //         title: 'Atenção!',
    //         text: "Deve escrever a data de nascimento",
    //         icon: 'error',
    //         confirmButtonText: 'Ok'
    //     });
    //     return false;
    // }
    //
    // if (estaVazioOuNulo(senha)) {
    //     Swal.fire({
    //         title: 'Atenção!',
    //         text: "Deve escrever a senha",
    //         icon: 'error',
    //         confirmButtonText: 'Ok'
    //     });
    //     return false;
    // }
    //
    // if (nome.length <= 3) {
    //     Swal.fire({
    //         title: 'Atenção!',
    //         text: "Nome deve ter pelo menos quatro caracteres",
    //         icon: 'error',
    //         confirmButtonText: 'Ok'
    //     });
    //     return false;
    // }
    //
    // if (senha.length <= 4) {
    //     Swal.fire({
    //         title: 'Atenção!',
    //         text: "Senha deve ter pelo menos 5 caracteres",
    //         icon: 'error',
    //         confirmButtonText: 'Ok'
    //     });
    //     return false;
    // }

    return true;
}
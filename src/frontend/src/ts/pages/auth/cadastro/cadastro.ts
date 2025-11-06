// Tipagem do formulário HTML
import Swal from "sweetalert2";

interface FormCampos extends HTMLFormElement {
    nome: HTMLInputElement;
    login: HTMLInputElement;
    dt_nascimento: HTMLInputElement;
    senha: HTMLInputElement;
}

function validaForm(form: FormCampos): boolean {
    const nome = form.nome.value;
    const login = form.login.value;
    const dt_nascimento = form.dt_nascimento.value;
    const senha = form.senha.value;

    if (estaVazioOuNulo(nome)) {
        exibirErro("Deve escrever o nome");
        return false;
    }

    if (estaVazioOuNulo(login)) {
        exibirErro("Deve escrever login");
        return false;
    }

    if (estaVazioOuNulo(dt_nascimento)) {
        exibirErro("Deve escrever a data de nascimento");
        return false;
    }

    if (estaVazioOuNulo(senha)) {
        exibirErro("Deve escrever a senha");
        return false;
    }

    const hoje = new Date();
    const dataNascimento = new Date(`${dt_nascimento}T00:00:00`);

    hoje.setHours(0, 0, 0, 0);

    if (dataNascimento >= hoje) {
        exibirErro("A data de nascimento deve ser no passado");
        return false;
    }

    if (nome.length < 4) {
        exibirErro("Nome deve ter pelo menos quatro caracteres");
        return false;
    }

    if (login.length < 4) {
        exibirErro("Login deve ter pelo menos quatro caracteres");
        return false;
    }

    if (senha.length < 5) {
        exibirErro("Senha deve ter pelo menos cinco caracteres");
        return false;
    }

    // const regexCaracterEspecial = /[^a-zA-Z0-9]/;
    // if (!regexCaracterEspecial.test(senha)) {
    //     exibirErro("A senha deve conter pelo menos um caracter especial");
    //     return false;
    // }

    return true;
}

function exibirErro(mensagem: string): void {
    Swal.fire({
        title: 'Atenção!',
        text: mensagem,
        icon: 'warning',
        confirmButtonText: 'Ok'
    });
}

function estaVazioOuNulo(str?: string): boolean {
    return !str || str.trim() === "";
}



// --- A SOLUÇÃO "ÂNCORA" ---
// Espera o HTML ser carregado
document.addEventListener('DOMContentLoaded', () => {
    
    // Encontra o formulário pelo ID que colocamos no HTML
    const form = document.getElementById('cadastro-form') as UserForm;

    if (form) {
        // "Liga" a função ao evento de 'submit' do formulário
        form.addEventListener('submit', (event: SubmitEvent) => {
            
            // Roda a sua função de validação
            // @ts-ignore
            const isFormValid = validaForm(form);
            
            if (!isFormValid) {
                // Se for inválido, previne o envio do formulário
                event.preventDefault();
            }
            // Se for válido (true), o evento continua e o formulário é enviado.
        });
    } else {
        console.error("Formulário #cadastro-form não foi encontrado. Verifique o ID no HTML.");
    }
});
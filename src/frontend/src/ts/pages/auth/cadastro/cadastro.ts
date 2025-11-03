// --- SUAS INTERFACES (Serão removidas pelo build, está correto) ---
interface FormElement {
    value: string;
}

interface UserForm extends HTMLFormElement {
    nome: FormElement;
    login: FormElement;
    dt_nascimento: FormElement;
    senha: FormElement;
}

// --- SUAS FUNÇÕES (Agora serão mantidas pelo build) ---
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
        window.Dashboard.showError("Deve escrever o nome");
        return false;
    }

    if (estaVazioOuNulo(login)) {
        window.Dashboard.showError("Deve escrever o login");
        return false;
    }

    if (estaVazioOuNulo(dt_nascimento)) {
        window.Dashboard.showError("Deve escrever a data de nascimento");
        return false;
    }

    if (estaVazioOuNulo(senha)) {
        window.Dashboard.showError("Deve escrever a senha");
        return false;
    }

    if (nome.length <= 3) {
        window.Dashboard.showError("Nome deve ter pelo menos quatro caracteres");
        return false;
    }

    if (senha.length <= 4) {
        window.Dashboard.showError("Senha deve ter pelo menos 5 caracteres");
        return false;
    }

    return true; // Sucesso!
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
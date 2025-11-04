// Obtém o modal com tipagem correta
const modalExtrato = document.getElementById("modalTransacaoAdicionar") as HTMLElement | null;

// Tipos para os dados vindos do backend
interface Categoria {
    id_tipo_gasto: number;
    descricao: string;
}

interface FormaPagamento {
    id_forma_pgmt: number;
    descricao: string;
}

if (modalExtrato) {
    modalExtrato.addEventListener("show.bs.modal", async () => {
        const form = document.forms[0];
        const selectCategoria = form.querySelector("#categoria") as HTMLSelectElement;
        const selectFormaPagamento = form.querySelector("#formaPagamento") as HTMLSelectElement;

        try {
            const listaCategoria: Categoria[] = await (await fetch("/pegaListaTipoGasto")).json();
            const listaFormaPagamento: FormaPagamento[] = await (await fetch("/pegaListaFormaPagamento")).json();

            // Limpa as opções antes de popular
            selectCategoria.innerHTML = "";
            selectFormaPagamento.innerHTML = "";

            const opcao1 = document.createElement("option");
            const opcao2 = document.createElement("option");

            opcao1.textContent = "Selecione";
            opcao2.textContent = "Selecione";

            selectFormaPagamento.appendChild(opcao1);
            selectCategoria.appendChild(opcao2);

            listaCategoria.forEach((categoria) => {
                const opcao = document.createElement("option");
                opcao.value = categoria.id_tipo_gasto.toString();
                opcao.textContent = categoria.descricao;
                selectCategoria.appendChild(opcao);
            });

            listaFormaPagamento.forEach((formaPagamento) => {
                const opcao = document.createElement("option");
                opcao.value = formaPagamento.id_forma_pgmt.toString();
                opcao.textContent = formaPagamento.descricao;
                selectFormaPagamento.appendChild(opcao);
            });
        } catch (error) {
            console.error("Erro ao carregar listas:", error);
        }
    });
}

function exibeOpcoesModalExtrato(): void {
    const form = document.forms[0];
    const valorTipoTransacao = (form["tp_transacao"] as HTMLSelectElement).value;
    const selectCategoria = form.querySelector("#categoria") as HTMLSelectElement;
    const selectFormaPagamento = form.querySelector("#formaPagamento") as HTMLSelectElement;
    const inputDescricao = form["descricao"] as HTMLInputElement;


    console.log("aaaaa");
    const isEntrada = valorTipoTransacao === "entrada";

    inputDescricao.value = isEntrada ? "Entrada" : "";

    inputDescricao.readOnly = isEntrada
    selectCategoria.disabled = isEntrada;
    selectFormaPagamento.disabled = isEntrada;
}

(window as any).exibeOpcoes = exibeOpcoes;

// Obtém o modal com tipagem correta
const modal = document.getElementById("modalTransacao") as HTMLElement | null;

// Tipos para os dados vindos do backend
interface Categoria {
    id_tipo_gasto: number;
    descricao: string;
}

interface FormaPagamento {
    id_forma_pgmt: number;
    descricao: string;
}

if (modal) {
    modal.addEventListener("show.bs.modal", async () => {
        const form = document.forms[0];
        const selectCategoria = form.querySelector("#categoria") as HTMLSelectElement;
        const selectFormaPagamento = form.querySelector("#formaPagamento") as HTMLSelectElement;

        try {
            const listaCategoria: Categoria[] = await (await fetch("/pegaListaTipoGasto")).json();
            const listaFormaPagamento: FormaPagamento[] = await (await fetch("/pegaListaFormaPagamento")).json();

            // Limpa as opções antes de popular
            selectCategoria.innerHTML = "";
            selectFormaPagamento.innerHTML = "";

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

function exibeOpcoes(): void {
    const form = document.forms[0];
    const valorTipoTransacao = (form["tp_transacao"] as HTMLSelectElement).value;
    const selectCategoria = form.querySelector("#categoria") as HTMLSelectElement;
    const selectFormaPagamento = form.querySelector("#formaPagamento") as HTMLSelectElement;

    const isEntrada = valorTipoTransacao === "entrada";
    selectCategoria.disabled = isEntrada;
    selectFormaPagamento.disabled = isEntrada;
}

(window as any).exibeOpcoes = exibeOpcoes;

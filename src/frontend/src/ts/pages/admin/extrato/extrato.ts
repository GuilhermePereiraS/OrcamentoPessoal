// ================== INTERFACES ==================
interface Item {
    id_item?: number;
    id_extrato?: number;
    nome: string;
    quantidade: number;
    vl_unitario: number;
    vl_total: number;
}

interface Extrato {
    id_extrato: number;
    descricao: string;
    tp_transacao: 'entrada' | 'saida';
    id_tipo_gasto: number;
    id_forma_pgmt: number;
    itens: Item[];
}

// ================== FUNÇÃO COMUM PARA CRIAR ITEM ==================
function criarItem(item: Item, container: HTMLDivElement, atualizarTotalFn: () => void) {
    const div = document.createElement('div');
    div.classList.add('d-flex', 'gap-2', 'align-items-center', 'mb-2');

    // hidden id_item
    if (item.id_item !== undefined) {
        const hiddenId = document.createElement('input');
        hiddenId.type = 'hidden';
        hiddenId.name = 'id_item';
        hiddenId.value = item.id_item.toString();
        div.appendChild(hiddenId);
    }

    const nomeInput = document.createElement('input');
    nomeInput.type = 'text';
    nomeInput.value = item.nome ?? '';
    nomeInput.placeholder = 'Nome do item';
    nomeInput.classList.add('form-control');

    const quantidadeInput = document.createElement('input');
    quantidadeInput.type = 'number';
    quantidadeInput.value = (item.quantidade ?? 1).toString();
    quantidadeInput.min = '1';
    quantidadeInput.classList.add('form-control', 'w-25');

    const valorUnitarioInput = document.createElement('input');
    valorUnitarioInput.type = 'number';
    valorUnitarioInput.value = (item.vl_unitario ?? 0).toFixed(2);
    valorUnitarioInput.min = '0';
    valorUnitarioInput.step = '0.01';
    valorUnitarioInput.classList.add('form-control', 'w-25');

    const vlTotalSpan = document.createElement('span');
    vlTotalSpan.classList.add('vlTotalItem');
    vlTotalSpan.textContent = ((item.vl_unitario ?? 0) * (item.quantidade ?? 1)).toFixed(2);

    const btnRemover = document.createElement('button');
    btnRemover.type = 'button';
    btnRemover.textContent = '-';
    btnRemover.classList.add('btn', 'btn-danger');

    const atualizar = () => {
        const quantidade = parseInt(quantidadeInput.value) || 0;
        const valor = parseFloat(valorUnitarioInput.value) || 0;
        vlTotalSpan.textContent = (quantidade * valor).toFixed(2);
        atualizarTotalFn();
    };

    quantidadeInput.addEventListener('input', atualizar);
    valorUnitarioInput.addEventListener('input', atualizar);

    btnRemover.addEventListener('click', () => {
        div.remove();
        atualizarTotalFn();
    });

    div.append(nomeInput, quantidadeInput, valorUnitarioInput, vlTotalSpan, btnRemover);
    container.appendChild(div);
}

// ================== FUNÇÃO COMUM PARA ATUALIZAR TOTAL ==================
function atualizarTotal(container: HTMLDivElement, totalEl: HTMLElement, inputItens: HTMLInputElement, inputVlTransacao: HTMLInputElement) {
    let total = 0;
    const itens: Item[] = [];

    container.querySelectorAll('div').forEach(div => {
        const inputs = Array.from(div.querySelectorAll('input')).filter(i => (i as HTMLInputElement).type !== 'hidden');
        if (inputs.length < 2) return; // evita erros
        const nome = (inputs[0] as HTMLInputElement).value;
        const quantidade = parseInt((inputs[1] as HTMLInputElement).value) || 0;
        const vl_unitario = parseFloat((inputs[2] as HTMLInputElement).value) || 0;
        const vl_total = quantidade * vl_unitario;
        total += vl_total;

        const id_itemInput = div.querySelector<HTMLInputElement>('input[name="id_item"]');
        const id_item = id_itemInput ? parseInt(id_itemInput.value) : undefined;

        itens.push({ id_item, nome, quantidade, vl_unitario, vl_total });
    });

    totalEl.textContent = `R$ ${total.toFixed(2)}`;
    inputVlTransacao.value = total.toFixed(2);
    inputItens.value = JSON.stringify(itens);
}

// ================== MODAL ADICIONAR ==================
const modalAdicionar = document.getElementById('modalTransacaoAdicionar') as HTMLElement;
const listaItensAdicionar = modalAdicionar.querySelector('#listaItensAdicionar') as HTMLDivElement;
const totalAdicionar = modalAdicionar.querySelector('#totalTransacaoAdicionar') as HTMLElement;
const btnAdicionarItemAdicionar = modalAdicionar.querySelector('#btnAdicionarItemAdicionar') as HTMLButtonElement;
const formAdicionar = modalAdicionar.querySelector('form') as HTMLFormElement;

// Hidden inputs
let inputItensAdicionar = modalAdicionar.querySelector<HTMLInputElement>('input[name="listaItensJson"]');
if (!inputItensAdicionar) {
    inputItensAdicionar = document.createElement('input');
    inputItensAdicionar.type = 'hidden';
    inputItensAdicionar.name = 'listaItensJson';
    formAdicionar.appendChild(inputItensAdicionar);
}

let inputVlTransacaoAdicionar = modalAdicionar.querySelector<HTMLInputElement>('input[name="vl_transacao"]');
if (!inputVlTransacaoAdicionar) {
    inputVlTransacaoAdicionar = document.createElement('input');
    inputVlTransacaoAdicionar.type = 'hidden';
    inputVlTransacaoAdicionar.name = 'vl_transacao';
    formAdicionar.appendChild(inputVlTransacaoAdicionar);
}

// Adicionar item
btnAdicionarItemAdicionar.addEventListener('click', () => {
    criarItem({ nome: '', quantidade: 1, vl_unitario: 0, vl_total: 0 }, listaItensAdicionar, () =>
        atualizarTotal(listaItensAdicionar, totalAdicionar, inputItensAdicionar!, inputVlTransacaoAdicionar!)
    );
});

// Atualiza total antes de enviar
formAdicionar.addEventListener('submit', () => {
    atualizarTotal(listaItensAdicionar, totalAdicionar, inputItensAdicionar!, inputVlTransacaoAdicionar!);
});

// ================== MODAL EDITAR ==================
const modalEditar = document.getElementById('modalTransacaoEditar') as HTMLElement;
const listaItensEditar = modalEditar.querySelector('#listaItensEditar') as HTMLDivElement;
const totalEditar = modalEditar.querySelector('#totalTransacaoEditar') as HTMLElement;
const formEditar = modalEditar.querySelector('form') as HTMLFormElement;

// Hidden inputs
let inputItensEditar = modalEditar.querySelector<HTMLInputElement>('input[name="listaItensJson"]');
if (!inputItensEditar) {
    inputItensEditar = document.createElement('input');
    inputItensEditar.type = 'hidden';
    inputItensEditar.name = 'listaItensJson';
    formEditar.appendChild(inputItensEditar);
}

let inputVlTransacaoEditar = modalEditar.querySelector<HTMLInputElement>('input[name="vl_transacao"]');
if (!inputVlTransacaoEditar) {
    inputVlTransacaoEditar = document.createElement('input');
    inputVlTransacaoEditar.type = 'hidden';
    inputVlTransacaoEditar.name = 'vl_transacao';
    formEditar.appendChild(inputVlTransacaoEditar);
}

// Botão de adicionar item
const btnAdicionarItemEditar = modalEditar.querySelector('#btnAdicionarItemEditar') as HTMLButtonElement;
btnAdicionarItemEditar.addEventListener('click', () => {
    criarItem({ nome: '', quantidade: 1, vl_unitario: 0, vl_total: 0 }, listaItensEditar, () =>
        atualizarTotal(listaItensEditar, totalEditar, inputItensEditar!, inputVlTransacaoEditar!)
    );
});

// Atualiza total antes de enviar
formEditar.addEventListener('submit', () => {
    atualizarTotal(listaItensEditar, totalEditar, inputItensEditar!, inputVlTransacaoEditar!);
});

// Abrir modal editar
document.querySelectorAll('[data-bs-target="#modalTransacaoEditar"]').forEach(div => {
    div.addEventListener('click', () => {
        const elemento = div as HTMLElement;

        const idExtrato = elemento.dataset.id_extrato ?? '';
        const descricao = elemento.dataset.descricao ?? '';
        const tpTransacao = elemento.dataset.tptransacao ?? 'saida';
        const idTipoGasto = elemento.dataset.idtipogasto ?? '';
        const idFormaPgmt = elemento.dataset.idformapgmt ?? '';
        const itensJson = elemento.dataset.items ?? '[]';

        // Preenche campos do modal
        (document.getElementById('extratoId') as HTMLInputElement).value = idExtrato;
        (document.getElementById('descricaoEditar') as HTMLInputElement).value = descricao;
        (document.getElementById('tpTransacaoEditar') as HTMLSelectElement).value = tpTransacao;
        (document.getElementById('tp_transacao_hidden') as HTMLSelectElement).value = tpTransacao;
        (document.getElementById('categoriaEditar') as HTMLSelectElement).value = idTipoGasto;
        (document.getElementById('formaPagamentoEditar') as HTMLSelectElement).value = idFormaPgmt;

        const campoItemsEditar = document.getElementById("campoItemsEditar") as HTMLDivElement;
        campoItemsEditar.style.display = tpTransacao === "entrada" ? "none" : "block";

        // Limpa itens anteriores
        listaItensEditar.innerHTML = '';

        // Cria itens do JSON com atualização de total
        try {
            const itens: Item[] = JSON.parse(itensJson);
            itens.forEach(item => {
                criarItem(item, listaItensEditar, () =>
                    atualizarTotal(listaItensEditar, totalEditar, inputItensEditar!, inputVlTransacaoEditar!)
                );
            });
        } catch (e) {
            console.error('Erro ao parsear JSON de itens:', e, itensJson);
        }

        // Atualiza total depois de criar todos os itens
        atualizarTotal(listaItensEditar, totalEditar, inputItensEditar!, inputVlTransacaoEditar!);
    });
});

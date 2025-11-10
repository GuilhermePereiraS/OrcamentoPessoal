// ================== INTERFACES ==================
interface Item {
    id_item?: number;
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
function criarItem(item: Item, container: HTMLDivElement, atualizarTotal: () => void) {
    const div = document.createElement('div');
    div.classList.add('d-flex', 'gap-2', 'align-items-center', 'mb-2');

    const nomeInput = document.createElement('input');
    nomeInput.type = 'text';
    nomeInput.value = item.nome;
    nomeInput.placeholder = 'Nome do item';
    nomeInput.classList.add('form-control');

    const quantidadeInput = document.createElement('input');
    quantidadeInput.type = 'number';
    quantidadeInput.value = item.quantidade.toString();
    quantidadeInput.min = '1';
    quantidadeInput.classList.add('form-control', 'w-25');

    const valorUnitarioInput = document.createElement('input');
    valorUnitarioInput.type = 'number';
    valorUnitarioInput.value = item.vl_unitario.toFixed(2);
    valorUnitarioInput.min = '0';
    valorUnitarioInput.step = '0.01';
    valorUnitarioInput.classList.add('form-control', 'w-25');

    const vlTotalSpan = document.createElement('span');
    vlTotalSpan.classList.add('vlTotalItem');
    vlTotalSpan.textContent = (item.vl_unitario * item.quantidade).toFixed(2);

    const btnRemover = document.createElement('button');
    btnRemover.type = 'button';
    btnRemover.textContent = '-';
    btnRemover.classList.add('btn', 'btn-danger');

    const atualizar = () => {
        const quantidade = parseInt(quantidadeInput.value) || 0;
        const valor = parseFloat(valorUnitarioInput.value) || 0;
        vlTotalSpan.textContent = (quantidade * valor).toFixed(2);
        atualizarTotal();
    };

    quantidadeInput.addEventListener('input', atualizar);
    valorUnitarioInput.addEventListener('input', atualizar);

    btnRemover.addEventListener('click', () => {
        div.remove();
        atualizarTotal();
    });

    div.append(nomeInput, quantidadeInput, valorUnitarioInput, vlTotalSpan, btnRemover);
    container.appendChild(div);
    atualizarTotal();
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

// Botão de adicionar item
btnAdicionarItemAdicionar.addEventListener('click', () => {
    criarItem({ nome: '', quantidade: 1, vl_unitario: 0, vl_total: 0 }, listaItensAdicionar, atualizarTotalAdicionar);
});

// Atualiza total e hidden
function atualizarTotalAdicionar() {
    let total = 0;
    const itens: Item[] = [];

    listaItensAdicionar.querySelectorAll('div').forEach(div => {
        const inputs = div.querySelectorAll('input');
        const nome = (inputs[0] as HTMLInputElement).value;
        const quantidade = parseInt((inputs[1] as HTMLInputElement).value) || 0;
        const vl_unitario = parseFloat((inputs[2] as HTMLInputElement).value) || 0;
        const vl_total = quantidade * vl_unitario;
        total += vl_total;
        itens.push({ nome, quantidade, vl_unitario, vl_total });
    });

    totalAdicionar.textContent = `R$ ${total.toFixed(2)}`;
    inputVlTransacaoAdicionar!.value = total.toFixed(2);
    inputItensAdicionar!.value = JSON.stringify(itens);
}

// Atualiza hidden antes de enviar
formAdicionar.addEventListener('submit', () => {
    atualizarTotalAdicionar();
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
    criarItem({ nome: '', quantidade: 1, vl_unitario: 0, vl_total: 0 }, listaItensEditar, atualizarTotalEditar);
});

// Atualiza total e hidden
function atualizarTotalEditar() {
    let total = 0;
    const itens: Item[] = [];
    listaItensEditar.querySelectorAll('div').forEach(div => {
        const inputs = div.querySelectorAll('input');
        const nome = (inputs[0] as HTMLInputElement).value;
        const quantidade = parseInt((inputs[1] as HTMLInputElement).value) || 0;
        const vl_unitario = parseFloat((inputs[2] as HTMLInputElement).value) || 0;
        const vl_total = quantidade * vl_unitario;
        total += vl_total;
        itens.push({ nome, quantidade, vl_unitario, vl_total });
    });

    totalEditar.textContent = `R$ ${total.toFixed(2)}`;
    inputVlTransacaoEditar!.value = total.toFixed(2);
    inputItensEditar!.value = JSON.stringify(itens);
}

// Atualiza hidden antes de enviar
formEditar.addEventListener('submit', () => {
    atualizarTotalEditar();
});

interface OrcamentoCard {
    id: string;
    idTipoGasto: string;
    limite: string;
    tipo: 'mensal' | 'secundario';
}

const modalOrcamento = document.getElementById('modalOrcamentoEditar') as HTMLElement | null;

if (modalOrcamento) {
    modalOrcamento.addEventListener('show.bs.modal', (event: any) => {
        const button = event.relatedTarget as HTMLElement;
        if (!button) return;

        const id = button.getAttribute('data-id') ?? '';
        const idTipoGasto = button.getAttribute('data-idTipoGasto') ?? '';
        const limiteAttr = button.getAttribute('data-limite') ?? '0';

        const card: OrcamentoCard = {
            id,
            idTipoGasto,
            limite: limiteAttr,
            tipo: button.classList.contains('orcamentoMensal') ? 'mensal' : 'secundario'
        };

        // Campos do modal
        const inputId = modalOrcamento.querySelector<HTMLInputElement>('#orcamentoId')!;
        const inputLimite = modalOrcamento.querySelector<HTMLInputElement>('#limiteOrcamento')!;
        const categoriaContainer = modalOrcamento.querySelector<HTMLDivElement>('#categoriaContainer')!;
        const inputCategoria = categoriaContainer.querySelector<HTMLSelectElement>('#categoriaOrcamento')!;
        const btnExcluir = modalOrcamento.querySelector<HTMLButtonElement>('#btnExcluir')!;

        // Preenche os campos do modal
        inputId.value = card.id;
        inputLimite.value = parseFloat(card.limite).toFixed(2);

        if (card.tipo === 'secundario') {
            categoriaContainer.style.display = 'flex';
            inputCategoria.value = card.idTipoGasto;
            inputCategoria.required = true;  // obrigatório só para secundário
            btnExcluir.style.display = 'inline-block';
        } else {
            categoriaContainer.style.display = 'none';
            inputCategoria.value = '';
            inputCategoria.required = false; // desativa required
            btnExcluir.style.display = 'none';
        }

    });
}

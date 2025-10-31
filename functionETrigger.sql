CREATE FUNCTION atualizaSaldo()
    RETURNS TRIGGER AS $$
DECLARE
linha RECORD;
	valor NUMERIC := 0;
	argIdUsuario INT;
BEGIN
	argIdUsuario := COALESCE(NEW.id_usuario, OLD.id_usuario);

FOR linha IN
SELECT tp_transacao, vl_transacao FROM EXTRATO WHERE EXTRATO.id_usuario = argIdUsuario
ORDER BY EXTRATO.dt_transacao ASC
    LOOP
		IF linha.tp_transacao = 'entrada' THEN
			valor := valor + linha.vl_transacao;
ELSIF linha.tp_transacao = 'saida' THEN
			valor := valor - linha.vl_transacao;
END IF;
END LOOP;

UPDATE usuario
SET saldo = valor
WHERE usuario.id_usuario = argIdUsuario;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

   -- trigger abaixo

CREATE TRIGGER trigger_atualiza_saldo
    AFTER INSERT OR UPDATE OR DELETE
                    ON extrato
                        FOR EACH ROW
                        EXECUTE FUNCTION atualizaSaldo();
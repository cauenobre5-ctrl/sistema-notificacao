package notificacao.servico;

import java.util.ArrayList;
import java.util.List;
import notificacao.modelo.Notificacao;
import notificacao.modelo.StatusNotificacao;

/** Registro das notificações processadas (parte da composição com ServicoNotificacao). */
public class HistoricoNotificacoes {
    private final List<Notificacao> registros = new ArrayList<>();

    public void registrar(Notificacao notificacao) { registros.add(notificacao); }

    public List<Notificacao> listarTodas() { return List.copyOf(registros); }

    public List<Notificacao> filtrarPorStatus(StatusNotificacao status) {
        return registros.stream().filter(n -> n.getStatus() == status).toList();
    }
}

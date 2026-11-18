package notificacao.servico;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import notificacao.estrategia.EstrategiaEnvio;
import notificacao.modelo.Destinatario;
import notificacao.modelo.Notificacao;
import notificacao.modelo.ResultadoEnvio;
import notificacao.modelo.TipoCanal;

/**
 * STRATEGY — Context. Recebe pedidos de notificação e delega o envio para a
 * estratégia registrada para o canal, sem nenhum if/switch por tipo de canal.
 */
public class ServicoNotificacao {
    private final Map<TipoCanal, EstrategiaEnvio> estrategias = new EnumMap<>(TipoCanal.class);
    private final HistoricoNotificacoes historico = new HistoricoNotificacoes(); // composição
    private final int maxTentativas;

    public ServicoNotificacao(int maxTentativas) {
        this.maxTentativas = maxTentativas;
    }

    /** Novos canais entram aqui, sem alterar o restante da classe (Aberto/Fechado). */
    public void registrarEstrategia(EstrategiaEnvio estrategia) {
        estrategias.put(estrategia.getCanal(), estrategia);
    }

    public ResultadoEnvio enviar(Notificacao notificacao) {
        EstrategiaEnvio estrategia = estrategias.get(notificacao.getCanal());
        ResultadoEnvio resultado;
        if (estrategia == null) {
            resultado = ResultadoEnvio.falhaDefinitiva("Nenhuma estratégia registrada para " + notificacao.getCanal());
            notificacao.marcarComoFalha();
        } else {
            resultado = enviarComRetentativa(estrategia, notificacao);
        }
        historico.registrar(notificacao);
        return resultado;
    }

    /** Envia a mesma mensagem por todos os canais preferidos do destinatário. */
    public List<ResultadoEnvio> enviarPorPreferencias(Destinatario destinatario, String titulo, String mensagem) {
        List<ResultadoEnvio> resultados = new ArrayList<>();
        for (TipoCanal canal : destinatario.getPreferencias()) {
            resultados.add(enviar(new Notificacao(destinatario, titulo, mensagem, canal)));
        }
        return resultados;
    }

    private ResultadoEnvio enviarComRetentativa(EstrategiaEnvio estrategia, Notificacao n) {
        ResultadoEnvio resultado;
        do {
            n.incrementarTentativa();
            resultado = estrategia.enviar(n);       // polimorfismo: e-mail, SMS ou push
            if (resultado.isSucesso()) {
                n.marcarComoEnviada();
                return resultado;
            }
        } while (!resultado.isDefinitivo() && n.getTentativas() < maxTentativas);
        n.marcarComoFalha();
        return resultado;
    }

    public HistoricoNotificacoes getHistorico() { return historico; }
}

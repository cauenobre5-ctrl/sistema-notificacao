package notificacao.estrategia;

import notificacao.modelo.Destinatario;
import notificacao.modelo.Notificacao;
import notificacao.modelo.ResultadoEnvio;
import notificacao.modelo.TipoCanal;

/**
 * STRATEGY — abstração comum (Strategy) para todos os algoritmos de envio.
 * O ServicoNotificacao (Context) conhece apenas esta abstração; cada subclasse
 * concreta encapsula as regras de um canal (validação, formatação, entrega).
 */
public abstract class EstrategiaEnvio {

    /** Canal que esta estratégia atende. */
    public abstract TipoCanal getCanal();

    /** Verifica se o destinatário possui o contato necessário para este canal. */
    protected abstract boolean validarDestinatario(Destinatario destinatario);

    /** Formata e entrega a mensagem de fato (cada canal faz de um jeito). */
    protected abstract ResultadoEnvio executarEnvio(Notificacao notificacao);

    /** Fluxo comum a todas as estratégias: valida e depois envia. */
    public final ResultadoEnvio enviar(Notificacao notificacao) {
        if (!validarDestinatario(notificacao.getDestinatario())) {
            return ResultadoEnvio.falhaDefinitiva(getCanal() + ": destinatário sem contato válido para este canal");
        }
        return executarEnvio(notificacao);
    }
}

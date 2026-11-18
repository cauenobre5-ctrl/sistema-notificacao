package notificacao;

import notificacao.adapter.FirebaseAdapter;
import notificacao.adapter.SendGridAdapter;
import notificacao.adapter.TwilioAdapter;
import notificacao.estrategia.EnvioEmail;
import notificacao.estrategia.EnvioPush;
import notificacao.estrategia.EnvioSms;
import notificacao.externo.FirebaseMessaging;
import notificacao.externo.SendGridApi;
import notificacao.externo.TwilioClient;
import notificacao.modelo.Destinatario;
import notificacao.modelo.Notificacao;
import notificacao.modelo.StatusNotificacao;
import notificacao.modelo.TipoCanal;
import notificacao.servico.ServicoNotificacao;

/** Prova de conceito do padrão Strategy no Sistema de Notificação. */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Sistema de Notificação - Prova de Conceito (Strategy) ===\n");

        // 1) Montagem: cada estratégia recebe o gateway (adapter) do seu provedor.
        ServicoNotificacao servico = new ServicoNotificacao(3);
        servico.registrarEstrategia(new EnvioEmail(
                new SendGridAdapter(new SendGridApi("SG.chave-demo")), "noreply@loja.com"));
        servico.registrarEstrategia(new EnvioSms(
                new TwilioAdapter(new TwilioClient(1), "+5511900000000")));   // falha 1x de propósito
        servico.registrarEstrategia(new EnvioPush(
                new FirebaseAdapter(new FirebaseMessaging())));

        // 2) Destinatários
        Destinatario ana = new Destinatario("U1", "Ana", "ana@email.com", "+5511987654321", "token-ana-123");
        ana.adicionarPreferencia(TipoCanal.EMAIL);
        ana.adicionarPreferencia(TipoCanal.SMS);
        ana.adicionarPreferencia(TipoCanal.PUSH);

        Destinatario bruno = new Destinatario("U2", "Bruno", "bruno-sem-arroba", null, "expired-token-999");

        // 3) Mesmo código cliente, comportamento diferente conforme a estratégia.
        System.out.println(">> Cenário 1: Ana recebe pelos 3 canais preferidos (SMS falha 1x e é reenviado)");
        servico.enviarPorPreferencias(ana, "Pedido #1042 confirmado",
                "Seu pedido foi aprovado e será enviado em até 2 dias úteis. "
                + "Acompanhe o rastreio pelo aplicativo ou pelo site da loja a qualquer momento.")
                .forEach(r -> System.out.println("   " + r));

        System.out.println("\n>> Cenário 2: Bruno - e-mail inválido (falha de validação, sem retentativa)");
        System.out.println("   " + servico.enviar(
                new Notificacao(bruno, "Promoção", "50% de desconto hoje!", TipoCanal.EMAIL)));

        System.out.println("\n>> Cenário 3: Bruno - token de push expirado (provedor rejeita 3x)");
        System.out.println("   " + servico.enviar(
                new Notificacao(bruno, "Promoção", "50% de desconto hoje!", TipoCanal.PUSH)));

        System.out.println("\n>> Cenário 4: Troca de estratégia em tempo de execução (canal SMS passa a usar outro provedor)");
        servico.registrarEstrategia(new EnvioSms(
                new TwilioAdapter(new TwilioClient(0), "+5521911111111")));
        System.out.println("   " + servico.enviar(
                new Notificacao(ana, "Código 482913", "Use este código para entrar.", TipoCanal.SMS)));

        System.out.println("\n=== Histórico ===");
        servico.getHistorico().listarTodas().forEach(n -> System.out.println("   " + n));
        System.out.printf("%nEnviadas: %d | Falhas: %d%n",
                servico.getHistorico().filtrarPorStatus(StatusNotificacao.ENVIADA).size(),
                servico.getHistorico().filtrarPorStatus(StatusNotificacao.FALHOU).size());
    }
}

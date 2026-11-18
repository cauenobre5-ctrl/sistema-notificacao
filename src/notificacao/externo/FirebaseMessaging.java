package notificacao.externo;

/** Adaptee (simulado): serviço de push de terceiro com modelo de mensagem próprio. */
public class FirebaseMessaging {

    public static class Message {
        public final String token;
        public final String title;
        public final String body;

        public Message(String token, String title, String body) {
            this.token = token;
            this.title = title;
            this.body = body;
        }
    }

    /** Retorna um messageId ou null quando o token é rejeitado. */
    public String dispatch(Message message) {
        if (message.token.startsWith("expired")) {
            System.out.println("      [Firebase] token rejeitado: " + message.token);
            return null;
        }
        System.out.println("      [Firebase] push \"" + message.title + "\" -> " + message.token);
        return "projects/demo/messages/" + System.nanoTime();
    }
}

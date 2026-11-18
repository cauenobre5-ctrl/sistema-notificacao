package notificacao.adapter;

import notificacao.externo.FirebaseMessaging;

/** ADAPTER — monta o objeto Message do Firebase e traduz o retorno nulo em false. */
public class FirebaseAdapter implements GatewayPush {
    private final FirebaseMessaging firebase;

    public FirebaseAdapter(FirebaseMessaging firebase) { this.firebase = firebase; }

    @Override
    public boolean enviarPush(String deviceToken, String titulo, String corpo) {
        FirebaseMessaging.Message msg = new FirebaseMessaging.Message(deviceToken, titulo, corpo);
        return firebase.dispatch(msg) != null;
    }
}

import conexaoBD.ConexaoBem;

public class Main {

    public static void main(String[] args) throws Exception {
        ConexaoBem conexaoBem= new ConexaoBem();
        System.out.println(conexaoBem.deletar(165));



        /*

        //Criação do objeto bot com as informações de acesso
        TelegramBot bot = TelegramBotAdapter.build("1027531974:AAEZjGaM6fNmmSVFd1dJNgnpl1_58yQaZ5o");

        //objeto responsável por receber as mensagens
        GetUpdatesResponse updatesResponse;
        //objeto responsável por gerenciar o envio de respostas
        SendResponse sendResponse;
        //objeto responsável por gerenciar o envio de ações do chat
        BaseResponse baseResponse;

        //controle de off-set, isto é, a partir deste ID será lido as mensagens pendentes na fila
        int m=0;

        //loop infinito pode ser alterado por algum timer de intervalo curto
        while (true){

            //executa comando no Telegram para obter as mensagens pendentes a partir de um off-set (limite inicial)
            updatesResponse =  bot.execute(new GetUpdates().limit(100).offset(m));

            //lista de mensagens
            List<Update> updates = updatesResponse.updates();

            //análise de cada ação da mensagem
            for (Update update : updates) {

                //atualização do off-set
                m = update.updateId()+1;
                //resposta do user
                System.out.println("Recebendo mensagem:"+ update.message().text());

                //envio de "Escrevendo" antes de enviar a resposta
                baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
                //verificação de ação de chat foi enviada com sucesso
                System.out.println("Resposta de Chat Action Enviada?" + baseResponse.isOk());

                //envio da mensagem de resposta
                sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Vai se foder, bot otario"));
                //verificação de mensagem enviada com sucesso
                System.out.println("Mensagem Enviada?" +sendResponse.isOk());

            }

        }

        */

    }

}
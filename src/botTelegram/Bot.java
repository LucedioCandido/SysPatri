package botTelegram;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

import java.util.List;

public class Bot {
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

    //para armazenar as mensagens
    List<Update> updates;

    public Bot(){

    }


    public String getMensagens(){
        //executa comando no Telegram para obter as mensagens pendentes a partir de um off-set (limite inicial)
        updatesResponse =  bot.execute(new GetUpdates().limit(100).offset(m));
        updates = updatesResponse.updates();
        String acao = "";

        for (Update update : updates) {
            //atualização do off-set
            m = update.updateId()+1;
            long user = update.message().chat().id();
            int op = 0;
            String mensagem = update.message().text();
            //envio de "Escrevendo" antes de enviar a resposta
            baseResponse = bot.execute(new SendChatAction(user, ChatAction.typing.name()));

            if(mensagem.charAt(0) =='/' ){
               op= getComando(mensagem);
               acao = Integer.toString(op);
               return user+':'+acao+'='+mensagem;
            }




        }

        return acao;

    }

    private int getComando(String text){
        return 0;
    }



    private boolean sendResponse(String resposta, long idUser  ){
        //envio da mensagem de resposta
        sendResponse = bot.execute(new SendMessage(idUser,"Vai se foder, bot otario"));
        return sendResponse.isOk();
    }
}

package botTelegram;


import Objetos.Bem;
import Objetos.Categoria;
import Objetos.Localizacao;
import com.itextpdf.text.DocumentException;
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
import conexaoBD.ConexaoBem;
import conexaoBD.ConexaoCategoria;
import conexaoBD.ConexaoLocalizacao;
import conexaoBD.excecoesBD.AbsenceDriverMSQLException;
import conexaoBD.excecoesBD.DatabaseAccessException;
import conexaoBD.excecoesBD.InvalidInputParametersException;
import relatorios.Relatorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Bot {
    //Criação do objeto bot com as informações de acesso
    TelegramBot bot = TelegramBotAdapter.build("931603595:AAG7QSZgTKdFGZxFk4CgLYdzYUR3Fl8QxDg");

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

    // fila de instrucoes
    Stack<String> instrucao ;
    Stack<String> partsInstrucao;

    public Bot(){

    }

    public void getMensagens() throws DatabaseAccessException, InvalidInputParametersException, AbsenceDriverMSQLException {
        String acao = "";
        String param = "";
        while (true) {
            //executa comando no Telegram para obter as mensagens pendentes a partir de um off-set (limite inicial)
            updatesResponse = bot.execute(new GetUpdates().limit(100).offset(m));
            updates = updatesResponse.updates();
            for (Update update : updates) {
                //atualização do off-set
                m = update.updateId() + 1;
                long user = update.message().chat().id();
                String mensagem = update.message().text();

                try {
                    //envio de "Escrevendo" antes de enviar a resposta
                    baseResponse = bot.execute(new SendChatAction(user, ChatAction.typing.name()));
                    if (mensagem.charAt(0) == '/') {
                        if(update.message().text().equals("/pdf")){
                            Relatorio rel = new Relatorio();
                            rel.exportData();
                        }
                        if(update.message().text().equals("/cadbem")){
                            novoBem(user);
                        }
                        if(update.message().text().equals("/movebem")){

                        }
                        if(update.message().text().equals("/delbem")){
                            removerBem(user);
                        }
                    } else {
                            sendResponse = bot.execute(new SendMessage(user,"Entrada inválida, selecione primeiro um comando."));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private String printAjuda(){
        String menu = " --- Menu de opções do sistema --- \n"+
                      ""
                    ;
        return menu;
    }

    public void getPDF() throws DocumentException, DatabaseAccessException, AbsenceDriverMSQLException {
        Relatorio rel = new Relatorio();
        rel.exportData();
    }

    private void novoBem(long id) throws DatabaseAccessException, InvalidInputParametersException, AbsenceDriverMSQLException {
        String nome =null, descricao =null;
        int codLocal=0, codCategoria=0;
        Bem bem;
        ConexaoBem conexao = new ConexaoBem();
        boolean pass =false;

        //pega o nome do bem
        baseResponse = bot.execute(new SendChatAction(id, ChatAction.typing.name()));
        sendResponse = bot.execute(new SendMessage(id,"Me diga o nome do bem"));
        pass=false;
        while(!pass) {
            updatesResponse = bot.execute(new GetUpdates().limit(100).offset(m));
            updates = updatesResponse.updates();
            for (Update update : updates) {
                m = update.updateId() + 1;
                nome = update.message().text();
                pass=true;
            }
        }

        //pega a descricao do bem
        baseResponse = bot.execute(new SendChatAction(id, ChatAction.typing.name()));
        sendResponse = bot.execute(new SendMessage(id, "Agora uma descrição do bem"));
        pass=false;
        while(!pass) {
            updatesResponse = bot.execute(new GetUpdates().limit(100).offset(m));
            updates = updatesResponse.updates();
            for (Update update : updates) {
                m = update.updateId() + 1;
                descricao = update.message().text();
                pass=true;
            }
        }

        //pega a categoria do bem, exibe todasd as categorias cadastradas
        ConexaoCategoria categoria= new ConexaoCategoria();
        pass=false;
        baseResponse = bot.execute(new SendChatAction(id, ChatAction.typing.name()));
        sendResponse = bot.execute(new SendMessage(id,"Me diga em que categoria ele se encaixa? listei abaixo as cadastradas."));
        ArrayList<Categoria> categorias = categoria.consultar();
        for (Categoria cat: categorias){
            String nomeCate = cat.getNome()+"-";
            sendResponse = bot.execute(new SendMessage(id,nomeCate));
        }
        while(!pass) {
            updatesResponse = bot.execute(new GetUpdates().limit(100).offset(m));
            updates = updatesResponse.updates();
            for (Update update : updates) {
                m = update.updateId() + 1;
                codCategoria = categoria.procuraExistenciaCategoria(update.message().text());
                pass=true;
            }
        }

        //pega a localizacao do bem, exibe todasd as localizacões cadastradas
        ConexaoLocalizacao localizacao = new ConexaoLocalizacao();
        baseResponse = bot.execute(new SendChatAction(id, ChatAction.typing.name()));
        sendResponse = bot.execute(new SendMessage(id,"Me diga o local onde ele ficará.Listei abaixo as cadastradas."));
        ArrayList<Localizacao> locais = localizacao.consultar();
        pass=false;
        for (Localizacao loc: locais){
            String nomelocal = loc.getNome()+"-";
            sendResponse = bot.execute(new SendMessage(id,nomelocal));
        }
        while(!pass) {
            updatesResponse = bot.execute(new GetUpdates().limit(100).offset(m));
            updates = updatesResponse.updates();
            for (Update update : updates) {
                m = update.updateId() + 1;
                codLocal = localizacao.procuraExistenciaLocal(update.message().text());
                pass = true;
            }
        }
        bem = new Bem(nome,descricao,codCategoria,codLocal);
        conexao.adicionar(bem);
        baseResponse = bot.execute(new SendChatAction(id, ChatAction.typing.name()));
        sendResponse = bot.execute(new SendMessage(id,"Feito!"));
    }

    private void removerBem(long id) throws InvalidInputParametersException, DatabaseAccessException, AbsenceDriverMSQLException {
        ConexaoBem conexao = new ConexaoBem();
        boolean pass =false;
        int cod = 0;
        //pega o nome do bem
        baseResponse = bot.execute(new SendChatAction(id, ChatAction.typing.name()));
        sendResponse = bot.execute(new SendMessage(id,"Me diga o tombo do bem que deseja remover"));
        while(!pass) {
            updatesResponse = bot.execute(new GetUpdates().limit(100).offset(m));
            updates = updatesResponse.updates();
            for (Update update : updates) {
                m = update.updateId() + 1;
                cod = Integer.parseInt(update.message().text());
                pass=true;
            }
        }
        if(conexao.verificarExistencia(cod)==null){
            conexao.remover(cod);
            baseResponse = bot.execute(new SendChatAction(id, ChatAction.typing.name()));
            sendResponse = bot.execute(new SendMessage(id,"Feito!"));
        }else{
            baseResponse = bot.execute(new SendChatAction(id, ChatAction.typing.name()));
            sendResponse = bot.execute(new SendMessage(id,"Não encontrei nenhum bem com esse tombo!"));
        }

    }


}


